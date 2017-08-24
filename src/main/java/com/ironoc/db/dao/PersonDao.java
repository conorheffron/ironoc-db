package com.ironoc.db.dao;

import java.util.List;

import com.ironoc.db.model.Person;

public interface PersonDao {

    List<Person> getAllPersons();

	Boolean addPerson(Person person);

	Boolean deleteBySurname(String surname);

	List<Person> findPersonBySurname(String surname);
 
}
