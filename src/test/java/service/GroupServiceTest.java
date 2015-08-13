package service;

import org.junit.Assert;
import org.junit.Test;

import entity.Group;

public class GroupServiceTest {

    private static GroupService groupService = GroupService.getInstance();

    @Test
    public void testGetGroupByName() {
        Group group = groupService.getGroupByName("testname");
        Assert.assertNotNull(group);
        System.out.println(group);
    }
  
}
