package com.techyourchance.mockitofundamentals.example7;

import com.techyourchance.mockitofundamentals.example7.LoginUseCaseSync.UseCaseResult;
import com.techyourchance.mockitofundamentals.example7.authtoken.AuthTokenCache;
import com.techyourchance.mockitofundamentals.example7.eventbus.EventBusPoster;
import com.techyourchance.mockitofundamentals.example7.eventbus.LoggedInEvent;
import com.techyourchance.mockitofundamentals.example7.networking.LoginHttpEndpointSync;
import com.techyourchance.mockitofundamentals.example7.networking.NetworkErrorException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginUseCaseSyncTest {

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String AUTH_TOKEN = "authToken";

    private LoginUseCaseSync SUT;

    @Mock
    private LoginHttpEndpointSync mLoginHttpEndpointSyncMock;

    @Mock
    private AuthTokenCache mAuthTokenCacheMock;

    @Mock
    private EventBusPoster mEventBusPosterMock;

    @Before
    public void setup() throws NetworkErrorException {
        this.SUT = new LoginUseCaseSync(
                this.mLoginHttpEndpointSyncMock,
                this.mAuthTokenCacheMock,
                this.mEventBusPosterMock
        );
        success();
    }

    @Test
    public void loginSync_success_usernameAndPasswordPassedToEndpoint() throws Exception {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        this.SUT.loginSync(USERNAME, PASSWORD);
        verify(mLoginHttpEndpointSyncMock, times(1)).loginSync(stringCaptor.capture(), stringCaptor.capture());
        List<String> values = stringCaptor.getAllValues();
        assertThat(values.get(0), is(USERNAME));
        assertThat(values.get(1), is(PASSWORD));
    }

    @Test
    public void loginSync_success_authTokenCached() throws Exception {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        this.SUT.loginSync(USERNAME, PASSWORD);
        verify(this.mAuthTokenCacheMock).cacheAuthToken(stringCaptor.capture());
        assertThat(stringCaptor.getValue(), is(AUTH_TOKEN));
    }

    @Test
    public void loginSync_generalError_authTokenNotCached() throws Exception {
        generalError();
        this.SUT.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(this.mAuthTokenCacheMock);
    }

    @Test
    public void loginSync_authError_authTokenNotCached() throws Exception {
        authError();
        this.SUT.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(this.mAuthTokenCacheMock);
    }

    @Test
    public void loginSync_serverError_authTokenNotCached() throws Exception {
        serverError();
        this.SUT.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(this.mAuthTokenCacheMock);
    }

    @Test
    public void loginSync_success_loggedInEventPosted() throws Exception {
        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        this.SUT.loginSync(USERNAME, PASSWORD);
        verify(this.mEventBusPosterMock).postEvent(captor.capture());
        assertThat(captor.getValue(), is(instanceOf(LoggedInEvent.class)));
    }

    @Test
    public void loginSync_generalError_noInteractionWithEventBusPoster() throws Exception {
        generalError();
        this.SUT.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(this.mEventBusPosterMock);
    }

    @Test
    public void loginSync_authError_noInteractionWithEventBusPoster() throws Exception {
        authError();
        this.SUT.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(this.mEventBusPosterMock);
    }

    @Test
    public void loginSync_serverError_noInteractionWithEventBusPoster() throws Exception {
        serverError();
        this.SUT.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(this.mEventBusPosterMock);
    }

    @Test
    public void loginSync_success_successReturned() throws Exception {
        UseCaseResult result = this.SUT.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(UseCaseResult.SUCCESS));
    }

    @Test
    public void loginSync_serverError_failureReturned() throws Exception {
        serverError();
        UseCaseResult result = this.SUT.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void loginSync_authError_failureReturned() throws Exception {
        authError();
        UseCaseResult result = this.SUT.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void loginSync_generalError_failureReturned() throws Exception {
        generalError();
        UseCaseResult result = this.SUT.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void loginSync_networkError_networkErrorReturned() throws Exception {
        networkError();
        UseCaseResult result = this.SUT.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(UseCaseResult.NETWORK_ERROR));
    }

    private void success() throws NetworkErrorException {
        when(this.mLoginHttpEndpointSyncMock.loginSync(anyString(), anyString()))
                .thenReturn(new LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.SUCCESS, AUTH_TOKEN));
    }

    private void generalError() throws NetworkErrorException {
        doReturn(new LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR, ""))
                .when(this.mLoginHttpEndpointSyncMock)
                .loginSync(anyString(), anyString());
    }

    private void authError() throws NetworkErrorException {
        doReturn(new LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.AUTH_ERROR, ""))
                .when(this.mLoginHttpEndpointSyncMock)
                .loginSync(anyString(), anyString());

    }

    private void serverError() throws NetworkErrorException {
        doReturn(new LoginHttpEndpointSync.EndpointResult(LoginHttpEndpointSync.EndpointResultStatus.SERVER_ERROR, ""))
                .when(this.mLoginHttpEndpointSyncMock)
                .loginSync(anyString(), anyString());

    }

    private void networkError() throws NetworkErrorException {
        when(this.mLoginHttpEndpointSyncMock.loginSync(anyString(), anyString()))
                .thenThrow(new NetworkErrorException());
    }

}