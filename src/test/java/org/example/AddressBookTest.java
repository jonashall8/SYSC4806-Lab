package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class AddressBookTest {

    @Test
    void addBuddy() {
        AddressBook addressBook = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("John Doe", "123-456-7890", "459 Dynes Road");
        addressBook.addBuddy(buddy);

        Assertions.assertFalse(addressBook.toString().isEmpty());
    }

    @Test
    public void getBuddies() {
        AddressBook addressBook = new AddressBook();
        BuddyInfo buddy1 = new BuddyInfo("John Doe", "555-1234", "904 Hitchory Cresent");
        BuddyInfo buddy2 = new BuddyInfo("Jane Smith", "555-3456", "704 Dynes Lane");

        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy2);

        ArrayList<BuddyInfo> buddies = (ArrayList<BuddyInfo>) addressBook.getBuddies();

        Assertions.assertEquals(2, buddies.size());
        Assertions.assertTrue(buddies.contains(buddy1));
        Assertions.assertTrue(buddies.contains(buddy2));
    }

}

