package rest.data;

import java.util.List;

import entity.Device;
import entity.Record;

public class RecordDataHelper {

	private static RecordDataHelper instanceHelper;

	public static synchronized RecordDataHelper getInstance() {
		if (instanceHelper == null) {
			instanceHelper = new RecordDataHelper();
		}
		return instanceHelper;
	}

	public RecordData[] recordsToRecordsData(List<Record> records) {
		int size = records.size();
		RecordData[] arrayRecordData = new RecordData[size];

		for (int i = 0; i < size; i++) {
			System.out.println(records.get(i).toString());
			arrayRecordData[i] = new RecordData();
			arrayRecordData[i].setDevice(records.get(i).getDevice().getMac());
			arrayRecordData[i].setQuantity(records.get(i).getQuantity());
			arrayRecordData[i].setValue(records.get(i).getValue());
			arrayRecordData[i].setTimestamp(records.get(i).getTimestamp());
			System.out.println(arrayRecordData[i].toString());
		}
		
		return arrayRecordData;
	}

	public RecordData recordToRecordData(Record record) {
		RecordData recordData = new RecordData();
		recordData.setDevice(record.getDevice().getMac());
		recordData.setQuantity(record.getQuantity());
		recordData.setValue(record.getValue());
		recordData.setTimestamp(record.getTimestamp());
		
		return recordData;
	}

	public Record recordDataToRecord(RecordData recordData) {
		Device device = new Device(recordData.getDevice());
		Record record = new Record();
		record.setDevice(device);
		record.setQuantity(recordData.getQuantity());
		record.setTimestamp(recordData.getTimestamp());
		record.setValue(recordData.getValue());
		
		return record;
	}
}
