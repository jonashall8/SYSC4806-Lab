package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccessingDataJpaApplication {

    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataJpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(AddressBookRepository addressBookRepo,
                                  BuddyInfoRepository buddyRepo) {
        return (args) -> {
            // create some buddies
            BuddyInfo buddy1 = new BuddyInfo("Jack", "123-4567");
            BuddyInfo buddy2 = new BuddyInfo("Chloe", "234-5678");
            BuddyInfo buddy3 = new BuddyInfo("Kim", "345-6789");
            BuddyInfo buddy4 = new BuddyInfo("John", "456-7893");
            BuddyInfo buddy5 = new BuddyInfo("Jane", "567-7890");
            BuddyInfo buddy6 = new BuddyInfo("David", "678-9043");

            // create an address book and add buddies
            AddressBook addressBook = new AddressBook();
            addressBook.addBuddy(buddy1);
            addressBook.addBuddy(buddy2);
            addressBook.addBuddy(buddy3);

            AddressBook addressBook1 = new AddressBook();
            addressBook1.addBuddy(buddy4);
            addressBook1.addBuddy(buddy5);
            addressBook1.addBuddy(buddy6);


            // save the address book (buddies are saved automatically via cascade)
            addressBookRepo.save(addressBook);
            addressBookRepo.save(addressBook1);

            log.info("Saved AddressBook with buddies:");
            log.info(addressBook.toString());

            // fetch all address books
            log.info("AddressBooks found with findAll():");
            log.info("-----------------------------------");
            addressBookRepo.findAll().forEach(book -> {
                log.info(book.toString());
            });
            log.info("");

            // fetch buddies directly
            log.info("All buddies in the system:");
            log.info("--------------------------");
            buddyRepo.findAll().forEach(b -> {
                log.info(b.toString());
            });
            log.info("");
        };
    }
}