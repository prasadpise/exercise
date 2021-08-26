package com.accela.exercise.dao;

import com.accela.exercise.entity.Address;
import com.accela.exercise.entity.Person;
import com.accela.exercise.service.AddressService;
import com.accela.exercise.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/addresses", method = RequestMethod.GET)
    public Iterable<Address> findAll() {
        return addressService.findAll();
    }

    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    public ResponseEntity<Address> addAddress(@RequestParam("street") String street,
                                          @RequestParam("city") String city,
                                          @RequestParam("state") String state,
                                          @RequestParam("postalCode") String postalCode,
                                          @RequestParam("personId") String personId){
        if(street == null || city == null || state == null || postalCode == null){
            return new ResponseEntity<Address>((Address)null, HttpStatus.BAD_REQUEST);
        }

        Optional<Person> person = personService.findOne(personId);
        if(!person.isPresent()){
            return new ResponseEntity<Address>((Address)null, HttpStatus.BAD_REQUEST);
        }
        Address address = new Address();
        address.setAddressId(UUID.randomUUID().toString());
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setPostalCode(postalCode);
        address.setPerson(person.get());
        return new ResponseEntity<Address>(addressService.save(address), HttpStatus.OK);
    }


    @RequestMapping(value = "/address", method = RequestMethod.GET)
    public ResponseEntity<Address> getAddressById(@RequestParam("addressId") String addressId) {
        if(addressId == null || addressId.isEmpty()){
            return new ResponseEntity<Address>( (Address)null, HttpStatus.BAD_REQUEST);
        }
        Optional<Address> address = addressService.findOne(addressId);
        if(address.isPresent())
            return new ResponseEntity<Address>(address.get(), HttpStatus.OK);
        return new ResponseEntity<Address>((Address)null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/deleteAddress", method = RequestMethod.DELETE)
    public ResponseEntity<Address> deleteAddressById(@RequestParam("addressId") String addressId) {
        if(addressId == null || addressId.isEmpty()){
            return new ResponseEntity<Address>((Address)null, HttpStatus.BAD_REQUEST);
        }
        Optional<Address> address = addressService.findOne(addressId);
        if(address.isPresent()) {
            addressService.delete(address.get());
            return new ResponseEntity<Address>((Address)null, HttpStatus.OK);
        }
        return new ResponseEntity<Address>((Address)null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/updateAddress", method = RequestMethod.PUT)
    public ResponseEntity<Address> updatePerson(@RequestParam("addressId") String addressId,
                                               @RequestParam("street") String street,
                                               @RequestParam("city") String city,
                                               @RequestParam("state") String state,
                                               @RequestParam("postalCode") String postalCode){
        if(street == null || city == null || state == null || postalCode == null){
            return new ResponseEntity<Address>((Address)null, HttpStatus.BAD_REQUEST);
        }
        Optional<Address> address = addressService.findOne(addressId);
        if(address.isPresent()){
            address.get().setStreet(street);
            address.get().setCity(city);
            address.get().setState(state);
            address.get().setPostalCode(postalCode);
            return new ResponseEntity<Address>(addressService.save(address.get()), HttpStatus.OK);
        }
        return new ResponseEntity<Address>((Address)null, HttpStatus.BAD_REQUEST);
    }
}
