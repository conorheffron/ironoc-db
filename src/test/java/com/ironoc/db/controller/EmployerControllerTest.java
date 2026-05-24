package com.ironoc.db.controller;

import com.ironoc.db.dto.EmployerDto;
import com.ironoc.db.model.Employer;
import com.ironoc.db.model.Person;
import com.ironoc.db.service.EmployerService;
import com.ironoc.db.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployerControllerTest {

    @InjectMocks
    private EmployerController employerController;

    @Mock
    private EmployerService employerServiceMock;

    @Mock
    private PersonService personServiceMock;

    @Mock
    private Model modelMock;

    @Mock
    private Person personMock;

    @Mock
    private Employer employerMock;

    @Mock
    private EmployerDto employerDtoMock;

    @Mock
    private BindingResult bindingResultMock;

    private static final Long TEST_PERSON_ID = 7L;
    private static final Long TEST_EMPLOYER_ID = 22L;
    private List<Employer> employers;

    @Before
    public void setUp() {
        employers = new ArrayList<>();
        employers.add(employerMock);
    }

    @Test
    public void test_showJobHistory_success() {
        when(personServiceMock.findPersonById(TEST_PERSON_ID)).thenReturn(Optional.of(personMock));
        when(employerServiceMock.getEmployersByPersonId(TEST_PERSON_ID)).thenReturn(employers);

        String result = employerController.showJobHistory(TEST_PERSON_ID, modelMock);

        verify(personServiceMock).findPersonById(TEST_PERSON_ID);
        verify(employerServiceMock).getEmployersByPersonId(TEST_PERSON_ID);
        verify(modelMock).addAttribute("person", personMock);
        verify(modelMock).addAttribute("employers", employers);

        assertThat(result, is("job-history"));
    }

    @Test
    public void test_showJobHistory_personNotFound() {
        when(personServiceMock.findPersonById(TEST_PERSON_ID)).thenReturn(Optional.empty());

        String result = employerController.showJobHistory(TEST_PERSON_ID, modelMock);

        verify(personServiceMock).findPersonById(TEST_PERSON_ID);
        verify(employerServiceMock, never()).getEmployersByPersonId(anyLong());

        assertThat(result, is("redirect:/"));
    }

    @Test
    public void test_addJobHistory_success() {
        when(bindingResultMock.hasErrors()).thenReturn(false);
        when(personServiceMock.findPersonById(TEST_PERSON_ID)).thenReturn(Optional.of(personMock));

        String result = employerController.addJobHistory(TEST_PERSON_ID, employerDtoMock, bindingResultMock, modelMock);

        verify(bindingResultMock).hasErrors();
        verify(personServiceMock).findPersonById(TEST_PERSON_ID);
        verify(employerServiceMock).addEmployer(any(Employer.class));

        assertThat(result, is("redirect:/job-history/" + TEST_PERSON_ID));
    }

    @Test
    public void test_addJobHistory_validationFail() {
        when(bindingResultMock.hasErrors()).thenReturn(true);
        when(personServiceMock.findPersonById(TEST_PERSON_ID)).thenReturn(Optional.of(personMock));
        when(employerServiceMock.getEmployersByPersonId(TEST_PERSON_ID)).thenReturn(employers);

        String result = employerController.addJobHistory(TEST_PERSON_ID, employerDtoMock, bindingResultMock, modelMock);

        verify(bindingResultMock).hasErrors();
        verify(employerServiceMock, never()).addEmployer(any(Employer.class));
        verify(modelMock).addAttribute("employer", employerDtoMock);

        assertThat(result, is("job-history"));
    }

    @Test
    public void test_showEditJobHistory_success() {
        when(employerServiceMock.findEmployerById(TEST_EMPLOYER_ID)).thenReturn(Optional.of(employerMock));
        when(employerMock.getEmployerId()).thenReturn(TEST_EMPLOYER_ID);
        when(employerMock.getPerson()).thenReturn(personMock);
        when(personMock.getId()).thenReturn(TEST_PERSON_ID);

        String result = employerController.showEditJobHistory(TEST_EMPLOYER_ID, modelMock);

        verify(employerServiceMock).findEmployerById(TEST_EMPLOYER_ID);
        verify(modelMock).addAttribute(eq("employer"), any(EmployerDto.class));

        assertThat(result, is("edit-job-history"));
    }

    @Test
    public void test_showEditJobHistory_notFound() {
        when(employerServiceMock.findEmployerById(TEST_EMPLOYER_ID)).thenReturn(Optional.empty());

        String result = employerController.showEditJobHistory(TEST_EMPLOYER_ID, modelMock);

        verify(employerServiceMock).findEmployerById(TEST_EMPLOYER_ID);
        verify(modelMock, never()).addAttribute(eq("employer"), any());

        assertThat(result, is("redirect:/"));
    }

    @Test
    public void test_updateJobHistory_success() {
        when(bindingResultMock.hasErrors()).thenReturn(false);
        when(employerServiceMock.findEmployerById(TEST_EMPLOYER_ID)).thenReturn(Optional.of(employerMock));
        when(employerMock.getPerson()).thenReturn(personMock);
        when(personMock.getId()).thenReturn(TEST_PERSON_ID);

        String result = employerController.updateJobHistory(TEST_EMPLOYER_ID, employerDtoMock, bindingResultMock, modelMock);

        verify(bindingResultMock).hasErrors();
        verify(employerServiceMock).findEmployerById(TEST_EMPLOYER_ID);
        verify(employerServiceMock).addEmployer(employerMock);

        assertThat(result, is("redirect:/job-history/" + TEST_PERSON_ID));
    }

    @Test
    public void test_updateJobHistory_validationFail() {
        when(bindingResultMock.hasErrors()).thenReturn(true);

        String result = employerController.updateJobHistory(TEST_EMPLOYER_ID, employerDtoMock, bindingResultMock, modelMock);

        verify(bindingResultMock).hasErrors();
        verify(employerDtoMock).setEmployerId(TEST_EMPLOYER_ID);
        verify(modelMock).addAttribute("employer", employerDtoMock);
        verify(employerServiceMock, never()).addEmployer(any(Employer.class));

        assertThat(result, is("edit-job-history"));
    }

    @Test
    public void test_deleteJobHistory_success() {
        when(employerServiceMock.findEmployerById(TEST_EMPLOYER_ID)).thenReturn(Optional.of(employerMock));

        String result = employerController.deleteJobHistory(TEST_EMPLOYER_ID, TEST_PERSON_ID);

        verify(employerServiceMock).findEmployerById(TEST_EMPLOYER_ID);
        verify(employerServiceMock).deleteEmployerById(TEST_EMPLOYER_ID);

        assertThat(result, is("redirect:/job-history/" + TEST_PERSON_ID));
    }

    @Test
    public void test_deleteJobHistory_notFound() {
        when(employerServiceMock.findEmployerById(TEST_EMPLOYER_ID)).thenReturn(Optional.empty());

        String result = employerController.deleteJobHistory(TEST_EMPLOYER_ID, TEST_PERSON_ID);

        verify(employerServiceMock).findEmployerById(TEST_EMPLOYER_ID);
        verify(employerServiceMock, never()).deleteEmployerById(anyLong());

        assertThat(result, is("redirect:/job-history/" + TEST_PERSON_ID));
    }
}
