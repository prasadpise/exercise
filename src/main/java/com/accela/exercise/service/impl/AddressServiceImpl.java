package com.accela.exercise.service.impl;

import com.accela.exercise.entity.Address;
import com.accela.exercise.service.AddressService;
import com.accela.exercise.service.dao.AddressDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public Address save(Address address) {
        return addressDao.save(address);
    }

    @Override
    public void delete(Address address) {
        addressDao.delete(address);
    }

    @Override
    public List<Address> findAll() {
        return addressDao.findAll();
    }

    @Override
    public Optional<Address> findOne(String id) {
        return addressDao.findById(id);
    }
}
