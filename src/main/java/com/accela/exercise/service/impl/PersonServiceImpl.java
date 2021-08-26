package com.accela.exercise.service.impl;

import com.accela.exercise.entity.Person;
import com.accela.exercise.service.PersonService;
import com.accela.exercise.service.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    @Override
    public Person save(Person person) {
        return personDao.save(person);
    }

    @Override
    public void delete(Person person) {
        personDao.delete(person);
    }

    @Override
    public List<Person> findAll() {
        return personDao.findAll();
    }

    @Override
    public Optional<Person> findOne(String id) {
        return personDao.findById(id);
    }
}
