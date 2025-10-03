package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AddressBookWebController {

    private final AddressBookRepository addressBookRepository;
    private final BuddyInfoRepository buddyInfoRepository;

    public AddressBookWebController(AddressBookRepository addressBookRepository,
                                    BuddyInfoRepository buddyInfoRepository) {
        this.addressBookRepository = addressBookRepository;
        this.buddyInfoRepository = buddyInfoRepository;
    }

    // View buddies for a specific address book
    @GetMapping("/addressBooks/{id}/view")
    public String viewBuddies(@PathVariable("id") Long id, Model model) {
        AddressBook addressBook = addressBookRepository.findById(id).orElse(null);
        if (addressBook != null) {
            model.addAttribute("buddies", addressBook.getBuddies());
        }
        return "buddies";
    }

    // List all AddressBooks with links
    @GetMapping("/addressBooks/list")
    public String listAddressBooks(Model model) {
        model.addAttribute("addressBooks", addressBookRepository.findAll());
        return "addressBooks";
    }

    // List all BuddyInfos
    @GetMapping("/buddyInfos/list")
    public String listBuddies(Model model) {
        model.addAttribute("buddies", buddyInfoRepository.findAll());
        return "allBuddies";
    }
}