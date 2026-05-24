package com.ironoc.db.dao;

import com.ironoc.db.model.Employer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployerDao extends CrudRepository<Employer, Long> {

    @Transactional
    List<Employer> findByPersonId(Long personId);

    @Transactional
    Optional<Employer> findById(Long id);
}
