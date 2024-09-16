package com.ironoc.db.controller;

import com.ironoc.db.model.Person;
import com.ironoc.db.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

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
    private Model modelMock;

    @Mock
    private Person personMock;

    @Mock
    private BindingResult bindingResultMock;

    // test variables
    private static final Integer TEST_ID = 7;
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

        assertThat(result, is("index"));
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
        verify(personServiceMock, never()).getAllPersons();

        assertThat(result, is("redirect:/index"));
    }

    @Test
    public void test_updatePerson_fail() {
        // given
        when(bindingResultMock.hasErrors()).thenReturn(true);

        // when
        String result = personController.updatePerson(TEST_ID, personMock, bindingResultMock);

        // then
        verify(bindingResultMock).hasErrors();
        verify(personServiceMock, never()).addPerson(personMock);

        assertThat(result, is("index"));
    }

    @Test
    public void test_updatePerson_success() {
        // given
        when(bindingResultMock.hasErrors()).thenReturn(false);

        // when
        String result = personController.updatePerson(TEST_ID, personMock, bindingResultMock);

        // then
        verify(bindingResultMock).hasErrors();
        verify(personServiceMock).addPerson(personMock);

        assertThat(result, is("redirect:/index"));
    }

    @Test
    public void test_showEditView_success() {
        // given
        when(personServiceMock.findPersonById(anyLong())).thenReturn(Optional.of(personMock));

        // when
        String result = personController.showEditView(TEST_ID, modelMock);

        // then
        verify(personServiceMock).findPersonById(anyLong());
        verify(modelMock).addAttribute("person", personMock);

        assertThat(result, is("edit-person"));
    }

    @Test
    public void test_showEditView_fail() {
        // given
        when(personServiceMock.findPersonById(anyLong())).thenReturn(Optional.empty());

        // when
        String result = personController.showEditView(TEST_ID, modelMock);

        // then
        verify(personServiceMock).findPersonById(anyLong());
        verify(modelMock, never()).addAttribute("person", personMock);

        assertThat(result, is("edit-person"));
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

        assertThat(result, is("index"));
    }

    @Test
    public void test_deletePersonById_success() {
        // given
        when(personServiceMock.findPersonById(TEST_ID.longValue())).thenReturn(Optional.ofNullable(persons.getFirst()));

        // when
        String result = personController.deletePersonById(modelMapMock, TEST_ID);

        // then
        verify(personServiceMock).findPersonById(TEST_ID.longValue());
        verify(personServiceMock).deletePersonById(TEST_ID.longValue());
        verify(personServiceMock, never()).getAllPersons();

        assertThat(result, is("redirect:/index"));
    }

    @Test
    public void test_deletePersonById_fail() {
        // given
        when(personServiceMock.findPersonById(TEST_ID.longValue())).thenReturn(Optional.empty());

        // when
        String result = personController.deletePersonById(modelMapMock, TEST_ID);

        // then
        verify(personServiceMock).findPersonById(TEST_ID.longValue());
        verify(personServiceMock, never()).deletePersonById(TEST_ID.longValue());
        verify(modelMapMock).addAttribute("deleteError", "There are no matching entries to delete");
        verify(personServiceMock, never()).getAllPersons();

        assertThat(result, is("redirect:/index"));
    }
}
