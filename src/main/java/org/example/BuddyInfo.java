package org.example;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class BuddyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  // Primary key, auto-generated

    private String name;
    private String phoneNumber;
    private String address;

    // No arg constructor required by JPA
    public BuddyInfo() {
    }

    public BuddyInfo(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @ManyToOne
    @JoinColumn(name = "ADDRESSBOOK_ID")
    @JsonBackReference // <-- ADD THIS ANNOTATION
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

    public String getAddress () {
        return address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    @Override
    public String toString() {
        return "BuddyInfo{id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static void main(String[] args) {

        BuddyInfo buddy1 = new BuddyInfo("Alice", "123-456-7890", "123 Dynes place");

        System.out.println(buddy1);

        System.out.println("Buddy1's name: " + buddy1.getName());
        System.out.println("Buddy1's phone number: " + buddy1.getPhoneNumber());
    }
}