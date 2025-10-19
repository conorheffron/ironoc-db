package com.ironoc.db.service;

import module java.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ironoc.db.dao.PersonDao;
import com.ironoc.db.model.Person;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
    private PersonDao personDao;

	@Override
	public List<Person> getAllPersons() {
		List<Person> persons = (List<Person>) personDao.findAll();
		if (persons.size() > 10) {
			return persons.stream().limit(10).collect(Collectors.toList());
		} else {
			return persons;
		}
	}

	@Override
	public Boolean addPerson(Person person) {
		if (personDao.save(person) != null) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean deletePersonBySurname(String surname) {
		personDao.deleteBySurname(surname);
		return true;
	}

	@Override
	public List<Person> findPersonBySurname(String surname) {
		return (List<Person>) personDao.findBySurname(surname);
	}

	@Override
	public Optional<Person> findPersonById(Long id) {
		return personDao.findById(id);
	}

	@Override
	public void deletePersonById(Long id) {
		personDao.deleteById(id);
	}
}
