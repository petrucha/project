package service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import entity.Record;

public class RecordServiceTest {

    private static RecordService recordService = RecordService.getInstance();
    
    Record record = null;
    
    @Before
    public void createRecord() {
    	record = new Record("AAAA", "111", 222, new Date().getTime());
    	recordService.addRecord(record);
    }
    
    @After
    public void removeRecord() {
    	recordService.deleteRecord(record);
    }

    @Test
    public void testAddAndGetRecord() {
    	Assert.assertTrue(record.getId() != 0);
        Record created = recordService.getRecordById(record.getId());
        Assert.assertNotNull(created);
        System.out.println(created);
        
        Assert.assertEquals(created.getDevice(), record.getDevice());
        Assert.assertEquals(record.getId(), created.getId());
        Assert.assertEquals(created.getQuantity(), record.getQuantity());
        Assert.assertEquals(created.getValue(), record.getValue());
        Assert.assertEquals(created.getTimestamp(), record.getTimestamp(), 0.01); //the last argument is epsilon
    }
    
    @Test
    public void testDeleteRecord() {
    	Record created = new Record("CCCC", "111", 222, new Date().getTime());
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
    }
    
    @Test
    public void testGetRecordsByDevicesArray() {
    	//preparations for devices
    	Record record1 = new Record("BBBB", "111", 222, new Date().getTime());
    	recordService.addRecord(record1);
    	String[] devices = {"AAAA", "BBBB"};
    	//preparations for time
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	Date startTime = null;
		try {
			startTime = sdf.parse("01/01/2000");
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	Date endTime = new Date();
    	//doing the operation
    	List<Record[]> records = recordService.getRecordsByDevicesAndTime(devices, startTime.getTime(), endTime.getTime());
    	//assertions
    	Assert.assertTrue(records.size() > 0);
    	for (Record[] rec : records) {
    		Assert.assertTrue(rec.length > 0);
    		for (int i = 0; i < rec.length; i++) {
    			System.out.println(rec[i]);
    		}
		}	
    	//getting "default record's device" and checks is it right 
    	String drd = records.get(0)[0].getDevice();
    	Assert.assertTrue(drd.equals(devices[0]) || drd.equals(devices[1]));
    	//deleting created in the testcase record
    	recordService.deleteRecord(record1);
    }
    
    @Test
    public void testGetFilteredAverages() {
    	//preparations for devices
    	Record record1 = new Record("BBBB", "111", 222, new Date().getTime());
    	recordService.addRecord(record1);
    	String[] devices = {"AAAA", "BBBB"};
    	//preparations for time
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	Date startTime = null;
		try {
			startTime = sdf.parse("01/01/2000");
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	Date endTime = new Date();
    	//doing the operation
    	HashMap<String, Double> averages = recordService.getFilteredAverages(devices, startTime.getTime(), endTime.getTime());
    	//assertions
    	Assert.assertTrue(averages.size() > 0);
    	//printing
    	for (String key : averages.keySet()) {
    		System.out.println("<\""+key+"\", "+averages.get(key)+">");
    	}
    	//deleting created in the testcase record
//    	recordService.deleteRecord(record1);
    }
  
}
