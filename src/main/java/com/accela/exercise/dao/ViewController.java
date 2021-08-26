package com.accela.exercise.dao;

import com.accela.exercise.entity.Address;
import com.accela.exercise.entity.Person;
import com.accela.exercise.service.AddressService;
import com.accela.exercise.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ViewController {

    @Autowired
    private PersonService personService;

    @Autowired
    private AddressService addressService;

    @GetMapping("/addPerson")
    public String showAddPersonForm(Person user) {
        return "add-person";
    }

    @RequestMapping("/")
    public String showPersonList(Model model) {
        model.addAttribute("persons", personService.findAll());
        return "index";
    }

    @PostMapping("/addPerson")
    public String addPerson(@Validated Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-person";
        }

        person.setPersonId(UUID.randomUUID().toString());
        personService.save(person);
        return "redirect:/";
    }

    @GetMapping("/updatePerson/{personId}")
    public String showUpdatePersonForm(@PathVariable("personId") String personId, Model model) {
        Person person = personService.findOne(personId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid personId:" + personId));

        model.addAttribute("person", person);
        return "update-person";
    }

    @PostMapping("/updatePerson/{personId}")
    public String updatePerson(@PathVariable("personId") String personId, @Validated Person person,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            person.setPersonId(personId);
            return "update-person";
        }
        Optional<Person> person2 = personService.findOne(personId);
        if(person2.isPresent()) {
            person2.get().setFirstName(person.getFirstName());
            person2.get().setLastName(person.getLastName());
            personService.save(person2.get());
        }
        else
            throw new IllegalArgumentException("Invalid person with person Id:" + personId);
        return "redirect:/";
    }

    @RequestMapping("/deletePerson/{personId}")
    public String deletePerson(@PathVariable("personId") String personId, Model model) {
        Person person = personService.findOne(personId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid personId:" + personId));
        if(!person.getAddressList().isEmpty() || null != person.getAddressList())
            new Exception("Can not delete person without deleting address");
        personService.delete(person);
        return "redirect:/";
    }

    @RequestMapping("/addAddress/{personId}")
    public String showAddAddressForm(@PathVariable("personId") String personId, Model model, Address address) {
        Person person = personService.findOne(personId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid personId:" + personId));

        model.addAttribute("person", person);
        return "add-address";
    }

    @PostMapping("/addAddress/{personId}")
    public String addAddress(@PathVariable("personId") String personId,@Validated Address address, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-address";
        }
        Person person = personService.findOne(personId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid personId:" + personId));
        address.setAddressId(UUID.randomUUID().toString());
        address.setPerson(person);
        addressService.save(address);
        return "redirect:/";
    }

    @GetMapping("/updateAddress/{addressId}")
    public String showUpdateAddressForm(@PathVariable("addressId") String addressId, Model model) {
        Address address = addressService.findOne(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid addressId:" + addressId));

        model.addAttribute("address", address);
        return "update-address";
    }

    @PostMapping("/updateAddress/{addressId}")
    public String updateAddress(@PathVariable("addressId") String addressId, @Validated Address address,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            address.setAddressId(addressId);
            return "update-address";
        }
        Optional<Address> address1 = addressService.findOne(addressId);
        if(address1.isPresent()){
            address1.get().setStreet(address.getStreet());
            address1.get().setCity(address.getCity());
            address1.get().setState(address.getState());
            address1.get().setPostalCode(address.getPostalCode());
            addressService.save(address1.get());
        }
        else
            throw new IllegalArgumentException("Invalid addressId:" + addressId);
        return "redirect:/";
    }

    @RequestMapping("/deleteAddress/{addressId}")
    public String deleteAddress(@PathVariable("addressId") String addressId, Model model) {
        Address address = addressService.findOne(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid addressId:" + addressId));
        addressService.delete(address);
        return "redirect:/";
    }
}
