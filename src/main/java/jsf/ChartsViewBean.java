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
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

import entity.Record;
import service.RecordService;

public class ChartsViewBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static RecordService instance = RecordService.getInstance();
	
	//Bean variables
	
	private LineChartModel valueModel;
	
	private PieChartModel averageModel;
	
	private String[] selectedDevices;
	
    private List<String> devices;
    
    private Date startDate = new Date();
    
    private Date endDate = new Date();
    
    // Constructor

	public ChartsViewBean() {
		devices = Arrays.asList(instance.getDevicesArray());
		// choosing first 5 devices
		if (devices.size() < 5 ) {
			selectedDevices = new String[devices.size()];
		} else {
			selectedDevices = new String[5];
		}
		for (int i = 0; (i < devices.size()) && (i < 5); i++) {
			selectedDevices[i] = devices.get(i);
		}
		// setting a period that starts in first day of last month until now
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
//		cal.set(Calendar.DATE, 1);
		startDate = cal.getTime();
		endDate = new Date();
		//initializing of filtered data
		filtersChange();
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
	
	public PieChartModel getAverageModel() {
		return averageModel;
	}

	public void setAverageModel(PieChartModel averageModel) {
		this.averageModel = averageModel;
	}

	// Chart methods

	private void createValueModel() {
		List<Record[]> recordArrays = instance.getRecordsByDevicesAndTime(selectedDevices, startDate.getTime(), endDate.getTime());
		LineChartModel model = new LineChartModel();

		for (Record[] recs : recordArrays) {
			if (recs.length == 0) {
				System.out.println("NO DATA FOR THE PERIOD!");
			} else {
				valueModel = initCategoryModel(recs, model);
			}
		}

		valueModel.setTitle("Temperature Chart");
		valueModel.setAnimate(true);
		valueModel.setLegendPosition("e");
		valueModel.getAxis(AxisType.Y).setLabel("Temperature C\u00b0");
		
        DateAxis axisX = new DateAxis("Dates");
        String tomorrow = printTomorrow();
        //set tomorrow as max day
        axisX.setTickAngle(-50);
        axisX.setMax(tomorrow);
        axisX.setTickFormat("%b %#d | %H:%M");
         
        valueModel.getAxes().put(AxisType.X, axisX);
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
	
	private void createAverageModel() {
		HashMap<String, Double> averages = instance.getFilteredAverages(selectedDevices, startDate.getTime(), endDate.getTime());
        averageModel = new PieChartModel();
        
        for (String key : averages.keySet()) {
        	averageModel.set(key, averages.get(key));
    	}
         
        averageModel.setTitle("Average per device");
        averageModel.setLegendPosition("w");
        averageModel.setShowDataLabels(true);
    }
	
	//filter methods
	
	public void filtersChange() {
		System.out.println(startDate);
		System.out.println(endDate);
		createValueModel();
		createAverageModel();
	}
	
	public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
	
	//utility methods

	private String printTomorrow() {
		Date dt = new Date();
        Calendar c = Calendar.getInstance(); 
        c.setTime(dt); 
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        String tomorrow = new SimpleDateFormat("yyyy-MM-dd").format(dt);
		return tomorrow;
	}

}