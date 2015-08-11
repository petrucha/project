package jsf;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.context.RequestContext;

import entity.Record;
import service.RecordService;


public class RecordsViewBean  extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static  RecordService instance =  RecordService.getInstance();
	private List<Record> records = new ArrayList<Record>();

	public RecordsViewBean() {
	}

	public List<Record> getRecords() {
		records = instance.getRecords();
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public String refresh() {
		return "refresh";

	}

	public void update() {
		RequestContext.getCurrentInstance().update("table");
	}
}
