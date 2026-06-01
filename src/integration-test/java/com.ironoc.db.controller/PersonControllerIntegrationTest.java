package com.ironoc.db.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironoc.db.config.IronocDbConfig;
import com.ironoc.db.dao.EmployerDao;
import com.ironoc.db.dao.PersonDao;
import com.ironoc.db.dto.PersonDto;
import com.ironoc.db.model.Employer;
import com.ironoc.db.model.Person;
import com.ironoc.db.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import module java.base;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = PersonController.class)
@ContextConfiguration(classes = IronocDbConfig.class)
public class PersonControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private PersonService personServiceMock;

    @MockitoBean
    private EmployerDao employerDaoMock;

    @MockitoBean
    private VersionController versionControllerMock;

    @InjectMocks
    private PersonController personController;// controller under test

    // Int test variables & constants
    private static final Long TEST_ID = 7L;
    private static final String TEST_SURNAME = "Heffron";
    private Person person;
    private static final String ADD_PERSON_TABLE_HTML = """
            <table class="table table-hover table-bordered w-100">
                                <thead class="thead-light">
                                <tr>
                                    <th>#</th>
                                    <th>Title</th>
                                    <th>First Name</th>
                                    <th>Surname</th>
                                    <th>Age</th>
                                    <th>Edit</th>
                                    <th>Delete</th>
                                    <th>Update Job History</th>
                                    <th>Job History</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>7</td>
                                    <td>Mr.</td>
                                    <td>Conor</td>
                                    <td>Heffron</td>
                                    <td>42</td>
                                    <td>
                                        <a href="/edit/7" class="btn btn-primary btn-sm rounded-0" title="Edit">
                                            <i class="fa fa-edit"></i>
                                        </a>
                                    </td>
                                    <td>
                                        <form action="/delete/7" method="post" class="d-inline"><input type="hidden" name="_method" value="delete"/>
                                            <input type="hidden" name="_method" value="delete" />
                                            <button type="submit" class="btn btn-danger btn-sm rounded-0" title="Delete">
                                                <i class="fa fa-trash"></i>
                                            </button>
                                        </form>
                                    </td>
                                    <td>
                                        <a href="/job-history/7" class="btn btn-success btn-sm rounded-0" title="Manage Job History">
                                            <i class="fa fa-plus"></i>
                                        </a>
                                    </td>
                                    <td>
                                        <table class="table table-sm mb-0">
                                            <thead>
                                            <tr>
                                                <th>Job Title</th>
                                                <th>Employer Name</th>
                                                <th>Start Year</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td></td>
                                                <td>Morgan Stanley</td>
                                                <td>2017</td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td>BSkyB</td>
                                                <td>2014</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="4"></td>
                                    <td colspan="5">
                                        <b>42</b> is the Average Employee Age
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6"></td>
                                    <td colspan="3">
                                        <b>42</b> is the Sum of Ages
                                    </td>
                                </tr>
                                </tbody>
                            </table>""";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();

        person = Person.builder()
                .firstName("Conor")
                .surname(TEST_SURNAME)
                .age(42)
                .title("Mr.")
                .id(7L)
                .employers(List.of(Employer.builder()
                                .employerName("Morgan Stanley")
                                .startYear(2017)
                                .employerId(22L)
                                .build(),
                        Employer.builder()
                                .employerName("BSkyB")
                                .startYear(2014)
                                .employerId(23L).build()))
                .build();
    }

    @Test
    public void test_addPerson_success() throws Exception {
        // given
        List<Person> persons = Collections.singletonList(person);

        given(personServiceMock.getAllPersons()).willReturn(persons).willReturn(persons);

        PersonDto personDto = PersonDto.builder()
                .firstName(person.getFirstName())
                .surname(person.getSurname())
                .age(person.getAge())
                .title(person.getTitle())
                .id(person.getId())
                .build();

        // when
        MockHttpServletResponse response = mockMvc.perform(post("/add")
                        .flashAttr("person", personDto)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isFound())
                .andReturn().getResponse();

        // then
        verify(personServiceMock, never()).getAllPersons();
        verify(personServiceMock).addPerson(any(Person.class));

        assertThat(response.getStatus(), is(HttpStatus.FOUND.value()));
        assertThat(response.getContentAsString(), emptyString());
    }

    @Test
    public void test_addPerson_fail() throws Exception {
        // given
        List<Person> persons = Collections.singletonList(person);

        given(personServiceMock.getAllPersons()).willReturn(persons).willReturn(persons);

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/add")
                        .flashAttr("person", PersonDto.builder().build())
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isMethodNotAllowed())
                .andReturn().getResponse();

        // then
        verify(personServiceMock, never()).getAllPersons();
        verify(personServiceMock, never()).addPerson(any(Person.class));

        assertThat(response.getStatus(), is(HttpStatus.METHOD_NOT_ALLOWED.value()));
        assertThat(response.getForwardedUrl(), is(nullValue()));
        assertThat(response.getContentAsString(), is(emptyString()));
    }

    @Test
    public void test_deletePerson_success() throws Exception {
        // given
        List<Person> persons = Collections.singletonList(person);

        given(personServiceMock.findPersonById(TEST_ID)).willReturn(Optional.ofNullable(person));
        given(personServiceMock.getAllPersons()).willReturn(persons).willReturn(persons);

        // when
        MockHttpServletResponse response = mockMvc.perform(delete("/delete/" + TEST_ID)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isFound())
                .andReturn().getResponse();

        // then
        verify(personServiceMock).findPersonById(TEST_ID);
        verify(personServiceMock).deletePersonById(TEST_ID);
        verify(personServiceMock, never()).getAllPersons();

        assertThat(response.getStatus(), is(HttpStatus.FOUND.value()));
        assertThat(response.getForwardedUrl(), is(nullValue()));
        assertThat(response.getContentAsString(), is(emptyString()));
    }

    @Test
    public void test_deletePerson_fail() throws Exception {
        // given
        List<Person> persons = Collections.singletonList(person);

        given(personServiceMock.findPersonById(TEST_ID)).willReturn(Optional.empty());
        given(personServiceMock.getAllPersons()).willReturn(persons).willReturn(persons);

        // when
        MockHttpServletResponse response = mockMvc.perform(delete("/delete/" + TEST_ID)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isFound())
                .andReturn().getResponse();

        // then
        verify(personServiceMock).findPersonById(TEST_ID);
        verify(personServiceMock, never()).deletePersonById(TEST_ID);
        verify(personServiceMock, never()).getAllPersons();

        assertThat(response.getStatus(), is(HttpStatus.FOUND.value()));
        assertThat(response.getForwardedUrl(), is(nullValue()));
        assertThat(response.getContentAsString(), is(emptyString()));
    }

    @Test
    public void test_home_success() throws Exception {
        // given
        List<Person> persons = Collections.singletonList(person);
        Page<Person> personsPage = new PageImpl<>(persons);

        given(personServiceMock.getPersonsPage(anyInt(), anyInt())).willReturn(personsPage);

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        verify(personServiceMock).getPersonsPage(anyInt(), anyInt());

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), containsString(ADD_PERSON_TABLE_HTML));
    }

    @Test
