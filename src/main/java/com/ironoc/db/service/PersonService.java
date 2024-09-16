package com.ironoc.db.service;

import java.util.List;
import java.util.Optional;

import com.ironoc.db.model.Person;

public interface PersonService {

    List<Person> getAllPersons();

	Boolean addPerson(Person person);

	Boolean deletePersonBySurname(String surname);

	List<Person> findPersonBySurname(String surname);

	Optional<Person> findPersonById(Long id);

	void deletePersonById(Long id);
}
