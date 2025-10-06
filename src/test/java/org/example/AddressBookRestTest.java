package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressBookRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    void whenSaveAddressBook_thenBuddyShowsUpViaRest() {
        // Arrange
        AddressBook book = new AddressBook();
        book.addBuddy(new BuddyInfo("John Doe", "555-1234", "123 Gray Drive"));
        addressBookRepository.save(book);

        // Act: fetch buddies
        String url = "http://localhost:" + port + "/buddyInfoes";
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("John Doe"));
    }

    @Test
    void whenSaveAddressBook_thenItShowsUpViaRest() {
        // Arrange
        AddressBook book = new AddressBook();
        book.addBuddy(new BuddyInfo("Jane Smith", "555-6789", "459 Dynes Road"));
        addressBookRepository.save(book);

        // Act: fetch address books
        String url = "http://localhost:" + port + "/addressBooks";
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

    }
}
