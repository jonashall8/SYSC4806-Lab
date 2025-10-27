package org.example;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/addressBooks")
public class AddressBookRestController {

    private final AddressBookRepository addressBookRepository;
    private final BuddyInfoRepository buddyInfoRepository;

    public AddressBookRestController(AddressBookRepository addressBookRepository,
                                     BuddyInfoRepository buddyInfoRepository) {
        this.addressBookRepository = addressBookRepository;
        this.buddyInfoRepository = buddyInfoRepository;
    }

    // Get all address books
    @GetMapping
    public Iterable<AddressBook> getAllAddressBooks() {
        return addressBookRepository.findAll();
    }

    // Get all buddies for a specific address book
    @GetMapping("/{id}/buddies")
    public List<BuddyInfo> getBuddies(@PathVariable Long id) {
        AddressBook ab = addressBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AddressBook not found"));
        return ab.getBuddies();
    }

    // Create a new address book
    @PostMapping
    public AddressBook createAddressBook(@RequestBody AddressBook addressBook) {
        return addressBookRepository.save(addressBook);
    }

    // Add a buddy to a specific address book
    // In AddressBookRestController.java

    @PostMapping("/{id}/buddies")
    public BuddyInfo addBuddy(@PathVariable Long id, @RequestBody BuddyInfoDTO buddyDTO) { // <-- Use the DTO

        // 1. Find the parent AddressBook
        AddressBook ab = addressBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AddressBook not found"));

        // 2. Create a new BuddyInfo entity from the DTO data
        BuddyInfo newBuddy = new BuddyInfo();
        newBuddy.setName(buddyDTO.getName());
        newBuddy.setPhoneNumber(buddyDTO.getPhoneNumber());
        newBuddy.setAddress(buddyDTO.getAddress()); // <-- Manually set the relationship

        // 3. Add the buddy to the address book's list
        ab.addBuddy(newBuddy);

        // 4. Save the new buddy (which cascades the save to the address book if configured)
        buddyInfoRepository.save(newBuddy);
        addressBookRepository.save(ab);

        // 5. Return the newly created entity (with its new ID)
        return newBuddy;
    }
}
