package jsf;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

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

	private LineChartModel valueModel;

	public ChartsViewBean() {
		createValueModel();
	}

	public LineChartModel getValueModel() {
		return valueModel;
	}

	public void setValueModel(LineChartModel valueModel) {
		this.valueModel = valueModel;
	}

	private void createValueModel() {
		Axis yAxis = null;

		valueModel = initCategoryModel();
		valueModel.setTitle("Temperature Chart");
		valueModel.setLegendPosition("e");
		valueModel.setShowPointLabels(true);
		valueModel.getAxes().put(AxisType.X, new CategoryAxis("Date"));
		yAxis = valueModel.getAxis(AxisType.Y);
		yAxis.setLabel("Temperature C\u00b0");
		// yAxis.setMin(0);
		// yAxis.setMax(200);
	}

	private LineChartModel initCategoryModel() {
		LineChartModel model = new LineChartModel();

		ChartSeries values = new ChartSeries();
		values.setLabel("Values");

		List<Record> records = instance.getRecords(); // do change to
														// getRecordsByDevice()
		// creating value data for chart series

		for (Record rec : records) {
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rec.getTimestamp());
			// Double quan = Double.parseDouble(rec.getQuantity());
			values.set(date, rec.getValue());
		}

		model.addSeries(values);

		return model;
	}

}