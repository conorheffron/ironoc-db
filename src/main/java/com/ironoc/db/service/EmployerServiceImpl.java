package com.ironoc.db.service;

import com.ironoc.db.dao.EmployerDao;
import com.ironoc.db.model.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerServiceImpl implements EmployerService {

    private final EmployerDao employerDao;

    public EmployerServiceImpl(@Autowired EmployerDao employerDao) {
        this.employerDao = employerDao;
    }

    @Override
    public List<Employer> getEmployersByPersonId(Long personId) {
        return employerDao.findByPersonId(personId);
    }

    @Override
    public Boolean addEmployer(Employer employer) {
        employerDao.save(employer);
        return true;
    }

    @Override
    public Optional<Employer> findEmployerById(Long id) {
        return employerDao.findById(id);
    }

    @Override
    public void deleteEmployerById(Long id) {
        employerDao.deleteById(id);
    }
}
