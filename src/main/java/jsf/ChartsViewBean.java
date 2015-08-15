package jsf;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

import entity.Record;
import service.RecordService;

public class ChartsViewBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static RecordService instance = RecordService.getInstance();

	// Bean variables

	private LineChartModel lineModel;

	private PieChartModel pieModel;

	private BarChartModel barModel;
	
	private boolean modelNotEmpty;

	private String[] selectedDevices;

	private List<String> devices;

	private Date startDate = new Date();

	private Date endDate = new Date();
	
	private int recordsCount;

	// Constructor

	public ChartsViewBean() {
		initSelectedDevices();
		// setting a period that starts in first day of last month until now
		initTimeSpan();
		// last 3 records
		recordsCount = 3;
		// initializing models
		createLineModel();
		createPieModel();
		createBarModel();
	}

	// Getters and setters

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}

	public int getRecordsCount() {
		return recordsCount;
	}

	public void setRecordsCount(int recordsCount) {
		this.recordsCount = recordsCount;
	}

	public boolean isModelNotEmpty() {
		return modelNotEmpty;
	}

	public void setModelNotEmpty(boolean modelNotEmpty) {
		this.modelNotEmpty = modelNotEmpty;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public String[] getSelectedDevices() {
		return selectedDevices;
	}

	public void setSelectedDevices(String[] selectedDevices) {
		this.selectedDevices = selectedDevices;
	}

	public List<String> getDevices() {
		return devices;
	}

	public void setDevices(List<String> devices) {
		this.devices = devices;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	// Chart methods

	private void createBarModel() {
		List<Record[]> recordArrays = instance.getLastRecords(selectedDevices, recordsCount);
		BarChartModel model = new BarChartModel();

		for (Record[] recs : recordArrays) {
			if (recs.length == 0) {
				System.out.println("NO DATA FOR THE PERIOD!");
			} else {
				barModel = initBarModel(recs, model);
			}
		}
		if (!modelNotEmpty) {
			ChartSeries emptySeries = new ChartSeries();
			emptySeries.set("No records found.", 0);
			model.addSeries(emptySeries);
			barModel = model;
		}

		barModel.setTitle("Bar chart of last records");
		barModel.setAnimate(true);
		barModel.setLegendPosition("ne");

		barModel.getAxis(AxisType.Y).setLabel("Temperature C\u00b0");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("Time");
	}

	private void createLineModel() {
		List<Record[]> recordArrays = instance.getRecordsByDevicesAndTime(selectedDevices, startDate.getTime(),
				endDate.getTime());
		LineChartModel model = new LineChartModel();

		for (Record[] recs : recordArrays) {
			if (recs.length == 0) {
				System.out.println("NO DATA FOR THE PERIOD!");
			} else {
				lineModel = initCategoryModel(recs, model);
				modelNotEmpty = true;
			}
		}
		if (!modelNotEmpty) {
			ChartSeries emptySeries = new ChartSeries();
			emptySeries.set("No records found.", 0);
			model.addSeries(emptySeries);
			lineModel = model;
		}

		lineModel.setTitle("Temperature Chart");
		lineModel.setAnimate(true);
		lineModel.setLegendPosition("e");

		lineModel.getAxis(AxisType.Y).setLabel("Temperature C\u00b0");
		DateAxis axisX = new DateAxis("Dates");
		String tomorrow = printTomorrow();
		// set tomorrow as max day
		axisX.setTickAngle(-50);
		axisX.setMax(tomorrow);
		axisX.setTickFormat("%b %#d | %H:%M");
		lineModel.getAxes().put(AxisType.X, axisX);
	}

	private void createPieModel() {
		HashMap<String, Double> averages = instance.getFilteredAverages(selectedDevices, startDate.getTime(),
				endDate.getTime());
		pieModel = new PieChartModel();

		for (String key : averages.keySet()) {
			pieModel.set(key, averages.get(key));
		}

		pieModel.setTitle("Average per device");
		pieModel.setLegendPosition("w");
		pieModel.setShowDataLabels(true);
	}

	private LineChartModel initCategoryModel(Record[] records, LineChartModel model) {
		ChartSeries values = new ChartSeries();
		values.setLabel(records[0].getDevice());

		// creating value data for chart series
		for (Record rec : records) {
			String date = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(rec.getTimestamp());
			// Double quan = Double.parseDouble(rec.getQuantity());
			values.set(date, rec.getValue());
		}
		model.addSeries(values);

		return model;
	}

	private BarChartModel initBarModel(Record[] records, BarChartModel model) {
		ChartSeries values = new ChartSeries();
		values.setLabel(records[0].getDevice());

		// creating value data for chart series
		for (Record rec : records) {
			String date = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(rec.getTimestamp());
			// Double quan = Double.parseDouble(rec.getQuantity());
			values.set(date, rec.getValue());
		}
		model.addSeries(values);

		return model;
	}

	// filter methods

	public void filtersChange() {
		createLineModel();
		createPieModel();
	}

	public void onDateSelect(SelectEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
	}
	
	public void refreshBar() {
		createBarModel();
	}

	// utility methods

	private String printTomorrow() {
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		String tomorrow = new SimpleDateFormat("yyyy-MM-dd").format(dt);
		return tomorrow;
	}
	
	private void initSelectedDevices() {
		devices = Arrays.asList(instance.getDevicesArray());
		// choosing first 5 devices
		if (devices.size() < 5) {
			selectedDevices = new String[devices.size()];
		} else {
			selectedDevices = new String[5];
		}
		for (int i = 0; (i < devices.size()) && (i < 5); i++) {
			selectedDevices[i] = devices.get(i);
		}
	}
	
	private void initTimeSpan() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		// cal.set(Calendar.DATE, 1);
		startDate = cal.getTime();
		endDate = new Date();
	}

}