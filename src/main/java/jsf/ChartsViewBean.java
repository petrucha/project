package jsf;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import service.DeviceService;
import service.RecordService;
import util.DateUtil;

public class ChartsViewBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static RecordService recordService = RecordService.getInstance();

	private static DeviceService deviceService = DeviceService.getInstance();

	// Bean variables

	private LineChartModel lineModel;

	private PieChartModel pieModel;

	private BarChartModel barModel;

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
		createBarModel();
		createLineModel();
		createPieModel();
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
		List<Record[]> recordArrays = recordService.getLastRecords(selectedDevices, "temp", recordsCount);
		BarChartModel model = new BarChartModel();

		for (Record[] recs : recordArrays) {
			ChartSeries series = initModel(recs);
			model.addSeries(series);
		}
		barModel = model;

		barModel.setTitle("Bar chart of last records");
		barModel.setAnimate(true);
		barModel.setLegendPosition("ne");

		barModel.getAxis(AxisType.Y).setLabel("Temperature C\u00b0");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("Time");
	}

	private void createLineModel() {
		List<Record[]> recordArrays = recordService.getRecordsForLineChart(selectedDevices, "temp", startDate,
				endDate);
		LineChartModel model = new LineChartModel();

		for (Record[] recs : recordArrays) {
			ChartSeries series = initModel(recs);
			model.addSeries(series);
		}
		lineModel = model;

		lineModel.setTitle("Temperature Chart");
		lineModel.setAnimate(true);
		lineModel.setLegendPosition("e");

		lineModel.getAxis(AxisType.Y).setLabel("Temperature C\u00b0");
		DateAxis axisX = new DateAxis("Dates");
		String nextHour = DateUtil.printNextHour(endDate);
		
		axisX.setTickAngle(-50);
		axisX.setMax(nextHour);
		axisX.setTickFormat("%#d %b %H:%M");
		lineModel.getAxes().put(AxisType.X, axisX);
	}

	private void createPieModel() {
		HashMap<String, Double> averages = recordService.getFilteredAverages(selectedDevices, "temp", startDate,
				endDate);
		pieModel = new PieChartModel();
		System.out.println(averages.size());
		if (averages.size() == 0) {
			pieModel.set("No records found for the period.", 0);
		} else {
			for (String key : averages.keySet()) {
				pieModel.set(key, averages.get(key));
			}
		}

		pieModel.setTitle("Average per device");
		pieModel.setLegendPosition("w");
		pieModel.setShowDataLabels(true);
	}
	
	// initializing methods

	private ChartSeries initModel(Record[] records) {
		ChartSeries series = new ChartSeries();
		SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMM hh:mm:ss");

		if (records.length == 0) {
			series.setLabel("No records found for the period.");
			series.set(fmtOut.format(new Date()), 0);
		} else {
			series.setLabel(records[0].getDevice().getMac());
			// creating value data for chart series
			for (Record rec : records) {
				String formatedDate = DateUtil.timestampToStringFmt(rec.getTimestamp());
				series.set(formatedDate, rec.getValue());
			}
		}

		return series;
	}
	
	private void initSelectedDevices() {
		devices = deviceService.getAllMacs(true);
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
		cal.add(Calendar.DATE, -1);
		startDate = cal.getTime();
		endDate = new Date();
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

}