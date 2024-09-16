package com.ironoc.db.service;

import com.ironoc.db.dao.PersonDao;
import com.ironoc.db.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonServiceImpl personService;// service under test

    @Mock
    private PersonDao personDaoMock;

    @Mock
    private Person personMock;

    // test suite constants & variables
    private static final Integer TEST_ID = 123;
    private static final String TEST_TITLE = "Mr.";
    private static final Integer TEST_AGE = Integer.valueOf(27);
    private static final String TEST_FIRST_NAME = "Conor";
    private static final String TEST_SURNAME = "Heffron";
    private Person person;
    private List<Person> persons;
    private List<Person> personsFullRs;

    @Before
    public void setUp() {
        // initialize test variables
        persons = new ArrayList<>();
        person = Person.builder()
                .id(TEST_ID.longValue())
                .title(TEST_TITLE)
                .firstName(TEST_FIRST_NAME)
                .surname(TEST_SURNAME)
                .age(TEST_AGE)
                .build();
        persons.addAll(Arrays.asList(personMock, person, personMock));

        personsFullRs = new ArrayList<>();
        personsFullRs.addAll(Arrays.asList(personMock, personMock, personMock, personMock, personMock, personMock,
                personMock, personMock, personMock, personMock, personMock, personMock, personMock, personMock));
    }

    @Test
    public void test_getAllPersons_success() {
        // given
        when(personDaoMock.findAll()).thenReturn(persons);

        // when
        List<Person> result = personService.getAllPersons();

        // then
        verify(personDaoMock).findAll();

        assertThat(result, is(persons));
        assertThat(result.size(), is(persons.size()));
        assertThat(result.get(0), is(personMock));
        assertThat(result.get(1), is(person));
        assertThat(result.get(2), is(personMock));
    }

    @Test
    public void test_getAllPersons_moreThanTen_success() {
        // given
        when(personDaoMock.findAll()).thenReturn(personsFullRs);

        // when
        List<Person> result = personService.getAllPersons();

        // then
        verify(personDaoMock).findAll();

        assertThat(result, is(personsFullRs.stream().limit(10).collect(Collectors.toList())));
        assertThat(result.size(), is(10));
    }

    @Test
    public void test_addPerson_false() {
        // when
        boolean result = personService.addPerson(personMock);

        // then
        verify(personDaoMock).save(personMock);

        assertThat(result, is(false));
    }

    @Test
    public void test_addPerson_true() {
        // given
        when(personDaoMock.save(personMock)).thenReturn(personMock);

        // when
        boolean result = personService.addPerson(personMock);

        // then
        verify(personDaoMock).save(personMock);

        assertThat(result, is(true));
    }

    @Test
    public void test_deletePersonBySurname() {
        // when
        boolean result = personService.deletePersonBySurname(TEST_SURNAME);

        // then
        verify(personDaoMock).deleteBySurname(TEST_SURNAME);

        assertThat(result, is(true));
    }

    @Test
    public void test_deletePersonById() {
        // when
        personService.deletePersonById(TEST_ID.longValue());

        // then
        verify(personDaoMock).deleteById(TEST_ID.longValue());
    }

    @Test
    public void test_findPersonBySurname_success() {
        // given
        when(personDaoMock.findBySurname(TEST_SURNAME)).thenReturn(persons);

        // when
        List<Person> result = personService.findPersonBySurname(TEST_SURNAME);

        // then
        verify(personDaoMock).findBySurname(TEST_SURNAME);

        assertThat(result, is(persons));
        assertThat(result.size(), is(persons.size()));
        assertThat(result.get(0), is(personMock));
        Person personInstance = result.get(1);
        assertThat(personInstance, is(person));
        assertThat(personInstance.getId(), is(TEST_ID.longValue()));
        assertThat(personInstance.getTitle(), is(TEST_TITLE));
        assertThat(personInstance.getFirstName(), is(TEST_FIRST_NAME));
        assertThat(personInstance.getSurname(), is(TEST_SURNAME));
        assertThat(personInstance.getAge(), is(TEST_AGE));
        assertThat(result.get(2), is(personMock));
    }

    @Test
    public void test_findPersonById_success() {
        // given
        when(personDaoMock.findById(TEST_ID.longValue())).thenReturn(Optional.of(personMock));

        // when
        Optional<Person> optionalPerson = personService.findPersonById(TEST_ID.longValue());

        // then
        verify(personDaoMock).findById(TEST_ID.longValue());

        Person result = optionalPerson.get();
        assertThat(result, is(personMock));
    }
}
