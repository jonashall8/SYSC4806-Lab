package org.example;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // one AddressBook can have many BuddyInfos
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)

    @JoinColumn(name = "ADDRESSBOOK_ID")
    private List<BuddyInfo> buddies = new ArrayList<>();

    public AddressBook() {
    }

    public void addBuddy(BuddyInfo buddy) {
        buddy.setAddressBook(this);
        this.buddies.add(buddy);
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AddressBook{id=" + id + ", buddies=" + buddies + "}";
    }

    public static void main(String[] args) {
        AddressBook myAddressBook = new AddressBook();

        BuddyInfo buddy1 = new BuddyInfo("John Doe", "555-1234", "123 Hollywood Bvld");
        BuddyInfo buddy2 = new BuddyInfo("Jane Smith", "555-3456", "459 Mixed Cresent");

        myAddressBook.addBuddy(buddy1);
        myAddressBook.addBuddy(buddy2);

        System.out.println(myAddressBook.buddies);

    }
}

