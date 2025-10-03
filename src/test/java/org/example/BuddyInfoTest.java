package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class BuddyInfoTest {

    @Test
    public void getName() {
        BuddyInfo buddy = new BuddyInfo("Alice", "123-456-7890");
        Assertions.assertEquals("Alice", buddy.getName());
    }

    @Test
    public void getPhoneNumber() {
        BuddyInfo buddy = new BuddyInfo("Alice", "123-456-7890");
        Assertions.assertEquals("123-456-7890", buddy.getPhoneNumber());

    }

    @Test
    public void testToString() {
        BuddyInfo buddy = new BuddyInfo("Alice", "123-456-7890");
        String expectedString = "BuddyInfo{id=null, name='Alice', phoneNumber='123-456-7890'}";
        Assertions.assertEquals(expectedString, buddy.toString());
    }
}