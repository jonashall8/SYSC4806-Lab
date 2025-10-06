package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class JPATest {

    public static void main(String[] args) {
        // Boot the Spring context from AccessingDataJpaApplication
        ApplicationContext context =
                SpringApplication.run(AccessingDataJpaApplication.class, args);

        // Get repository beans
        BuddyInfoRepository buddyRepo = context.getBean(BuddyInfoRepository.class);
        AddressBookRepository addressBookRepo = context.getBean(AddressBookRepository.class);

        // Persist BuddyInfo entities
        System.out.println("Persisting BuddyInfo objects...");
        BuddyInfo buddy1 = new BuddyInfo("Alice", "123-456-7890", "123 Dynes Road");
        BuddyInfo buddy2 = new BuddyInfo("Bob", "987-654-3210", "459 Dynes Road");
        buddyRepo.save(buddy1);
        buddyRepo.save(buddy2);

        // Query all BuddyInfo
        System.out.println("List of individual BuddyInfo objects\n----------------------------------");
        Iterable<BuddyInfo> buddies = buddyRepo.findAll();
        for (BuddyInfo b : buddies) {
            System.out.println(b);
        }

        // Persist AddressBook with buddies
        System.out.println("\nPersisting AddressBook with buddies...");
        BuddyInfo buddy3 = new BuddyInfo("John Doe", "555-1234", "123 Washignton Square");
        BuddyInfo buddy4 = new BuddyInfo("Jane Smith", "555-3456", "568 Huntington Beach");
        AddressBook addressBook = new AddressBook();
        addressBook.addBuddy(buddy3);
        addressBook.addBuddy(buddy4);
        addressBookRepo.save(addressBook); // cascades to persist buddy3 and buddy4

        // Query all AddressBooks
        System.out.println("\nList of AddressBooks with their buddies\n--------------------------------------");
        Iterable<AddressBook> addressBooks = addressBookRepo.findAll();
        for (AddressBook ab : addressBooks) {
            System.out.println(ab);
        }
    }
}