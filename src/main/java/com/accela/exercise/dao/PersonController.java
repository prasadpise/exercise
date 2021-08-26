package com.accela.exercise.dao;

import com.accela.exercise.entity.Person;
import com.accela.exercise.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public Iterable<Person> findAll() {
        return personService.findAll();
    }

    @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
    public ResponseEntity<Person> addPerson(@RequestParam("firstName") String firstName,
                                          @RequestParam("lastName") String lastName){
        if(firstName == null || lastName == null){
            return new ResponseEntity<Person>((Person)null, HttpStatus.BAD_REQUEST);
        }
        Person person = new Person();
        person.setPersonId(UUID.randomUUID().toString());
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return new ResponseEntity<Person>(personService.save(person), HttpStatus.OK);
    }


    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public ResponseEntity<Person> getPersonById(@RequestParam("personId") String personId) {
        if(personId == null || personId.isEmpty()){
            return new ResponseEntity<Person>( (Person)null, HttpStatus.BAD_REQUEST);
        }
        Optional<Person> person = personService.findOne(personId);
        if(person.isPresent())
            return new ResponseEntity<Person>(person.get(), HttpStatus.OK);
        return new ResponseEntity<Person>((Person)null, HttpStatus.OK);
    }

    @RequestMapping(value = "/deletePerson", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deletePersonById(@RequestParam("personId") String personId) {
        if(personId == null || personId.isEmpty()){
            return new ResponseEntity<Person>((Person)null, HttpStatus.BAD_REQUEST);
        }
        Optional<Person> person = personService.findOne(personId);
        if(person.isPresent()) {
            if(!person.get().getAddressList().isEmpty() || null != person.get().getAddressList())
                new Exception("Can not delete person without deleting address");
            personService.delete(person.get());
            return new ResponseEntity<Person>((Person)null, HttpStatus.OK);
        }
        return new ResponseEntity<Person>((Person)null, HttpStatus.OK);
    }

    @RequestMapping(value = "/updatePerson", method = RequestMethod.PUT)
    public ResponseEntity<Person> updatePerson(@RequestParam("personId") String personId,
                                               @RequestParam("firstName") String firstName,
                                            @RequestParam("lastName") String lastName){
        if(personId == null || firstName == null || lastName == null){
            return new ResponseEntity<Person>((Person)null, HttpStatus.BAD_REQUEST);
        }
        Optional<Person> person = personService.findOne(personId);
        if(person.isPresent()){
            person.get().setFirstName(firstName);
            person.get().setLastName(lastName);
            return new ResponseEntity<Person>(personService.save(person.get()), HttpStatus.OK);
        }
        return new ResponseEntity<Person>((Person)null, HttpStatus.OK);
    }
}
