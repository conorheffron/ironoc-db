package com.ironoc.db.service;

import module java.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.ironoc.db.dao.PersonDao;
import com.ironoc.db.model.Person;

@Service
public class PersonServiceImpl implements PersonService {

    public final PersonDao personDao;

    public PersonServiceImpl(@Autowired PersonDao personDao) {
        this.personDao = personDao;
    }

	@Override
	public List<Person> getAllPersons() {
		return personDao.findAll();
	}

	@Override
	public Page<Person> getPersonsPage(int page, int size) {
		return personDao.findAll(PageRequest.of(page, size));
	}

	@Override
	public Boolean addPerson(Person person) {
        personDao.save(person);
        return true;
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
