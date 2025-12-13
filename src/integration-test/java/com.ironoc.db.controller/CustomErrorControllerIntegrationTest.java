package com.ironoc.db.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironoc.db.config.IronocDbConfig;
import com.ironoc.db.dao.PersonDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = PersonController.class)
@ContextConfiguration(classes = IronocDbConfig.class)
public class CustomErrorControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private PersonDao personDaoMock;

    @MockitoBean
    private VersionController versionControllerMock;

    @InjectMocks
    private CustomErrorController customErrorController;// controller under test

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void testNotFoundUriSuccessPath() throws Exception {
        // when
        MockHttpServletResponse response = mockMvc.perform(get("/invalid")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
        assertThat(response.getRedirectedUrl(), is(emptyOrNullString()));
        assertThat(response.getForwardedUrl(), is(nullValue()));
        assertThat(response.getContentAsString(), is(emptyString()));
        assertThat(response.getErrorMessage(), is("No static resource invalid."));
        assertThat(response.getIncludedUrl(), is(emptyOrNullString()));
        assertThat(response.getIncludedUrls(), is(empty()));
    }

    @Test
    public void testFaviconNotFoundUriFailPath() throws Exception {
        // when
        MockHttpServletResponse response = mockMvc.perform(get("/static/favicon.ico")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
        assertThat(response.getRedirectedUrl(), is(emptyOrNullString()));
        assertThat(response.getForwardedUrl(), is(emptyOrNullString()));
        assertThat(response.getContentAsString(), is(emptyOrNullString()));
        assertThat(response.getErrorMessage(), is("No static resource static/favicon.ico."));
        assertThat(response.getIncludedUrl(), is(emptyOrNullString()));
        assertThat(response.getIncludedUrls(), is(empty()));
    }

    @Test
    public void testFaviconNotFoundUriSuccessPath() throws Exception {
        // when
        MockHttpServletResponse response = mockMvc.perform(get("/favicon.ico")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getRedirectedUrl(), is(emptyOrNullString()));
        assertThat(response.getForwardedUrl(), is(nullValue()));
        assertThat(response.getContentAsString(), containsString("PNG"));
        assertThat(response.getErrorMessage(), is(emptyOrNullString()));
        assertThat(response.getIncludedUrl(), is(emptyOrNullString()));
        assertThat(response.getIncludedUrls(), is(empty()));
    }
}
