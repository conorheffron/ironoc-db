package com.ironoc.db.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ironoc.db.model.Person;

@Repository
public interface PersonDao extends CrudRepository<Person, Long> {
	
	@Transactional
    public Long deleteBySurname(String surname);

    @Transactional
    public List<Person> findBySurname(String surname);

    @Transactional
    public Optional<Person> findById(Long id);
}
