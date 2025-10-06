package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

@WebMvcTest(AddressBookWebController.class)
class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AddressBookRepository addressBookRepository;

    @MockitoBean
    private BuddyInfoRepository buddyInfoRepository;

    @Test
    void listAddressBooksShouldReturnView() throws Exception {
        // mock repo so it returns an empty list
        when(addressBookRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/addressBooks/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("addressBooks"))
                .andExpect(model().attributeExists("addressBooks"));
    }
}
