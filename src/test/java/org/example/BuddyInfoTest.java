package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BuddyInfoTest {

    @Test
    public void getName() {
        BuddyInfo buddy = new BuddyInfo("Alice", "123-456-7890", "123 Dynes Road");
        Assertions.assertEquals("Alice", buddy.getName());
    }

    @Test
    public void getPhoneNumber() {
        BuddyInfo buddy = new BuddyInfo("Alice", "123-456-7890", "123 Dynes Road");
        Assertions.assertEquals("123-456-7890", buddy.getPhoneNumber());
    }

    @Test
    public void testToString() {
        BuddyInfo buddy = new BuddyInfo("Alice", "123-456-7890", "123 Dynes Road");
        String expectedString = "BuddyInfo{id=null, name='Alice', phoneNumber='123-456-7890', address='123 Dynes Road'}";
        Assertions.assertEquals(expectedString, buddy.toString());
    }
}