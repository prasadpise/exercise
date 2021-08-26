package com.accela.exercise.service.dao;

import com.accela.exercise.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonDao extends JpaRepository<Person, String> {
}
