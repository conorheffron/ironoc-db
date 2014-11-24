package com.hibernate.test.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hibernate.test.model.Person;

@Repository
public class PersonDaoImpl implements
		PersonDao {
	
    @Autowired
    private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getAllPersons() {
		return this.sessionFactory.getCurrentSession().createQuery("from Person").list();
	}

}