<<<<<<< HEAD
    public void test_home_filterBySurname_success() throws Exception {
        // given
        List<Person> persons = Collections.singletonList(person);

        given(personDaoMock.findBySurname(TEST_SURNAME)).willReturn(persons);

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/")
                        .queryParam("surname", TEST_SURNAME)
=======
    public void test_home_success_no_job_history() throws Exception {
        // given
        Person personNoEmployers = Person.builder()
                .firstName("Conor")
                .surname(TEST_SURNAME)
                .age(42)
                .title("Mr.")
                .id(TEST_ID)
                .employers(Collections.emptyList())
                .build();
        List<Person> persons = Collections.singletonList(personNoEmployers);

        given(personServiceMock.getPersonsPage(anyInt(), anyInt())).willReturn(new PageImpl<>(persons));

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/")
>>>>>>> origin/main
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse();

        // then
<<<<<<< HEAD
        verify(personDaoMock).findBySurname(TEST_SURNAME);
        verify(personDaoMock, never()).findAll();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), containsString(ADD_PERSON_TABLE_HTML));
=======
        verify(personServiceMock).getPersonsPage(anyInt(), anyInt());

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), not(containsString("<th>Job Title</th>")));
        assertThat(response.getContentAsString(), not(containsString("<th>Employer Name</th>")));
        assertThat(response.getContentAsString(), not(containsString("<th>Start Year</th>")));
>>>>>>> origin/main
    }
}
