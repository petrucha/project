package service;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import entity.Device;

import util.TestUtil;

public class DeviceServiceTest {

	private static DeviceService deviceService = DeviceService.getInstance();

	@Test
	public void testAddAndGetAndDeleteDevice() {
		Device device = new Device(TestUtil.randomMacAddress());
		deviceService.addDevice(device);
		Assert.assertTrue(device.getId() != 0);
		
		Device created = deviceService.getDevice(device.getId());
		Assert.assertNotNull(created);
		System.out.println(created);

		Assert.assertEquals(created.getMac(), device.getMac());
		Assert.assertEquals(created.getId(), device.getId());
		
		deviceService.deleteDevice(created);
		Assert.assertNull(deviceService.getDevice(created.getId()));
	}
	
	@Test
	public void testGetAllDevices() {
		Device device = new Device(TestUtil.randomMacAddress());
		deviceService.addDevice(device);
		
		List<Device> devices = deviceService.getAllDevices(false);
		Assert.assertTrue(devices.size() > 0);
		Assert.assertNotNull(devices.get(0));
		
		devices = deviceService.getAllDevices(true);
		Assert.assertFalse(devices.contains(device));
		
		deviceService.deleteDevice(device);
		Assert.assertNull(deviceService.getDevice(device.getId()));
	}
	
}