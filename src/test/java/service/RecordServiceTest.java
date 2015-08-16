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
import java.util.Random;

import entity.Device;
import entity.Record;

public class RecordServiceTest {

    private static RecordService recordService = RecordService.getInstance();
    
    private static DeviceService deviceService = DeviceService.getInstance();
    
    Device device = null;
    Record record = null;
    
    @Before
    public void createRecord() {
    	device = new Device(randomMacAddress());
    	deviceService.addDevice(device);
    	record = new Record(device, "111", 222, new Date().getTime());
    	recordService.addRecord(record);
    }
    
    @After
    public void removeRecord() {
    	recordService.deleteRecord(record);
//    	deviceService.deleteDevice(device);
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
    
//    @Test
//    public void testGetDevicesArray() {
//    	String[] devices = recordService.getDevicesArray();
//    	
//    	Assert.assertTrue(devices.length > 0);
//    	Assert.assertTrue(Arrays.asList(devices).contains(record.getDevice()));
//    	
//    	for (int i = 0; i < devices.length; i++) {
//			System.out.println(devices[i]);
//		}
//    }
    
    @Test
    @Ignore
    public void testGetRecordsByDevicesArray() {
    	//preparations for devices
    	Device device = new Device("CC-B0-D0-86-BB-F7");
    	Record record1 = new Record(device, "111", 222, new Date().getTime());
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
    	String drd = records.get(0)[0].getDevice().getMac();
    	Assert.assertTrue(drd.equals(devices[0]) || drd.equals(devices[1]));
    	//deleting created in the testcase record
    	recordService.deleteRecord(record1);
    }
    
    @Test
    @Ignore
    public void testGetFilteredAverages() {
    	//preparations for devices
    	Device device = new Device("CC-B0-D0-86-BB-F7");
    	Record record1 = new Record(device, "111", 222, new Date().getTime());
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
    	recordService.deleteRecord(record1);
    }
    
    @Test
    @Ignore
    public void testGetLastRecords() {
    	String[] devices = {"AAAA"};
    	//e.g. lets get last 5 records
    	int recordsCount = 5;
    	List<Record[]> records = recordService.getLastRecords(devices, recordsCount);
    	//assertions
    	Assert.assertTrue(records.size() > 0);
    	for (Record[] rec : records) {
    		Assert.assertTrue((rec.length > 0) && (rec.length <= recordsCount));
    		for (int i = 0; i < rec.length; i++) {
    			System.out.println(rec[i]);
    		}
		}	
    	//getting "default record's device" and checks is it right 
    	String drd = records.get(0)[0].getDevice().getMac();
    	Assert.assertTrue(drd.equals(devices[0]) || drd.equals(devices[1]));
    }
    
    private String randomMacAddress(){
        Random rand = new Random();
        byte[] macAddr = new byte[6];
        rand.nextBytes(macAddr);

        macAddr[0] = (byte)(macAddr[0] & (byte)254);  //zeroing last 2 bytes to make it unicast and locally adminstrated

        StringBuilder sb = new StringBuilder(18);
        for(byte b : macAddr){

            if(sb.length() > 0)
                sb.append(":");

            sb.append(String.format("%02x", b));
        }


        return sb.toString();
    }
  
}
