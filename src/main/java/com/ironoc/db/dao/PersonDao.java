package com.ironoc.db.dao;

import module java.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ironoc.db.model.Person;

@Repository
public interface PersonDao extends JpaRepository<Person, Long> {
	
	@Transactional
    Long deleteBySurname(String surname);

    @Transactional
    List<Person> findBySurname(String surname);

    @Transactional
    Optional<Person> findById(Long id);
}
