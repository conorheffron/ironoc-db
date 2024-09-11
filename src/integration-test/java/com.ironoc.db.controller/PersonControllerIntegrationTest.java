package com.ironoc.db.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironoc.db.config.AppConfig;
import com.ironoc.db.dao.PersonDao;
import com.ironoc.db.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = PersonController.class)
@ContextConfiguration(classes = AppConfig.class)
public class PersonControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PersonDao personDaoMock;

    @InjectMocks
    private PersonController personController;// controller under test

    // Int test variables & constants
    private static final String TEST_SURNAME = "Heffron";
    private Person person;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();

        person = new Person.PersonBuilder()
                .withFirstName("Conor")
                .withSurname(TEST_SURNAME)
                .withAge(42)
                .withId(123)
                .withTitle("Mr.")
                .build();
    }

    @Test
    public void test_addPerson_success() throws Exception {
        // given
        Iterable<Person> persons = Collections.singletonList(person);

        given(personDaoMock.findAll()).willReturn(persons).willReturn(persons);

        // when
        MockHttpServletResponse response = mockMvc.perform(post("/add")
                        .flashAttr("person", person)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        verify(personDaoMock).findAll();
        verify(personDaoMock).save(any(Person.class));

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getForwardedUrl(), is("/templates/personList.jsp"));
        assertThat(response.getContentAsString(), is(emptyString()));
    }

    @Test
    public void test_addPerson_fail() throws Exception {
        // given
        Iterable<Person> persons = Collections.singletonList(person);

        given(personDaoMock.findAll()).willReturn(persons).willReturn(persons);

        // when
        MockHttpServletResponse response = mockMvc.perform(post("/add")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        verify(personDaoMock).findAll();
        verify(personDaoMock, never()).save(any(Person.class));

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getForwardedUrl(), is("/templates/personList.jsp"));
        assertThat(response.getContentAsString(), is(emptyString()));
    }

    @Test
    public void test_deletePerson_success() throws Exception {
        // given
        List<Person> persons = Collections.singletonList(person);

        given(personDaoMock.findBySurname(TEST_SURNAME)).willReturn(persons);
        given(personDaoMock.findAll()).willReturn(persons).willReturn(persons);

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/delete")
                        .queryParam("surname", TEST_SURNAME)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        verify(personDaoMock).findBySurname(TEST_SURNAME);
        verify(personDaoMock).deleteBySurname(TEST_SURNAME);
        verify(personDaoMock).findAll();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getForwardedUrl(), is("/templates/personList.jsp"));
        assertThat(response.getContentAsString(), is(emptyString()));
    }

    @Test
    public void test_deletePerson_fail() throws Exception {
        // given
        List<Person> persons = Collections.singletonList(person);

        given(personDaoMock.findBySurname(TEST_SURNAME)).willReturn(Collections.emptyList());
        given(personDaoMock.findAll()).willReturn(persons).willReturn(persons);

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/delete")
                        .queryParam("surname", TEST_SURNAME)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        verify(personDaoMock).findBySurname(TEST_SURNAME);
        verify(personDaoMock, never()).deleteBySurname(TEST_SURNAME);
        verify(personDaoMock).findAll();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getForwardedUrl(), is("/templates/personList.jsp"));
        assertThat(response.getContentAsString(), is(emptyString()));
    }

    @Test
    public void test_home_success() throws Exception {
        // given
        List<Person> persons = Collections.singletonList(person);

        given(personDaoMock.findAll()).willReturn(persons);

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        verify(personDaoMock).findAll();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getForwardedUrl(), is("/templates/personList.jsp"));
        assertThat(response.getContentAsString(), is(emptyString()));
    }
}
