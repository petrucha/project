package service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import entity.Device;
import entity.Group;
import entity.Record;
import entity.User;
import util.TestUtil;


public class UserServiceTest {

    private static UserService userService = UserService.getInstance();
    
    private static GroupService groupService = GroupService.getInstance();
    
    private static DeviceService deviceService = DeviceService.getInstance();

    @Test
    public void testAddAndGetAndDeleteUser() {
    	Group group = new Group("ADMIN", "testgroupdesc");
    	groupService.addGroup(group);
    	Device device = new Device(TestUtil.randomMacAddress());
		deviceService.addDevice(device);
		
		List<Device> devices = deviceService.getAllDevices(false);
    	User user = new User("testusername1", "testpassword1", "testfirstname1",
    			"testlastname1", new Date(), "email1@email.com",
    			group, new HashSet<Device>(devices));
    	
    	userService.addUser(user);
    	Assert.assertTrue(user.getId() != 0);
        User createdUser = userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        Assert.assertNotNull(createdUser);
        System.out.println(createdUser);
        
        Assert.assertEquals(createdUser.getEmail(), user.getEmail());
        Assert.assertEquals(createdUser.getFirstname(), user.getFirstname());
        Assert.assertEquals(createdUser.getLastname(), user.getLastname());
        Assert.assertEquals(createdUser.getPassword(), user.getPassword());
        Assert.assertEquals(createdUser.getUsername(), user.getUsername());
        Assert.assertEquals(createdUser.getBirthday(), user.getBirthday());
        Assert.assertEquals(createdUser.getDevices(), user.getDevices());
        Assert.assertEquals(createdUser.getGroup(), user.getGroup());
        Assert.assertEquals(createdUser.getId(), user.getId());
        
        userService.deleteUser(user);
		Assert.assertNull(userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword()));
    }
    
  
}
