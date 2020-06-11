package com.techyourchance.testdoublesfundamentals.example4;

import com.techyourchance.testdoublesfundamentals.example4.authtoken.AuthTokenCache;
import com.techyourchance.testdoublesfundamentals.example4.eventbus.EventBusPoster;
import com.techyourchance.testdoublesfundamentals.example4.eventbus.LoggedInEvent;
import com.techyourchance.testdoublesfundamentals.example4.networking.LoginHttpEndpointSync;
import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LoginUseCaseSyncTest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String AUTH_TOKEN = "authToken";

    private LoginUseCaseSync SUT;
    private LoginHttpEndpointSyncTd mLoginHttpEndpointSyncTd;
    private AuthTokenCacheTd mAuthTokenCacheTd;
    private EventBusPosterTd mEventBusPosterTd;

    @Before
    public void setup() {
        this.mLoginHttpEndpointSyncTd = new LoginHttpEndpointSyncTd();
        this.mAuthTokenCacheTd = new AuthTokenCacheTd();
        this.mEventBusPosterTd = new EventBusPosterTd();
        this.SUT = new LoginUseCaseSync(
                this.mLoginHttpEndpointSyncTd,
                this.mAuthTokenCacheTd,
                this.mEventBusPosterTd
        );
    }

    //success - username & passowrd passed to endpoint
    @Test
    public void loginSync_success_usernameAndPasswordPassedToEndpoint() {
        this.SUT.loginSync(USERNAME, PASSWORD);
        assertThat(mLoginHttpEndpointSyncTd.mUsername, is(USERNAME));
        assertThat(mLoginHttpEndpointSyncTd.mPassword, is(PASSWORD));
    }

    //success - auth token cached
    @Test
    public void loginSync_success_authTokenCached() {
        this.SUT.loginSync(USERNAME, PASSWORD);
        assertThat(mAuthTokenCacheTd.getAuthToken(), is(AUTH_TOKEN));
    }

    //general error - auth token not cached
    @Test
    public void loginSync_generalError_authTokenNotCached() {
        this.mLoginHttpEndpointSyncTd.generalError = true;
        this.SUT.loginSync(USERNAME, PASSWORD);
        assertThat(mAuthTokenCacheTd.getAuthToken(), is(""));
    }

    //auth error - auth token not cached
    @Test
    public void loginSync_authError_authTokenNotCached() {
        this.mLoginHttpEndpointSyncTd.authError = true;
        this.SUT.loginSync(USERNAME, PASSWORD);
        assertThat(mAuthTokenCacheTd.getAuthToken(), is(""));
    }

    //server error - auth token not cached
    @Test
    public void loginSync_serverError_authTokenNotCached() {
        this.mLoginHttpEndpointSyncTd.serverError = true;
        this.SUT.loginSync(USERNAME, PASSWORD);
        assertThat(mAuthTokenCacheTd.getAuthToken(), is(""));
    }

    //success - logged in event posted
    @Test
    public void loginSync_success_loggedIntEventPosted() {
        SUT.loginSync(USERNAME, PASSWORD);
        assertThat(mEventBusPosterTd.mEvent, is(instanceOf(LoggedInEvent.class)));
    }

    //general error - no interaction with event bus poster
    @Test
    public void loginSync_generalError_noInteractionWithEventBusPoster() {
        mLoginHttpEndpointSyncTd.generalError = true;
        SUT.loginSync(USERNAME, PASSWORD);
        assertThat(mEventBusPosterTd.mCounter, is(0));
    }

    //auth error - no interaction with event bus poster
    @Test
    public void loginSync_authError_noInteractionWithEventBusPoster() {
        mLoginHttpEndpointSyncTd.authError = true;
        SUT.loginSync(USERNAME, PASSWORD);
        assertThat(mEventBusPosterTd.mCounter, is(0));
    }

    //server error - no interaciton with event bus poster
    @Test
    public void loginSync_serverError_noInteractionWithEventBusPoster() {
        mLoginHttpEndpointSyncTd.serverError = true;
        SUT.loginSync(USERNAME, PASSWORD);
        assertThat(mEventBusPosterTd.mCounter, is(0));
    }

    //success - success returned
    @Test
    public void loginSync_success_successReturned() {
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.SUCCESS));
    }

    //server error - failure returned
    @Test
    public void loginSync_serverError_failureReturned() {
        mLoginHttpEndpointSyncTd.serverError = true;
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }

    //auth error - failure returned
    @Test
    public void loginSync_authError_failureReturned() {
        mLoginHttpEndpointSyncTd.authError = true;
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }

    //general error - failure returned
    @Test
    public void loginSync_generalError_failureReturned() {
        mLoginHttpEndpointSyncTd.generalError = true;
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.FAILURE));
    }

    //network error - network error returned
    @Test
    public void loginSync_networkError_failureReturned() {
        mLoginHttpEndpointSyncTd.networkError = true;
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, PASSWORD);
        assertThat(result, is(LoginUseCaseSync.UseCaseResult.NETWORK_ERROR));
    }

    private static class LoginHttpEndpointSyncTd implements LoginHttpEndpointSync {

        public String mUsername = "";
        public String mPassword = "";
        public boolean generalError = false;
        public boolean authError = false;
        public boolean serverError = false;
        public boolean networkError = false;

        @Override
        public EndpointResult loginSync(String username, String password) throws NetworkErrorException {
            mUsername = username;
            mPassword = password;
            if (generalError) {
                return new EndpointResult(EndpointResultStatus.GENERAL_ERROR, "");
            } else if (authError) {
                return new EndpointResult(EndpointResultStatus.AUTH_ERROR, "");
            } else if (serverError) {
                return new EndpointResult(EndpointResultStatus.SERVER_ERROR, "");
            } else if (networkError) {
                throw new NetworkErrorException();
            } else {
                return new EndpointResult(EndpointResultStatus.SUCCESS, AUTH_TOKEN);
            }
        }
    }

    private static class AuthTokenCacheTd implements AuthTokenCache {

        String mAuthToken = "";

        @Override
        public void cacheAuthToken(String authToken) {
            mAuthToken = authToken;
        }

        @Override
        public String getAuthToken() {
            return mAuthToken;
        }
    }

    private static class EventBusPosterTd implements EventBusPoster {

        public Object mEvent;
        public int mCounter = 0;

        @Override
        public void postEvent(Object event) {
            mCounter++;
            mEvent = event;
        }
    }

}