package com.ironoc.db.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironoc.db.config.AppConfig;
import com.ironoc.db.dao.PersonDao;
import com.ironoc.db.model.Person;
import com.ironoc.db.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@WebMvcTest(controllers = PersonController.class)
@TestPropertySource(properties = {"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration"})

@ContextConfiguration(classes = AppConfig.class)
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = {PersonController.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
public class PersonControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webAppContext;

//    @Autowired
//    private ApplicationContext context;

    private MockMvc mockMvc;

//    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Mock
    private PersonService personServiceMock;

    @MockBean
    private PersonDao personDaoMock;

    @InjectMocks
    private PersonController personController;// controller under test

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void test_addPerson_success() throws Exception {
        // given
        Person person = new Person.PersonBuilder()
                .withFirstName("Conor")
                .withSurname("Heffron")
                .withAge(42)
                .withId(123)
                .withTitle("Mr.")
                .build();
        List<Person> persons = Collections.singletonList(person);

        given(personServiceMock.getAllPersons()).willReturn(persons).willReturn(persons);
        given(personServiceMock.addPerson(any(Person.class))).willReturn(true);

        // when
        MockHttpServletResponse response = mockMvc.perform(post("/add")
                        .requestAttr("person", person)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(person))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getForwardedUrl(), is("/templates/personList.jsp"));
//        assertThat(response.getOutputStream(), is(new Gson().toJson(person)));
        assertThat(response.getContentAsString(), is(emptyString()));
    }

    @Test
    public void test_deletePerson_success() throws Exception {
        // when
        MockHttpServletResponse response = mockMvc.perform(get("/delete")
                        .queryParam("surname", "Heffron")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getForwardedUrl(), is("/templates/personList.jsp"));
        assertThat(response.getContentAsString(), is(emptyString()));
    }

    @Test
    public void test_home_success() throws Exception {
        // when
        MockHttpServletResponse response = mockMvc.perform(get("/")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getForwardedUrl(), is("/templates/personList.jsp"));
        assertThat(response.getContentAsString(), is(emptyString()));
    }

    @Test
    public void test_catchAll_success() throws Exception {
        // when
        MockHttpServletResponse response = mockMvc.perform(get("/invalid")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isFound())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus(), is(HttpStatus.FOUND.value()));
        assertThat(response.getRedirectedUrl(), is("/"));
        assertThat(response.getForwardedUrl(), is(nullValue()));
        assertThat(response.getContentAsString(), is(emptyString()));
    }

}