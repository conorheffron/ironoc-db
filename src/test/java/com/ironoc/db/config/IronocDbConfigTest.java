package com.ironoc.db.config;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretPayload;
import com.google.protobuf.ByteString;
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

import java.io.IOException;

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
    private AccessSecretVersionResponse accessSecretVersionResponseMock;

    @Mock
    private SecretManagerServiceClient secretManagerServiceClientMock;

    @Mock
    private SecretPayload secretPayloadMock;

    // Static mocks
    private MockedStatic<DataSourceBuilder> dataSourceBuilderMockedStatic = mockStatic(DataSourceBuilder.class);

    private MockedStatic<SecretManagerServiceClient> secretManagerServiceClientMockedStatic =
            mockStatic(SecretManagerServiceClient.class);

    private static final String TEST_SECRET_DATA = "TeSt_SeCr3t_V0l";

    @Test
    public void test_error_view_success() throws IOException {
        // given
        when(environmentMock.getRequiredProperty(anyString())).thenReturn(new String());
        BDDMockito.given(DataSourceBuilder.create()).willReturn(dataSourceBuilderMock);
        when(dataSourceBuilderMock.build()).thenReturn(dataSourceMock);
        BDDMockito.given(SecretManagerServiceClient.create()).willReturn(secretManagerServiceClientMock);
        when(secretManagerServiceClientMock.accessSecretVersion(anyString())).thenReturn(accessSecretVersionResponseMock);
        when(accessSecretVersionResponseMock.getPayload()).thenReturn(secretPayloadMock);
        when(secretPayloadMock.getData()).thenReturn(ByteString.copyFromUtf8(TEST_SECRET_DATA));

        // when
        DataSource result = ironocDbConfig.dataSource(environmentMock);

        // then
        verify(environmentMock, times(4)).getRequiredProperty(anyString());
        verify(dataSourceBuilderMock).build();
        verify(secretManagerServiceClientMock).accessSecretVersion(anyString());
        verify(accessSecretVersionResponseMock).getPayload();
        verify(secretPayloadMock).getData();

        assertThat(result, is(dataSourceMock));
    }
}
