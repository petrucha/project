package service;

import org.junit.Assert;
import org.junit.Test;
import java.util.Date;

import entity.Record;

public class RecordServiceTest {

    private static RecordService recordService = RecordService.getInstance();

    @Test
    public void testAddRecordAndGetRecordAndDeleteRecord() {
    	Record record = new Record("Samsung", "111", 222, new Date().getTime());
    	recordService.addRecord(record);
    	
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
  
}
