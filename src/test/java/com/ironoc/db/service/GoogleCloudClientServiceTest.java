package com.ironoc.db.service;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretPayload;
import com.google.protobuf.ByteString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.Silent.class)
public class GoogleCloudClientServiceTest {

    @InjectMocks
    private GoogleCloudClientImpl googleCloudClient;

    @Mock
    private AccessSecretVersionResponse accessSecretVersionResponseMock;

    @Mock
    private SecretPayload secretPayloadMock;

    @Mock
    private SecretManagerServiceClient secretManagerServiceClientMock;

    private final MockedStatic<SecretManagerServiceClient> secretManagerServiceClientMockedStatic =
            mockStatic(SecretManagerServiceClient.class);

    private static final String TEST_SECRET_DATA = "TeSt_SeCr3t_V0l";
    private static final String TEST_SECRET_KEY = "projects/123/secrets/<TEST_SECRET_NAME>/versions/33";

    @Before
    public void setUp() throws IOException {
        BDDMockito.given(SecretManagerServiceClient.create()).willReturn(secretManagerServiceClientMock);
    }

    @After
    public void tearDown() {
        secretManagerServiceClientMockedStatic.close();
    }

    @Test
    public void test_getSecret_success() {
        // given
        when(secretManagerServiceClientMock.accessSecretVersion(anyString()))
                .thenReturn(accessSecretVersionResponseMock);
        when(accessSecretVersionResponseMock.getPayload()).thenReturn(secretPayloadMock);
        when(secretPayloadMock.getData()).thenReturn(ByteString.copyFromUtf8(TEST_SECRET_DATA));

        // when
        String result = googleCloudClient.getSecret(TEST_SECRET_KEY);

        // then
        secretManagerServiceClientMockedStatic.verify(SecretManagerServiceClient::create);
        verify(secretManagerServiceClientMock).accessSecretVersion(anyString());
        verify(accessSecretVersionResponseMock).getPayload();
        verify(secretPayloadMock).getData();

        assertThat(result, is(TEST_SECRET_DATA));
    }

    @Test
    public void test_getSecret_fail() {
        // given
        doThrow(new UnsupportedOperationException("test exception."))
                .when(secretManagerServiceClientMock).accessSecretVersion(TEST_SECRET_KEY);

        // when
        String result = googleCloudClient.getSecret(TEST_SECRET_KEY);

        // then
        secretManagerServiceClientMockedStatic.verify(SecretManagerServiceClient::create);
        verify(secretManagerServiceClientMock).accessSecretVersion(anyString());
        verify(accessSecretVersionResponseMock, never()).getPayload();
        verify(secretPayloadMock, never()).getData();

        assertThat(result, is(emptyString()));
    }
}
