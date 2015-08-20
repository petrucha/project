package service;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import data.DeviceData;
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
	
	@Test
	public void testGetAllMacs() {
		Device device = new Device(TestUtil.randomMacAddress());
		deviceService.addDevice(device);
		
		List<String> macs = deviceService.getAllMacs(false);
		Assert.assertTrue(macs.size() > 0);
		Assert.assertNotNull(macs.get(0));
		
		macs = deviceService.getAllMacs(true);
		Assert.assertFalse(macs.contains(device));
		
		deviceService.deleteDevice(device);
		Assert.assertNull(deviceService.getDevice(device.getId()));
	}
	
	@Test
	public void testGetDevicesData() {
		Device device = new Device(TestUtil.randomMacAddress());
		deviceService.addDevice(device);
		
		List<DeviceData> dds = deviceService.getDevicesData();
		Assert.assertTrue(dds.size() > 0);
		Assert.assertNotNull(dds.get(0));
		for (DeviceData deviceData : dds) {
			System.out.println(deviceData);
		}
		
		deviceService.deleteDevice(device);
		Assert.assertNull(deviceService.getDevice(device.getId()));
	}
	
}