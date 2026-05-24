package com.ironoc.db.service;

import module java.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
	@Cacheable("persons")
	public List<Person> getAllPersons() {
		List<Person> persons = (List<Person>) personDao.findAll();
		if (persons.size() > 10) {
			return persons.stream().limit(10).collect(Collectors.toList());
		} else {
			return persons;
		}
	}

	@Override
	@Caching(evict = {
		@CacheEvict(value = "persons", allEntries = true),
		@CacheEvict(value = "personsBySurname", allEntries = true),
		@CacheEvict(value = "personById", allEntries = true)
	})
	public Boolean addPerson(Person person) {
        personDao.save(person);
        return true;
    }

	@Override
	@Caching(evict = {
		@CacheEvict(value = "persons", allEntries = true),
		@CacheEvict(value = "personsBySurname", allEntries = true),
		@CacheEvict(value = "personById", allEntries = true)
	})
	public Boolean deletePersonBySurname(String surname) {
		personDao.deleteBySurname(surname);
		return true;
	}

	@Override
	@Cacheable(value = "personsBySurname", key = "#surname")
	public List<Person> findPersonBySurname(String surname) {
		return (List<Person>) personDao.findBySurname(surname);
	}

	@Override
	@Cacheable(value = "personById", key = "#id")
	public Optional<Person> findPersonById(Long id) {
		return personDao.findById(id);
	}

	@Override
	@Caching(evict = {
		@CacheEvict(value = "persons", allEntries = true),
		@CacheEvict(value = "personsBySurname", allEntries = true),
		@CacheEvict(value = "personById", key = "#id")
	})
	public void deletePersonById(Long id) {
		personDao.deleteById(id);
	}
}
