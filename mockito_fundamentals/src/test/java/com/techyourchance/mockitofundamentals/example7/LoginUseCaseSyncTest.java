package com.techyourchance.mockitofundamentals.example7;

import org.junit.Test;

public class LoginUseCaseSyncTest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String AUTH_TOKEN = "authToken";

    private LoginUseCaseSync SUT;


    @Test
    public void loginSync_success_usernameAndPasswordPassedToEndpoint() throws Exception {

    }

    @Test
    public void loginSync_success_authTokenCached() throws Exception {

    }

    @Test
    public void loginSync_generalError_authTokenNotCached() throws Exception {

    }

    @Test
    public void loginSync_authError_authTokenNotCached() throws Exception {

    }

    @Test
    public void loginSync_serverError_authTokenNotCached() throws Exception {

    }

    @Test
    public void loginSync_success_loggedInEventPosted() throws Exception {

    }

    @Test
    public void loginSync_generalError_noInteractionWithEventBusPoster() throws Exception {

    }

    @Test
    public void loginSync_authError_noInteractionWithEventBusPoster() throws Exception {

    }

    @Test
    public void loginSync_serverError_noInteractionWithEventBusPoster() throws Exception {

    }

    @Test
    public void loginSync_success_successReturned() throws Exception {

    }

    @Test
    public void loginSync_serverError_failureReturned() throws Exception {

    }

    @Test
    public void loginSync_authError_failureReturned() throws Exception {

    }

    @Test
    public void loginSync_generalError_failureReturned() throws Exception {

    }

    @Test
    public void loginSync_networkError_networkErrorReturned() throws Exception {

    }

}