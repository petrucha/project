package jsf;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import entity.Record;
import service.RecordService;

public class ChartsViewBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static RecordService instance = RecordService.getInstance();
	
	//Bean variables
	
	private LineChartModel valueModel;
	
	private String[] selectedDevices = {"AAAA"};  // make it in the constructor  
	
    private List<String> devices;
    
    private Date startDate = new Date();
    
    private Date endDate = new Date();
    
    // Constructor

	public ChartsViewBean() {
		String[] allDevices = instance.getDevicesArray();
		// choosing first 5 devices
		if (allDevices.length < 5 ) {
			selectedDevices = new String[allDevices.length];
		} else {
			selectedDevices = new String[5];
		}
		for (int i = 0; (i < allDevices.length) && (i < 5); i++) {
			selectedDevices[i] = allDevices[i];
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, 1);
		Date firstDateOfPreviousMonth = cal.getTime();
		// initializes chart by period that starts in first day of last month until now
		createValueModel(instance.getRecordsByDevicesAndTime(selectedDevices, firstDateOfPreviousMonth.getTime(), new Date().getTime()));

        devices = Arrays.asList(instance.getDevicesArray());
	}
	
	// Getters and setters

	public LineChartModel getValueModel() {
		return valueModel;
	}

	public void setValueModel(LineChartModel valueModel) {
		this.valueModel = valueModel;
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

	private void createValueModel(List<Record[]> recordArrays) {
		LineChartModel model = new LineChartModel();

		for (Record[] recs : recordArrays) {
			if (recs.length == 0) {
				System.out.println("NO DATA FOR THE PERIOD!");
			} else {
				valueModel = initCategoryModel(recs, model);
			}
		}
		
		valueModel.setTitle("Temperature Chart");
		valueModel.setLegendPosition("e");
		valueModel.setShowPointLabels(true);
		valueModel.getAxes().put(AxisType.X, new CategoryAxis("Date"));
		Axis yAxis = valueModel.getAxis(AxisType.Y);
		yAxis.setLabel("Temperature C\u00b0");
		// yAxis.setMin(0);
		// yAxis.setMax(200);
	}

	private LineChartModel initCategoryModel(Record[] records, LineChartModel model) { 
		ChartSeries values = new ChartSeries();
		values.setLabel(records[0].getDevice());
														
		// creating value data for chart series
		for (Record rec : records) {
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rec.getTimestamp());
			// Double quan = Double.parseDouble(rec.getQuantity());
			values.set(date, rec.getValue());
		}
		model.addSeries(values);

		return model;
	}
	
	//filter methods
	
	public void filtersChange() {
		System.out.println(startDate);
		System.out.println(endDate);
		createValueModel(instance.getRecordsByDevicesAndTime(selectedDevices, startDate.getTime(), endDate.getTime()));
	}
	
	public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

}