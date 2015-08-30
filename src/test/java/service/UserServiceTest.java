package service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
      	User user = new User(TestUtil.randomString(6)+"name",
				TestUtil.randomString(6)+"pass", TestUtil.randomString(6)+"first",
				TestUtil.randomString(6)+"last", new Date(), 
				TestUtil.randomString(6)+"@email.com",
    			group, new HashSet<Device>());
    	userService.addUser(user);
    	
    	Device device = new Device(TestUtil.randomString(4));
    	device.getUsers().add(user);
    	deviceService.addDevice(device);
    
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
		User user1 = new User(TestUtil.randomString(6)+"name",
				TestUtil.randomString(6)+"pass", TestUtil.randomString(6)+"first",
				TestUtil.randomString(6)+"last", new Date(), 
				TestUtil.randomString(6)+"@email.com",
    			null, new HashSet<Device>());
		
		User user2 = new User(TestUtil.randomString(6)+"name",
				TestUtil.randomString(6)+"pass", TestUtil.randomString(6)+"first",
				TestUtil.randomString(6)+"last", new Date(), 
				TestUtil.randomString(6)+"@email.com",
    			null, null);
		
    	userService.addUser(user1);
    	userService.addUser(user2);
    	
    	Device device = new Device(TestUtil.randomString(4));
    	device.getUsers().add(user1);
		deviceService.addDevice(device);
		
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
    
    @Test
    public void testIsUserExist() {
    	User user = new User(TestUtil.randomString(6)+"name",
				TestUtil.randomString(6)+"pass", TestUtil.randomString(6)+"first",
				TestUtil.randomString(6)+"last", new Date(), 
				TestUtil.randomString(6)+"@email.com",
    			null, new HashSet<Device>());
    	userService.addUser(user);
    	
    	Assert.assertTrue(userService.isUserExist(user.getUsername()));
    	Assert.assertFalse(userService.isUserExist("no" + user.getUsername()));
    	
    	userService.deleteUser(user);
		Assert.assertNull(userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword()));
    }
    
}
