package com.ironoc.db.service;

import com.ironoc.db.model.Employer;

import java.util.List;
import java.util.Optional;

public interface EmployerService {

    List<Employer> getEmployersByPersonId(Long personId);

    Boolean addEmployer(Employer employer);

    Optional<Employer> findEmployerById(Long id);

    void deleteEmployerById(Long id);
}
