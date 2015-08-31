package service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import entity.Device;
import entity.Record;
import util.DateUtil;
import util.TestUtil;

public class RecordServiceTest {

    private static RecordService recordService = RecordService.getInstance();
    
    private static DeviceService deviceService = DeviceService.getInstance();
    
    Device device = null;
    Record record = null;
    
    @Before
    public void createDeviceAndRecord() {
    	device = new Device(TestUtil.randomString(4));
    	deviceService.addDevice(device);
    	record = new Record(device, "test", TestUtil.randInt(-100, 100), DateUtil.dateToTimestamp(new Date()));
    	recordService.addRecord(record);
    }
    
//    @BeforeClass
    public static void createDevicesAndRecords() {
    	for (int j=0; j<30; j++) {
    		Device dev = new Device(TestUtil.randomString(4));
        	deviceService.addDevice(dev);
        	Calendar c = Calendar.getInstance();
        	Date dt = new Date();
    		c.setTime(dt);
    		c.add(Calendar.HOUR, -1);
    		for (int i=0; i<100; i++) {
    			c.add(Calendar.SECOND, 10);
    			dt = c.getTime();
    			double timestamp = DateUtil.dateToTimestamp(dt);
    			Record rec1 = new Record(dev, "temp", TestUtil.randInt(-100, 100), timestamp);
    			Record rec2 = new Record(dev, "humi", TestUtil.randInt(-100, 100), timestamp);
    			Record rec3 = new Record(dev, "pres", TestUtil.randInt(-100, 100), timestamp);
    			recordService.addRecord(rec1);
    			recordService.addRecord(rec2);
    			recordService.addRecord(rec3);
    		}
    	}
    }
    
    @After
    public void removeDeviceAndRecord() {
    	recordService.deleteRecord(record);
    	deviceService.deleteDevice(device);
    }

    @Test
    public void testAddAndGetRecord() {
    	Assert.assertTrue(record.getId() != 0);
        Record created = recordService.getRecord(record.getId());
        Assert.assertNotNull(created);
        System.out.println(created);
        
        Assert.assertEquals(created.getDevice(), record.getDevice());
        Assert.assertEquals(created.getId(), record.getId());
        Assert.assertEquals(created.getQuantity(), record.getQuantity());
        Assert.assertEquals(created.getValue(), record.getValue());
        Assert.assertEquals(created.getTimestamp(), record.getTimestamp(), 0.01); //the last argument is epsilon
    }
    
    @Test
    public void testDeleteRecord() {
    	Device device = new Device(TestUtil.randomString(4));
    	Record created = new Record(device, "test", 222, DateUtil.dateToTimestamp(new Date()));
    	
        recordService.deleteRecord(created);
        Assert.assertNull(recordService.getRecord(created.getId()));
    }
    
    @Test
    public void testGetRecordsByDevicesAndTime() {
    	String[] devices = {device.getMac()};
    	List<Record[]> records = recordService.getRecordsForLineChart(devices, "test", DateUtil.getYesterday(), new Date());

    	Assert.assertTrue(records.size() > 0);
    	for (Record[] recs : records) {
    		Assert.assertTrue(recs.length > 0);
    		for (Record rec : recs) {
				System.out.println(rec);
			}
		}	
    	//getting "default record's device" and checks is it right 
    	Device drd = records.get(0)[0].getDevice();
    	Assert.assertEquals(drd, device);
    }
    
    @Test
    public void testGetFilteredAverages() {
    	String[] devices = {device.getMac()};
    	HashMap<String, Double> averages = recordService.getFilteredAverages(devices, "test", DateUtil.getYesterday(), new Date());
    	
    	Assert.assertTrue(averages.size() > 0);
    	
    	for (String key : averages.keySet()) {
    		System.out.println("<\""+key+"\", "+averages.get(key)+">");
    	}
    }
    
    @Test
    public void testGetLastRecords() {
    	String[] devices = {device.getMac()};
    	//e.g. lets get last 5 records
    	int recordsCount = 5;
    	List<Record[]> records = recordService.getLastRecords(devices, "test", recordsCount);
    	
    	Assert.assertTrue(records.size() > 0);
    	for (Record[] recs : records) {
    		Assert.assertTrue((recs.length > 0) && (recs.length <= recordsCount));
    		for (Record rec : recs) {
				System.out.println(rec);
			}
		}	
    	//getting "default record's device" and checks is it right 
    	Device drd = records.get(0)[0].getDevice();
    	Assert.assertEquals(drd, device);
    }
    
    @Test 
    public void testCountLastRecords() {
    	Date startDate = DateUtil.timestampToDate(record.getTimestamp());
    	int number = recordService.getNumberOfLastRecords(null, startDate);
    	Assert.assertEquals(number, 1);
    	number = recordService.getNumberOfLastRecords("notexisting", startDate);
    	Assert.assertEquals(number, 0);
    }
  
}
