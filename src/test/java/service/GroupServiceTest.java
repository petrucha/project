package service;

import org.junit.Assert;
import org.junit.Test;

import entity.Group;

public class GroupServiceTest {

    private static GroupService groupService = GroupService.getInstance();

    @Test
    public void testGetGroupByName() {
    	Group group = new Group("ADMIN", "testgroupdesc");
    	groupService.addGroup(group);
    	
        Group created = groupService.getGroupByName("ADMIN");
        Assert.assertNotNull(group);
        System.out.println(group.getUsers().size());
        
        groupService.deleteGroup(created);
        Assert.assertNull(groupService.getGroupByName("ADMIN"));
    }
  
}
