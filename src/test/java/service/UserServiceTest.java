package service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import entity.Device;
import entity.Group;
import entity.User;
import util.TestUtil;


public class UserServiceTest {

    private static UserService userService = UserService.getInstance();
    
    private static GroupService groupService = GroupService.getInstance();
    
    private static DeviceService deviceService = DeviceService.getInstance();

    @Test
    public void testAddAndGetAndDeleteUser() {
    	Group group = new Group("ADMIN1", "testgroupdesc1");
    	groupService.addGroup(group);
    	Device device = new Device(TestUtil.randomMacAddress());
		deviceService.addDevice(device);
		
		List<Device> devices = deviceService.getAllDevices(false);
    	User user = new User("testusername10", "testpassword1", "testfirstname1",
    			"testlastname1", new Date(), "email1@email.com",
    			group, new HashSet<Device>(devices));
    	
    	userService.addUser(user);
    	Assert.assertTrue(user.getId() != 0);
        User createdUser = userService.getUserById(user.getId());
        Assert.assertNotNull(createdUser);
        System.out.println(createdUser);
        
        Assert.assertEquals(createdUser.getEmail(), user.getEmail());
        Assert.assertEquals(createdUser.getFirstname(), user.getFirstname());
        Assert.assertEquals(createdUser.getLastname(), user.getLastname());
        Assert.assertEquals(createdUser.getPassword(), user.getPassword());
        Assert.assertEquals(createdUser.getUsername(), user.getUsername());
        Assert.assertTrue(createdUser.getDevices().contains(device));
        Assert.assertEquals(createdUser.getGroup(), user.getGroup());
        Assert.assertEquals(createdUser.getId(), user.getId());
        
        userService.deleteUser(user);
		Assert.assertNull(userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword()));
    }
    
    @Test
    public void testGetUsernamesByDevice() {
    	Device device = new Device(TestUtil.randomMacAddress());
		deviceService.addDevice(device);
		Set<Device> userDevices = new HashSet<Device>();
		userDevices.add(device);
		
		User user1 = new User("testusername00001", "testpassword1", "testfirstname1",
    			"testlastname1", new Date(), "email1@email.com",
    			null, userDevices);
		
		User user2 = new User("testusername00002", "testpassword2", "testfirstname2",
    			"testlastname2", new Date(), "email2@email.com",
    			null, null);
		
    	userService.addUser(user1);
    	userService.addUser(user2);
    	
    	List<String> usernames = userService.getUsernamesByDevice(device.getId());
    	Assert.assertTrue(usernames.contains(user1.getUsername()));
    	Assert.assertFalse(usernames.contains(user2.getUsername()));
    	Assert.assertTrue(usernames.size() == 1);
    	
    	usernames = userService.getUsernamesNotHavingDevice(device.getId());
    	Assert.assertTrue(usernames.contains(user2.getUsername()));
    	Assert.assertFalse(usernames.contains(user1.getUsername()));
    	Assert.assertTrue(usernames.size() > 0);
    	
    	userService.deleteUser(user1);
		Assert.assertNull(userService.getUserByUsernameAndPassword(user1.getUsername(), user1.getPassword()));
		userService.deleteUser(user2);
		Assert.assertNull(userService.getUserByUsernameAndPassword(user2.getUsername(), user2.getPassword()));
		deviceService.deleteDevice(device);
		Assert.assertNull(deviceService.getDevice(device.getId()));
    }
    
  
}
