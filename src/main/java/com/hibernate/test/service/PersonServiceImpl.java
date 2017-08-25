package com.hibernate.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hibernate.test.dao.PersonDao;
import com.hibernate.test.model.Person;

@Service
public class PersonServiceImpl implements
		PersonService {

	@Autowired
    private PersonDao personDao;

	@Override
	@Transactional
	public List<Person> getAllPersons() {
		return personDao.getAllPersons();
	}

	@Override
	@Transactional
	public Boolean addPerson(Person person) {
		return personDao.addPerson(person);
	}

	@Override
	@Transactional
	public Boolean deletePersonBySurname(String surname) {
		return personDao.deleteBySurname(surname);
	}

	@Override
	@Transactional
	public List<Person> findPersonBySurname(String surname) {
		return personDao.findPersonBySurname(surname);
	}

}
