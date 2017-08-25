package com.ironoc.db.service;

import java.util.List;

import com.ironoc.db.model.Person;

public interface PersonService {

    List<Person> getAllPersons();

	Boolean addPerson(Person person);

	Boolean deletePersonBySurname(String surname);

	List<Person> findPersonBySurname(String surname);

}
