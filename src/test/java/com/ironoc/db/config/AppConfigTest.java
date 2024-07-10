package com.ironoc.db.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AppConfigTest {

    @InjectMocks
    private AppConfig appConfig;// config bean under test

    @Mock
    private ResourceHandlerRegistry resourceHandlerRegistryMock;

    @Mock
    private ResourceHandlerRegistration resourceHandlerRegistrationMock;

    @Mock
    private DefaultServletHandlerConfigurer defaultServletHandlerConfigurerMock;

    @Test
    public void test_getViewResolver_success() {
        // when
        ViewResolver result = appConfig.getViewResolver();

        // then
        assertThat(result, is(notNullValue()));
        assertThat(result.getClass(), is(InternalResourceViewResolver.class));
    }

    @Test
    public void test_addResourceHandlers_success() {
        // given
        when(resourceHandlerRegistryMock.addResourceHandler(ArgumentMatchers.anyString()))
                .thenReturn(resourceHandlerRegistrationMock);

        // when
        appConfig.addResourceHandlers(resourceHandlerRegistryMock);

        // then
        ArgumentCaptor<String> resourceHandlerCaptors = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> resourceLocationCaptors = ArgumentCaptor.forClass(String.class);

        verify(resourceHandlerRegistryMock, times(4)).addResourceHandler(resourceHandlerCaptors.capture());
        verify(resourceHandlerRegistrationMock, times(4)).addResourceLocations(resourceLocationCaptors.capture());

        List<String> capturedResourceHandlers = resourceHandlerCaptors.getAllValues();
        assertThat(AppConfig.RESOURCES_HANDLER, is(capturedResourceHandlers.get(0)));
        assertThat(AppConfig.FAV_ICON, is(capturedResourceHandlers.get(1)));
        assertThat(AppConfig.SITE_MAP, is(capturedResourceHandlers.get(2)));
        assertThat(AppConfig.ROBOTS_TEXT, is(capturedResourceHandlers.get(3)));

        List<String> capturedLocations = resourceLocationCaptors.getAllValues();
        assertThat(AppConfig.STATIC_LOC, is(capturedLocations.get(0)));
        assertThat(AppConfig.IMAGES_LOC, is(capturedLocations.get(1)));
        assertThat(AppConfig.STATIC_CONF_LOC, is(capturedLocations.get(2)));
        assertThat(AppConfig.STATIC_CONF_LOC, is(capturedLocations.get(3)));
    }

    @Test
    public void test_configureDefaultServletHandling_success() {
        // when
        appConfig.configureDefaultServletHandling(defaultServletHandlerConfigurerMock);

        // then
        verify(defaultServletHandlerConfigurerMock).enable();
    }
}
