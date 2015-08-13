package jsf;
 
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import entity.Record;
import service.RecordService;
 
public class ChartsViewBean extends AbstractBean implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private static  RecordService instance =  RecordService.getInstance();
	
	private CartesianChartModel lineModel1;
    private LineChartModel lineModel2;
 
	public ChartsViewBean() {
		createLineModels();
	}
 
	public CartesianChartModel getLineModel1() {
        return lineModel1;
    }
 
    public LineChartModel getLineModel2() {
        return lineModel2;
    }
     
//    private void createLineModels() {
//    	new LineChartSeries();
//        quantity.setFill(true);
//        quantity.setLabel("Quantity");
//      
// 
//        LineChartSeries value = new LineChartSeries();
//        value.setFill(true);
//        value.setLabel("Value");
//
//        
//        List<Record> records = instance.getRecords();
//        for (Record rec : records) {
//        	String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rec.getTimestamp());
//        	Double quan = Double.parseDouble(rec.getQuantity());
//        	quantity.set(date, quan);
//        	value.set(date, rec.getValue());
//        }
// 
//        areaModel.addSeries(quantity);
//        areaModel.addSeries(value);
//         
//        areaModel.setTitle("Area Chart");
//        areaModel.setLegendPosition("ne");
//        areaModel.setStacked(true);
//        areaModel.setShowPointLabels(true);
//         
//        Axis xAxis = new CategoryAxis("Date");
//        areaModel.getAxes().put(AxisType.X, xAxis);
//        Axis yAxis = areaModel.getAxis(AxisType.Y);
//        yAxis.setLabel("");
//        yAxis.setMin(0);
//        yAxis.setMax(300);
//    }
    
    private void createLineModels() {
        lineModel1 = initLinearModel();
        lineModel1.setTitle("Linear Chart");
        lineModel1.setLegendPosition("e");
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
         
        lineModel2 = initCategoryModel();
        lineModel2.setTitle("Category Chart");
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }
     
    private CartesianChartModel initLinearModel() {
        CartesianChartModel model = new CartesianChartModel();
 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
 
        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);
 
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");
 
        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);
 
        model.addSeries(series1);
        model.addSeries(series2);
         
        return model;
    }
     
    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();
 
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);
 
        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 90);
        girls.set("2008", 120);
 
        model.addSeries(boys);
        model.addSeries(girls);
         
        return model;
    }

	public void setLineModel1(CartesianChartModel lineModel1) {
		this.lineModel1 = lineModel1;
	}

	public void setLineModel2(LineChartModel lineModel2) {
		this.lineModel2 = lineModel2;
	}
     
}