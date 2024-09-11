package com.ironoc.db.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import com.ironoc.db.model.Person;
import com.ironoc.db.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {

    @InjectMocks
    private PersonController personController;// controller under test

    @Mock
    private HttpServletRequest httpServletRequestMock;

    @Mock
    private PersonService personServiceMock;

    @Mock
    private ModelMap modelMapMock;

    @Mock
    private Person personMock;

    @Mock
    private BindingResult bindingResultMock;

    // test variables
    private static final String TEST_URI = "test/uri";
    private static final String TEST_SURNAME = "Heffron";
    private List<Person> persons;

    @Before
    public void setUp() {
        // initialise test variables
        persons = new ArrayList<>();
        persons.add(personMock);
    }

    @Test
    public void test_Home_success() {
        // given
        when(personServiceMock.getAllPersons()).thenReturn(persons);

        // when
        String result = personController.home(modelMapMock);

        // then
        verify(personServiceMock).getAllPersons();

        assertThat(result, is("personList"));
    }

    @Test
    public void test_AddPerson_success() {
        // given
        when(bindingResultMock.hasErrors()).thenReturn(false);
        when(personServiceMock.addPerson(ArgumentMatchers.any(Person.class))).thenReturn(true);

        // when
        String result = personController.addPerson(modelMapMock, personMock, bindingResultMock);

        // then
        verify(bindingResultMock).hasErrors();
        verify(personServiceMock).addPerson(personMock);
        verify(personServiceMock).getAllPersons();

        assertThat(result, is("personList"));
    }

    @Test
    public void test_AddPerson_fail() {
        // given
        when(bindingResultMock.hasErrors()).thenReturn(true);

        // when
        String result = personController.addPerson(modelMapMock, personMock, bindingResultMock);

        // then
        verify(bindingResultMock).hasErrors();
        verify(personServiceMock, never()).addPerson(personMock);
        verify(personServiceMock).getAllPersons();

        assertThat(result, is("personList"));
    }

    @Test
    public void test_DeletePersonBySurname_success() {
        // given
        when(personServiceMock.findPersonBySurname(TEST_SURNAME)).thenReturn(persons);
        when(personServiceMock.deletePersonBySurname(anyString())).thenReturn(true);

        // when
        String result = personController.deletePersonBySurname(modelMapMock, TEST_SURNAME);

        // then
        verify(personServiceMock).findPersonBySurname(TEST_SURNAME);
        verify(personServiceMock).deletePersonBySurname(TEST_SURNAME);
        verify(personServiceMock).getAllPersons();

        assertThat(result, is("personList"));
    }

    @Test
    public void test_DeletePersonBySurname_fail() {
        // given
        when(personServiceMock.findPersonBySurname(TEST_SURNAME)).thenReturn(Collections.emptyList());

        // when
        String result = personController.deletePersonBySurname(modelMapMock, TEST_SURNAME);

        // then
        verify(personServiceMock).findPersonBySurname(TEST_SURNAME);
        verify(personServiceMock, never()).deletePersonBySurname(TEST_SURNAME);
        verify(modelMapMock).addAttribute("deleteError", "There are no matching entries to delete");
        verify(personServiceMock).getAllPersons();

        assertThat(result, is("personList"));
    }
}
