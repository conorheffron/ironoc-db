package com.ironoc.db.controller;

import module java.base;

import com.ironoc.db.dto.PersonDto;
import com.ironoc.db.mapper.PersonMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personServiceMock;

    @Mock
    private PersonMapper personMapperMock;

    @Mock
    private ModelMap modelMapMock;

    @Mock
    private Model modelMock;

    @Mock
    private Person personMock;

    @Mock
    private PersonDto personDtoMock;

    @Mock
    private BindingResult bindingResultMock;

    private static final Integer TEST_ID = 7;
    private List<Person> persons;

    @Before
    public void setUp() {
        persons = new ArrayList<>();
        persons.add(personMock);
    }

    @Test
    public void test_Home_success() {
        when(personServiceMock.getAllPersons()).thenReturn(persons);

        String result = personController.home(modelMapMock);

        verify(personServiceMock).getAllPersons();
        assertThat(result, is("index"));
    }

    @Test
    public void test_AddPerson_success() {
        when(bindingResultMock.hasErrors()).thenReturn(false);
        when(personMapperMock.toPerson(personDtoMock)).thenReturn(personMock);

        String result = personController.addPerson(modelMapMock, personDtoMock, bindingResultMock);

        verify(bindingResultMock).hasErrors();
        verify(personMapperMock).toPerson(personDtoMock);
        verify(personServiceMock).addPerson(personMock);
        verify(personServiceMock, never()).getAllPersons();

        assertThat(result, is("redirect:/"));
    }

    @Test
    public void test_updatePerson_fail() {
        when(bindingResultMock.hasErrors()).thenReturn(true);

        String result = personController.updatePerson(modelMapMock, TEST_ID, personDtoMock, bindingResultMock);

        verify(bindingResultMock).hasErrors();
        verify(personDtoMock).setId(TEST_ID.longValue());
        verify(modelMapMock).addAttribute("person", personDtoMock);
        verify(personServiceMock, never()).addPerson(ArgumentMatchers.any(Person.class));

        assertThat(result, is("edit-person"));
    }

    @Test
    public void test_updatePerson_success() {
        when(bindingResultMock.hasErrors()).thenReturn(false);
        when(personMapperMock.toPerson(personDtoMock)).thenReturn(personMock);

        String result = personController.updatePerson(modelMapMock, TEST_ID, personDtoMock, bindingResultMock);

        verify(bindingResultMock).hasErrors();
        verify(personDtoMock).setId(TEST_ID.longValue());
        verify(personMapperMock).toPerson(personDtoMock);
        verify(personServiceMock).addPerson(personMock);

        assertThat(result, is("redirect:/"));
    }

    @Test
    public void test_showEditView_success() {
        when(personServiceMock.findPersonById(anyLong())).thenReturn(Optional.of(personMock));
        when(personMapperMock.toPersonDto(personMock)).thenReturn(personDtoMock);

        String result = personController.showEditView(TEST_ID, modelMock);

        verify(personServiceMock).findPersonById(anyLong());
        verify(personMapperMock).toPersonDto(personMock);
        verify(modelMock).addAttribute("person", personDtoMock);

        assertThat(result, is("edit-person"));
    }

    @Test
    public void test_showEditView_fail() {
        when(personServiceMock.findPersonById(anyLong())).thenReturn(Optional.empty());

        String result = personController.showEditView(TEST_ID, modelMock);

        verify(personServiceMock).findPersonById(anyLong());
        verify(modelMock, never()).addAttribute("person", personDtoMock);

        assertThat(result, is("edit-person"));
    }

    @Test
    public void test_AddPerson_fail() {
        when(bindingResultMock.hasErrors()).thenReturn(true);

        String result = personController.addPerson(modelMapMock, personDtoMock, bindingResultMock);

        verify(bindingResultMock).hasErrors();
        verify(personServiceMock, never()).addPerson(ArgumentMatchers.any(Person.class));
        verify(personServiceMock).getAllPersons();

        assertThat(result, is("index"));
    }

    @Test
    public void test_deletePersonById_success() {
        when(personServiceMock.findPersonById(TEST_ID.longValue())).thenReturn(Optional.ofNullable(persons.getFirst()));

        String result = personController.deletePersonById(modelMapMock, TEST_ID);

        verify(personServiceMock).findPersonById(TEST_ID.longValue());
        verify(personServiceMock).deletePersonById(TEST_ID.longValue());
        verify(personServiceMock, never()).getAllPersons();

        assertThat(result, is("redirect:/"));
    }

    @Test
    public void test_deletePersonById_fail() {
        when(personServiceMock.findPersonById(TEST_ID.longValue())).thenReturn(Optional.empty());

        String result = personController.deletePersonById(modelMapMock, TEST_ID);

        verify(personServiceMock).findPersonById(TEST_ID.longValue());
        verify(personServiceMock, never()).deletePersonById(TEST_ID.longValue());
        verify(modelMapMock).addAttribute("deleteError", "There are no matching entries to delete");
        verify(personServiceMock, never()).getAllPersons();

        assertThat(result, is("redirect:/"));
    }
}
