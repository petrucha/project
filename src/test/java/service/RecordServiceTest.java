package service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import entity.Record;

public class RecordServiceTest {

    private static RecordService recordService = RecordService.getInstance();
    
    Record record = null;
    
    @Before
    public void addRecord() {
    	record = new Record("AAAA", "111", 222, new Date().getTime());
    	recordService.addRecord(record);
    }

    @Test
    public void testAddRecordAndGetRecordAndDeleteRecord() {
    	Assert.assertTrue(record.getId() != 0);
        Record created = recordService.getRecordById(record.getId());
        Assert.assertNotNull(created);
        System.out.println(created);
        
        Assert.assertEquals(created.getDevice(), record.getDevice());
        Assert.assertEquals(record.getId(), created.getId());
        Assert.assertEquals(created.getQuantity(), record.getQuantity());
        Assert.assertEquals(created.getValue(), record.getValue());
        Assert.assertEquals(created.getTimestamp(), record.getTimestamp(), 0.01); //the last argument is epsilon
        
        recordService.deleteRecord(created);
        Assert.assertNull(recordService.getRecordById(created.getId()));
    }
    
    @Test
    public void testGetDevicesArray() {
    	String[] devices = recordService.getDevicesArray();
    	
    	Assert.assertTrue(devices.length > 0);
    	Assert.assertTrue(Arrays.asList(devices).contains(record.getDevice()));
    	
    	for (int i = 0; i < devices.length; i++) {
			System.out.println(devices[i]);
		}
    	
    	recordService.deleteRecord(record);
    }
    
    @Test
    public void testGetRecordsByTimestamp() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	Date startTime = null;
		try {
			startTime = sdf.parse("01/01/2000");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Date endTime = new Date();
    	
    	Record[] records = recordService.getRecordsByTimestamp(startTime.getTime(), endTime.getTime());
    	
    	for (int i = 0; i < records.length; i++) {
			System.out.println(records[i]);
		}
    	
    	Assert.assertTrue(records.length > 0);
    }
  
}
