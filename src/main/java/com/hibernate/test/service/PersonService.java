package com.hibernate.test.service;

import java.util.List;

import com.hibernate.test.model.Person;

public interface PersonService {

    List<Person> getAllPersons();

	Boolean addPerson(Person person);

	Boolean deletePersonBySurname(String surname);

	List<Person> findPersonBySurname(String surname);

}
