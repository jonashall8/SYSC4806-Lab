package org.example;

// No @Entity or @Id
// This is just a plain Java object (POJO)
// to carry data from the client
public class BuddyInfoDTO {
    private String name;
    private String phoneNumber;
    private String address;

    // --- IMPORTANT ---
    // Add a no-argument constructor
    public BuddyInfoDTO() {
    }

    // Add getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }
}