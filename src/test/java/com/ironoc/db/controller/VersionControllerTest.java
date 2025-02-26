package com.ironoc.db.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.info.BuildProperties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VersionControllerTest {

    @InjectMocks
    private VersionController versionController;// controller under test

    @Mock
    private BuildProperties buildPropertiesMock;

    private static final String TEST_VERSION = "2.2-RELEASE";

    @Test
    public void test_getApplicationVersion_success() {
        // given
        when(buildPropertiesMock.getVersion()).thenReturn(TEST_VERSION);

        // when
        String result = versionController.getApplicationVersion();

        // then
        verify(buildPropertiesMock).getVersion();

        assertThat(result, is("Version: " + TEST_VERSION));
    }
}
