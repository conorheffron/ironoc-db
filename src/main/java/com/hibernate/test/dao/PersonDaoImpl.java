package com.hibernate.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hibernate.test.model.Person;

@Repository
public class PersonDaoImpl implements PersonDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonDaoImpl.class);
	
    @Autowired
    private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getAllPersons() {
		
		Session session = sessionFactory.openSession();
		List<Person> personsList = null;
		
		try {
			Query query = session.createQuery("from Person");
			personsList = query.list();
		} catch (HibernateException e) {
			LOGGER.error("Exception occured in DAO layer: {}", e);
		}
		
		session.close();
		return personsList;
	}

	@Override
	public Boolean addPerson(Person person) {
		Session session = sessionFactory.openSession();
		Boolean isAdded = false;
		
		try {
			session.save(person);
			isAdded = true;
		} catch (HibernateException e) {
			LOGGER.error("Exception occured in DAO layer: {}", e);
			return isAdded;
		}
		
		session.close();
		return isAdded;
	}

	@Override
	public Boolean deleteBySurname(String surname) {
		
		Session session = sessionFactory.openSession();
		Integer noOfRecsDeleted = 0;
		
		try {
			Query query = session.createQuery("delete from Person where surname= :surname");
			query.setString("surname", surname);
			noOfRecsDeleted = query.executeUpdate();
		} catch (HibernateException e) {
			LOGGER.error("Exception occured in DAO layer: {}", e);
		}
		
		session.close();
		return noOfRecsDeleted > 0 ? true : false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> findPersonBySurname(String surname) {
		Session session = sessionFactory.openSession();
		List<Person> personList = new ArrayList<Person>();
		
		try {
			Query query = session.createQuery("from Person where surname= :surname");
			query.setString("surname", surname);
			personList = query.list();
		} catch (HibernateException e) {
			LOGGER.error("Exception occured in DAO layer: {}", e);
		}
		
		session.close();
		return personList;
	}

}
