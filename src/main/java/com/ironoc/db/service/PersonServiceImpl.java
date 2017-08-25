package com.ironoc.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ironoc.db.dao.PersonDao;
import com.ironoc.db.model.Person;

@Service
public class PersonServiceImpl implements
		PersonService {

	@Autowired
    private PersonDao personDao;

	@Override
	public List<Person> getAllPersons() {
		return (List<Person>) personDao.findAll();
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

}
