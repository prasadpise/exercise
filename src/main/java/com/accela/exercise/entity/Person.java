package com.accela.exercise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Person {

    @Id
    private String personId;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "person")
    List<Address> addressList;
}