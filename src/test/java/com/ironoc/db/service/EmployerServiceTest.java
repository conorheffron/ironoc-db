package com.ironoc.db.service;

import com.ironoc.db.dao.EmployerDao;
import com.ironoc.db.model.Employer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployerServiceTest {

    @InjectMocks
    private EmployerServiceImpl employerService;

    @Mock
    private EmployerDao employerDaoMock;

    @Mock
    private Employer employerMock;

    private static final Long TEST_EMPLOYER_ID = 22L;
    private static final Long TEST_PERSON_ID = 7L;
    private List<Employer> employers;

    @Before
    public void setUp() {
        employers = new ArrayList<>();
        employers.add(employerMock);
    }

    @Test
    public void test_getEmployersByPersonId_success() {
        // given
        when(employerDaoMock.findByPersonId(TEST_PERSON_ID)).thenReturn(employers);

        // when
        List<Employer> result = employerService.getEmployersByPersonId(TEST_PERSON_ID);

        // then
        verify(employerDaoMock).findByPersonId(TEST_PERSON_ID);
        assertThat(result, is(employers));
        assertThat(result.size(), is(1));
    }

    @Test
    public void test_addEmployer_true() {
        // given
        when(employerDaoMock.save(employerMock)).thenReturn(employerMock);

        // when
        boolean result = employerService.addEmployer(employerMock);

        // then
        verify(employerDaoMock).save(employerMock);
        assertThat(result, is(true));
    }

    @Test
    public void test_findEmployerById_success() {
        // given
        when(employerDaoMock.findById(TEST_EMPLOYER_ID)).thenReturn(Optional.of(employerMock));

        // when
        Optional<Employer> result = employerService.findEmployerById(TEST_EMPLOYER_ID);

        // then
        verify(employerDaoMock).findById(TEST_EMPLOYER_ID);
        assertThat(result.orElse(null), is(employerMock));
    }

    @Test
    public void test_deleteEmployerById() {
        // when
        employerService.deleteEmployerById(TEST_EMPLOYER_ID);

        // then
        verify(employerDaoMock).deleteById(TEST_EMPLOYER_ID);
    }
}
