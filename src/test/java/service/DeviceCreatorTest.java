package service;


import org.junit.Test;

import entity.Device;
import util.TestUtil;
import entity.Device;


public class DeviceCreatorTest {
	private static DeviceService deviceService = DeviceService.getInstance();

	@Test
	public void testAddAndGetAndDeleteDevice() {
		for(int i=0; i<10;i++){
		Device device = new Device(TestUtil.randomMacAddress());
		deviceService.addDevice(device);}

	}
}
