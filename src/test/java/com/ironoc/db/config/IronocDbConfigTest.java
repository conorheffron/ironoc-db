package com.ironoc.db.config;

import com.ironoc.db.enums.DataSourceKey;
import com.ironoc.db.service.GoogleCloudClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mockStatic;

@RunWith(MockitoJUnitRunner.class)
public class IronocDbConfigTest {

    @InjectMocks
    private IronocDbConfig ironocDbConfig;

    @Mock
    private Environment environmentMock;

    @Mock
    private DataSourceBuilder dataSourceBuilderMock;

    @Mock
    private DataSource dataSourceMock;

    @Mock
    private GoogleCloudClient googleCloudClientMock;

    // Static mocks
    private MockedStatic<DataSourceBuilder> dataSourceBuilderMockedStatic = mockStatic(DataSourceBuilder.class);

    private static final String TEST_SECRET_KEY = "projects/123/secrets/<TEST_SECRET_NAME>/versions/33";
    private static final String TEST_SECRET_DATA = "TeSt_SeCr3t_V0l";

    @Before
    public void setUp() {
        BDDMockito.given(DataSourceBuilder.create()).willReturn(dataSourceBuilderMock);
    }

    @After
    public void tearDown() {
        dataSourceBuilderMockedStatic.close();
    }

    @Test
    public void test_dataSource_success() {
        // given
        when(environmentMock.getRequiredProperty(anyString())).thenReturn("");
        when(environmentMock.getRequiredProperty(DataSourceKey.GCP_SEC_VER.getKey())).thenReturn(TEST_SECRET_KEY);
        when(dataSourceBuilderMock.build()).thenReturn(dataSourceMock);
        when(googleCloudClientMock.getSecret(TEST_SECRET_KEY)).thenReturn(TEST_SECRET_DATA);

        // when
        DataSource result = ironocDbConfig.dataSource();

        // then
        dataSourceBuilderMockedStatic.verify(DataSourceBuilder::create);
        verify(environmentMock).getRequiredProperty(DataSourceKey.GCP_SEC_VER.getKey());
        verify(environmentMock, times(4)).getRequiredProperty(anyString());
        verify(dataSourceBuilderMock).build();
        verify(googleCloudClientMock).getSecret(TEST_SECRET_KEY);

        assertThat(result, is(dataSourceMock));
    }
}
