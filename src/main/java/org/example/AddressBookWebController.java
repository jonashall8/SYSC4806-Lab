package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddressBookWebController {

    private final AddressBookRepository addressBookRepository;
    private final BuddyInfoRepository buddyInfoRepository;

    public AddressBookWebController(AddressBookRepository addressBookRepository,
                                    BuddyInfoRepository buddyInfoRepository) {
        this.addressBookRepository = addressBookRepository;
        this.buddyInfoRepository = buddyInfoRepository;
    }

    // ✅ View buddies for a specific address book
    @GetMapping("/addressBooks/{id}/view")
    public String viewBuddies(@PathVariable("id") Long id, Model model) {
        AddressBook addressBook = addressBookRepository.findById(id).orElse(null);
        if (addressBook != null) {
            model.addAttribute("addressBook", addressBook);
            model.addAttribute("buddies", addressBook.getBuddies());
        }
        return "buddies"; // must exist in templates/
    }

    // ✅ List all AddressBooks
    @GetMapping("/addressBooks/list")
    public String listAddressBooks(Model model) {
        model.addAttribute("addressBooks", addressBookRepository.findAll());
        return "addressBooks";
    }

    // ✅ Show form to create a new address book
    @GetMapping("/addAddressBook")
    public String showAddAddressBookForm(Model model) {

        Iterable<AddressBook> addressBooks = addressBookRepository.findAll();
        model.addAttribute("addressBooks", addressBooks);

        return "addAddressBook" ; // render Thymeleaf template, not redirect
    }

    // ✅ Handle creation of new address book, then redirect to list page
    @PostMapping("/addAddressBook")
    public String addAddressBook(@RequestParam String name) {
        AddressBook ab = new AddressBook(name);
        addressBookRepository.save(ab);
        return "redirect:/addressBooks/list";
    }

    // ✅ Show form to add a buddy to a specific address book
    @GetMapping("/addBuddy/{id}")
    public String showAddBuddyForm(@PathVariable("id") Long addressBookId, Model model) {

        AddressBook addressBook = addressBookRepository.findById(addressBookId)
                .orElseThrow(() -> new RuntimeException("AddressBook not found"));

        model.addAttribute("buddies", addressBook.getBuddies());

        model.addAttribute("addressBookId", addressBookId);
        return "addBuddy"; // render addBuddy.html template
    }

    // ✅ Handle adding a new buddy and redirect back to same address book view
    @PostMapping("/addBuddy/{id}")
    public String addBuddy(@PathVariable("id") Long addressBookId,
                           @RequestParam String name,
                           @RequestParam String phoneNumber,
                           @RequestParam String address) {
        AddressBook ab = addressBookRepository.findById(addressBookId).orElse(null);
        if (ab != null) {
            BuddyInfo buddy = new BuddyInfo(name, phoneNumber, address);
            buddy.setAddressBook(ab);
            ab.addBuddy(buddy);
            buddyInfoRepository.save(buddy);
            addressBookRepository.save(ab);
        }
        return "redirect:/addressBooks/" + addressBookId + "/view";
    }

    @GetMapping("/buddyInfos/list")
    public String listBuddies(Model model) {
        model.addAttribute("buddies", buddyInfoRepository.findAll());
        return "allBuddies";
    }
}

