package com.hibernate.test.dao;

import java.util.List;

import com.hibernate.test.model.Person;

public interface PersonDao {

    List<Person> getAllPersons();
 
}
