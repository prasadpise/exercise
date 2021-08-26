package com.accela.exercise.service;

import com.accela.exercise.entity.Person;
import java.util.List;
import java.util.Optional;

public interface PersonService {
    Person save(Person person);
    void delete(Person person);
    List<Person> findAll();
    Optional<Person> findOne(String id);
}
