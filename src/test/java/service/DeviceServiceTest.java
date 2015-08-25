package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import data.DeviceData;
import entity.Device;
import entity.Group;
import entity.User;
import util.TestUtil;

public class DeviceServiceTest {

	private static UserService userService = UserService.getInstance();
	private static DeviceService deviceService = DeviceService.getInstance();
	private static GroupService groupService = GroupService.getInstance();

	@Test
	public void testAddAndGetAndDeleteDevice() {
		Device device = new Device(TestUtil.randomString(4));
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
		Device device = new Device(TestUtil.randomString(4));
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
		Device device = new Device(TestUtil.randomString(4));
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
		Group group = new Group("ADMIN1", "testgroupdesc1");
    	groupService.addGroup(group);
      	User user = new User(TestUtil.randomString(6)+"name",
				TestUtil.randomString(6)+"pass", TestUtil.randomString(6)+"first",
				TestUtil.randomString(6)+"last", new Date(), 
				TestUtil.randomString(6)+"@email.com",
    			group, new HashSet<Device>());
    	userService.addUser(user);
    	
		Device device = new Device(TestUtil.randomString(4));
		device.getUsers().add(user);
		deviceService.addDevice(device);
		
		List<DeviceData> dds = deviceService.getUserDevicesData(user);
		Assert.assertTrue(dds.size() > 0);
		Assert.assertNotNull(dds.get(0));
		for (DeviceData deviceData : dds) {
			System.out.println(deviceData);
		}
		
		deviceService.deleteDevice(device);
		Assert.assertNull(deviceService.getDevice(device.getId()));
	}
	
	@Test
	public void testAddDeviceToUsers() {
		User user1 = new User(TestUtil.randomString(6)+"name",
				TestUtil.randomString(6)+"pass", TestUtil.randomString(6)+"first",
				TestUtil.randomString(6)+"last", new Date(), 
				TestUtil.randomString(6)+"@email.com",
    			null, null);
		
		User user2 = new User(TestUtil.randomString(6)+"name",
				TestUtil.randomString(6)+"pass", TestUtil.randomString(6)+"first",
				TestUtil.randomString(6)+"last", new Date(), 
				TestUtil.randomString(6)+"@email.com",
    			null, null);
		
		User user3 = new User(TestUtil.randomString(6)+"name",
				TestUtil.randomString(6)+"pass", TestUtil.randomString(6)+"first",
				TestUtil.randomString(6)+"last", new Date(), 
				TestUtil.randomString(6)+"@email.com",
    			null, null);
		
    	userService.addUser(user1);
    	userService.addUser(user2);
     	userService.addUser(user3);
     	
     	Device device = new Device(TestUtil.randomString(4));
     	device.getUsers().add(user1);
		deviceService.addDevice(device);
    	
    	List<String> usernames = new ArrayList<String>();
    	usernames.add(user1.getUsername());
    	usernames.add(user2.getUsername());
    	
    	deviceService.addDeviceToUsers(device, usernames);
    	user1 = userService.getUserById(user1.getId());
    	user2 = userService.getUserById(user2.getId());
    	user3 = userService.getUserById(user3.getId());
    	
      	Assert.assertTrue(user1.getDevices().contains(device));
    	Assert.assertTrue(user2.getDevices().contains(device));
     	Assert.assertFalse(user3.getDevices().contains(device));
    	
    	userService.deleteUser(user1);
		Assert.assertNull(userService.getUserByUsernameAndPassword(user1.getUsername(), user1.getPassword()));
		userService.deleteUser(user2);
		Assert.assertNull(userService.getUserByUsernameAndPassword(user2.getUsername(), user2.getPassword()));
		userService.deleteUser(user3);
		Assert.assertNull(userService.getUserByUsernameAndPassword(user3.getUsername(), user3.getPassword()));
		deviceService.deleteDevice(device);
		Assert.assertNull(deviceService.getDevice(device.getId()));
	}
	
	@Test
	public void testGetMacsByUser() {
		User user = new User(TestUtil.randomString(6)+"name",
				TestUtil.randomString(6)+"pass", TestUtil.randomString(6)+"first",
				TestUtil.randomString(6)+"last", new Date(), 
				TestUtil.randomString(6)+"@email.com",
    			null, null);
		userService.addUser(user);
		Device device = new Device(TestUtil.randomString(4));
     	device.getUsers().add(user);
		deviceService.addDevice(device);
		
		List<String> macs = deviceService.getMacsByUser(user.getUsername());
		Assert.assertTrue(macs.contains(device.getMac()));
    	Assert.assertEquals(macs.size(), 1);
		
		userService.deleteUser(user);
		Assert.assertNull(userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword()));
		deviceService.deleteDevice(device);
		Assert.assertNull(deviceService.getDevice(device.getId()));
	}
	
	@Test
    public void testIsDeviceExist() {
		Device device = new Device(TestUtil.randomString(4));
		deviceService.addDevice(device);
    	
    	Assert.assertTrue(deviceService.isDeviceExist(device.getMac()));
    	Assert.assertFalse(deviceService.isDeviceExist("no" + device.getMac()));
    	
    	deviceService.deleteDevice(device);
		Assert.assertNull(deviceService.getDevice(device.getId()));
    }
	
}