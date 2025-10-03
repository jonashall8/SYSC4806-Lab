package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        AddressBook myAddressBook = new AddressBook();

        BuddyInfo buddy1 = new BuddyInfo("John Doe", "555-1234");
        BuddyInfo buddy2 = new BuddyInfo("Jane Smith", "555-3456");
        BuddyInfo buddy3 = new BuddyInfo("Alice Johnson", "555-6789");

        myAddressBook.addBuddy(buddy1);
        myAddressBook.addBuddy(buddy2);
        myAddressBook.addBuddy(buddy3);

        System.out.println("Address Book:");
        for (BuddyInfo b : myAddressBook.getBuddies()) {
            System.out.println(b);
        }
    }
}