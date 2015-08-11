package rest.data;

import entity.Record;

public class RecordDataHelper {

	private static RecordDataHelper instanceHelper;

	public static synchronized RecordDataHelper getInstance() {
		if (instanceHelper == null) {
			instanceHelper = new RecordDataHelper();
		}
		return instanceHelper;
	}

public RecordData[] recordsToRecordsData(Record[] records){
	int size = records.length;
	 RecordData[] arrayRecordData = new RecordData[size];

	for(int i=0; i<size; i++){
		System.out.println(records[i].toString());
	    arrayRecordData[i]= new  RecordData();
		arrayRecordData[i].setDevice(records[i].getDevice());
		arrayRecordData[i].setQuantity(records[i].getQuantity());
		arrayRecordData[i].setValue(records[i].getValue());
		arrayRecordData[i].setTimestamp(records[i].getTimestamp());
		System.out.println(arrayRecordData[i].toString());
	}
	return arrayRecordData;
}

public  RecordData recordToRecordData(Record record){
	 RecordData recordData= new RecordData();
	    recordData.setDevice(record.getDevice());
	    recordData.setQuantity(record.getQuantity());
	    recordData.setValue(record.getValue());
	    recordData.setTimestamp(record.getTimestamp());			
	return recordData;
}
}
