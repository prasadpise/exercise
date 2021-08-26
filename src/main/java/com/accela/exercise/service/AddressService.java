package com.accela.exercise.service;

import com.accela.exercise.entity.Address;
import java.util.List;
import java.util.Optional;

public interface AddressService {
    Address save(Address address);
    void delete(Address address);
    List<Address> findAll();
    Optional<Address> findOne(String id);
}
