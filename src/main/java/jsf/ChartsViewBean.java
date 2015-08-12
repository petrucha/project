package jsf;
 
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import entity.Record;
import service.RecordService;
 
public class ChartsViewBean extends AbstractBean implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private static  RecordService instance =  RecordService.getInstance();
	
	private LineChartModel areaModel;
 
	public ChartsViewBean() {
		 createAreaModel();
	}
 
    public LineChartModel getAreaModel() {
        return areaModel;
    }
     
    private void createAreaModel() {
        areaModel = new LineChartModel();
 
        LineChartSeries quantity = new LineChartSeries();
        quantity.setFill(true);
        quantity.setLabel("Quantity");
      
 
        LineChartSeries value = new LineChartSeries();
        value.setFill(true);
        value.setLabel("Value");

        
        List<Record> records = instance.getRecords();
        for (Record rec : records) {
        	String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rec.getTimestamp());
        	Double quan = Double.parseDouble(rec.getQuantity());
        	quantity.set(date, quan);
        	value.set(date, rec.getValue());
        }
 
        areaModel.addSeries(quantity);
        areaModel.addSeries(value);
         
        areaModel.setTitle("Area Chart");
        areaModel.setLegendPosition("ne");
        areaModel.setStacked(true);
        areaModel.setShowPointLabels(true);
         
        Axis xAxis = new CategoryAxis("Date");
        areaModel.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = areaModel.getAxis(AxisType.Y);
        yAxis.setLabel("");
        yAxis.setMin(0);
        yAxis.setMax(300);
    }

	public void setAreaModel(LineChartModel areaModel) {
		this.areaModel = areaModel;
	}
     
}