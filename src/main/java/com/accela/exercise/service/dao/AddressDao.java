package com.accela.exercise.service.dao;

import com.accela.exercise.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDao extends JpaRepository<Address, String> {
}
