package jsf;

import java.io.Serializable;
import java.util.*;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

import entity.Record;
import entity.User;
import service.DeviceService;
import service.RecordService;
import util.DateUtil;

public class ChartsViewBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger.getLogger(ChartsViewBean.class);

	private static RecordService recordService = RecordService.getInstance();
	private static DeviceService deviceService = DeviceService.getInstance();

	private static ResourceBundle rb;
	
	// Default selected devices number
	private static final int DEV_NUMBER = 5;
	// Default records number per device for bar chart
	private static final int REC_NUMBER = 3;
	// Records type by default
	private static final String REC_TYPE = "temp";
	// Chart type by default
	private static final String CHART_TYPE = "line";

	// Bean variables

	private LineChartModel lineModel;
	private PieChartModel pieModel;
	private BarChartModel barModel;

	private String[] selectedDevices;
	private List<String> devices;
	private Date startDate = new Date();
	private Date endDate = new Date();
	private int recordsCount;

	private String recordsType;
	private String chartType;

	// Constructor

	public ChartsViewBean() {
		LOG.debug("Getting Faces Context and resource bundle.");
		FacesContext context = FacesContext.getCurrentInstance();
		rb = ResourceBundle.getBundle("i18n.messages", context.getViewRoot().getLocale());
		
		LOG.debug("Setting default devices for chart.");
		initSelectedDevices();
		
		LOG.debug("Chart data time span initialization.");
		initTimeSpan();
		
		recordsCount = REC_NUMBER;
		recordsType = REC_TYPE;
		chartType = CHART_TYPE;
		
		initChart();
	}

	// Chart methods
	
	private void createLineModel() {
		List<Record[]> recordArrays = recordService.getRecordsForLineChart(selectedDevices, recordsType, startDate,
				endDate);
		LOG.debug("Received records of " + recordArrays.size() + " devices.");
		
		LineChartModel model = new LineChartModel();
		LOG.debug("Line chart model initialization.");
		for (int i = 0; i < recordArrays.size(); i++) {
			Record[] recs = recordArrays.get(i);
			if (recs.length > 0) {
				ChartSeries series = initSeries(recs);
				model.addSeries(series);
				LOG.trace("Initialized a series with size: " + recs.length);
			}
		}
		if ( model.getSeries().size() == 0 ) {
			model.addSeries(initEmptySeries());
			LOG.debug("Line chart model has no data.");
		}
		
		lineModel = model;
		lineModel.setTitle(rb.getString("line.chart"));
		lineModel.setAnimate(true);
		lineModel.setLegendPosition("e");

		String fullType = getFullNameOfRecordsType(recordsType);
		LOG.debug("Records type now is: " + fullType);
		lineModel.getAxis(AxisType.Y).setLabel(fullType);
		
		DateAxis axisX = new DateAxis(rb.getString("dates"));
		axisX.setTickAngle(-50);
		String startStr = DateUtil.dateToCZFormat(startDate);
		String endStr = DateUtil.dateToCZFormat(endDate);
		LOG.debug("Time span now is: from " + startStr + " to " + endStr + ".");
		axisX.setMin(startStr);
		axisX.setMax(endStr);
		axisX.setTickFormat("%#d %b %H:%M");
		lineModel.getAxes().put(AxisType.X, axisX);
	}

	private void createBarModel() {
		List<Record[]> recordArrays = recordService.getLastRecords(selectedDevices, recordsType, recordsCount);
		LOG.debug("Received records of " + recordArrays.size() + " devices.");
		
		BarChartModel model = new BarChartModel();
		LOG.debug("Bar chart model initialization.");
		for (int i = 0; i < recordArrays.size(); i++) {
			Record[] recs = recordArrays.get(i);
			if (recs.length > 0) {
				ChartSeries series = initSeries(recs);
				model.addSeries(series);
				LOG.trace("Initialized a series with size: " + recs.length);
			}
		}
		if ( model.getSeries().size() == 0 ) {
			model.addSeries(initEmptySeries());
			LOG.debug("Bar chart model has no data.");
		}
		
		barModel = model;
		barModel.setTitle(rb.getString("bar.chart.of.last.records"));
		barModel.setAnimate(true);
		barModel.setLegendPosition("e");

		String fullType = getFullNameOfRecordsType(recordsType);
		LOG.debug("Records type now is: " + fullType);
		barModel.getAxis(AxisType.Y).setLabel(fullType);

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel(rb.getString("time"));
	}

	private void createPieModel() {
		HashMap<String, Double> averages = recordService.getFilteredAverages(selectedDevices, recordsType,
				startDate, endDate);
		LOG.debug("Received averages of " + averages.size() + " devices.");
		
		LOG.debug("Chart model initialization.");
		pieModel = new PieChartModel();
		if (averages.size() == 0) {
			pieModel.set(rb.getString("no.records.found.for.the.period"), 0);
		} else {
			for (String key : averages.keySet()) {
				pieModel.set(key, averages.get(key));
			}
		}

		pieModel.setTitle(rb.getString("average.per.device"));
		pieModel.setLegendPosition("e");
		pieModel.setShowDataLabels(true);
	}

	// initializing methods

	private ChartSeries initSeries(Record[] records) {
		ChartSeries series = new ChartSeries();

		LOG.debug("Initialization of " + records.length + " records.");
		series.setLabel(records[0].getDevice().getMac());
		// creating value data for chart series
		for (Record rec : records) {
			String formatedDate = DateUtil.timestampToStringFmt(rec.getTimestamp());
			series.set(formatedDate, rec.getValue());
		}

		return series;
	}
	
	private ChartSeries initEmptySeries() {
		ChartSeries series = new ChartSeries();
		series.setLabel(rb.getString("no.records.found.for.the.period"));
		series.set(DateUtil.dateToCZFormat(new Date()), 0);
		return series;
	}
	
	// limit 5 devices
	private void initSelectedDevices() {
		FacesContext context = FacesContext.getCurrentInstance();
		UserBean userB = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);
		
		if (userB.isAdminRole()) {
			devices = deviceService.getAllMacs(true);
			LOG.debug("Recieved " + devices.size() + " devices with admin access.");
		} else {
			User currentUser = userB.getUser();
			devices = deviceService.getMacsByUser(currentUser.getUsername(), true);
			LOG.debug("Recieved " + devices.size() + " devices with user access. User: " + currentUser.getUsername());
		}
		// choosing default devices
		int size = devices.size() < DEV_NUMBER ? devices.size() : DEV_NUMBER;
		selectedDevices = new String[size];
		for (int i = 0; i < size; i++) {
			selectedDevices[i] = devices.get(i);
		}
	}
	
	// yesterday
	private void initTimeSpan() {
		startDate = DateUtil.getLastHour();
		endDate = new Date();
	}

	public void initChart() {
		if (chartType.equals("line")) {
			createLineModel();
		} else if (chartType.equals("bar")) {
			createBarModel();
		} else {
			createPieModel();
		}
	}

	private String getFullNameOfRecordsType(String shortType) {
		if (shortType.equals("temp")) {
			return rb.getString("temperature.c");
		} else if (shortType.equals("humi")) {
			return rb.getString("graph.humi");
		} else {
			return rb.getString("graph.pres");
		}
	}

	// Getters and setters

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}

	public BarChartModel getBarModel() {
		return barModel;
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

	public int getRecordsCount() {
		return recordsCount;
	}

	public void setRecordsCount(int recordsCount) {
		this.recordsCount = recordsCount;
	}

	public String getRecordsType() {
		return recordsType;
	}

	public void setRecordsType(String recordsType) {
		this.recordsType = recordsType;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
}