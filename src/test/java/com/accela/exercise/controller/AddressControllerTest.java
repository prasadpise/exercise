package com.accela.exercise.controller;

import com.accela.exercise.dao.AddressController;
import com.accela.exercise.entity.Address;
import com.accela.exercise.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.Collections;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AddressControllerTest {

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mvc;

    @MockBean
    private AddressController addressController;

    @Test
    public void findAll() throws Exception {
        Person person = new Person();
        person.setPersonId("1");
        person.setFirstName("Test");
        person.setLastName("Name");
        Address address = new Address();
        address.setAddressId("1");
        address.setStreet("Old Dun Laoghaire Road");
        address.setCity("Dublin");
        address.setState("Dublin");
        address.setPostalCode("A96XH76");
        address.setPerson(person);
        person.setAddressList(Collections.singletonList(address));
        given(addressController.findAll()).willReturn(Collections.singletonList(address));

        this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .build();
        MockHttpServletResponse response = this.mvc.perform(get("/api/v1/addresses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assert(response.getContentAsString().contains("1"));
        assert(response.getContentAsString().contains("Old Dun Laoghaire Road"));
        assert(response.getContentAsString().contains("Dublin"));
        assert(response.getContentAsString().contains("A96XH76"));
    }
}
