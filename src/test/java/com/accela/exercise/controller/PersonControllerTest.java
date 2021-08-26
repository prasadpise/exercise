package com.accela.exercise.controller;

import com.accela.exercise.dao.PersonController;
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
import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PersonControllerTest {

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mvc;

    @MockBean
    private PersonController personController;

    @Test
    public void findAll() throws Exception {
        Person person = new Person();
        person.setPersonId("1");
        person.setFirstName("Test");
        person.setLastName("Name");
        person.setAddressList(null);
        Person person2 = new Person();
        person2.setPersonId("2");
        person2.setFirstName("Test2");
        person2.setLastName("Name2");
        person2.setAddressList(null);
        List<Person> allPersons = new ArrayList<>();
        allPersons.add(person);
        allPersons.add(person2);
        given(personController.findAll()).willReturn(allPersons);
        this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .build();
        MockHttpServletResponse response = this.mvc.perform(get("/api/v1/persons")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        assert(response.getContentAsString().contains("1"));
        assert(response.getContentAsString().contains("2"));
        assert(response.getContentAsString().contains("Test"));
        assert(response.getContentAsString().contains("Name"));
        assert(response.getContentAsString().contains("Test2"));
        assert(response.getContentAsString().contains("Name2"));
    }
}