package org.example;

import jakarta.persistence.*;

@Entity
public class BuddyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  // Primary key, auto-generated

    private String name;
    private String phoneNumber;

    // No arg constructor required by JPA
    public BuddyInfo() {
    }

    public BuddyInfo(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @ManyToOne
    @JoinColumn(name = "ADDRESSBOOK_ID")
    private AddressBook addressBook;

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "BuddyInfo{id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public static void main(String[] args) {

        BuddyInfo buddy1 = new BuddyInfo("Alice", "123-456-7890");

        System.out.println(buddy1);

        System.out.println("Buddy1's name: " + buddy1.getName());
        System.out.println("Buddy1's phone number: " + buddy1.getPhoneNumber());
    }
}