package com.accela.exercise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Address {

    @Id
    private String addressId;

    private String street;

    private String city;

    private String state;

    private String postalCode;

    @ManyToOne
    @JoinColumn(name="personId", nullable=false)
    @JsonIgnore
    private Person person;
}
