package com.ironoc.db.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ironoc.db.model.Person;

@Repository
public interface PersonDao extends CrudRepository<Person, Long> {
	
	@Transactional
    Long deleteBySurname(String surname);

    @Transactional
    List<Person> findBySurname(String surname);

}
