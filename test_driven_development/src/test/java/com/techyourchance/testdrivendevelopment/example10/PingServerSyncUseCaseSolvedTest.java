package com.techyourchance.testdrivendevelopment.example10;

import com.techyourchance.testdrivendevelopment.example10.networking.PingServerHttpEndpointSync;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.techyourchance.testdrivendevelopment.example10.PingServerSyncUseCaseSolved.UseCaseResult;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PingServerSyncUseCaseSolvedTest {

    // region constants ----------------------------------------------------------------------------
    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------
    @Mock
    PingServerHttpEndpointSync mPingServerHttpEndpointSyncMock;
    // endregion helper fields ---------------------------------------------------------------------

    PingServerSyncUseCaseSolved SUT;

    @Before
    public void setup() throws Exception {
        SUT = new PingServerSyncUseCaseSolved(mPingServerHttpEndpointSyncMock);
        success();
    }

    @Test
    public void pingServerSync_success_successReturned() throws Exception {
        // Arrange
        // Act
        UseCaseResult result = SUT.pingServerSync();
        // Assert
        assertThat(result, is(UseCaseResult.SUCCESS));
    }

    @Test
    public void pingServerSync_generalError_failureReturned() throws Exception {
        // Arrange
        generalError();
        // Act
        UseCaseResult result = SUT.pingServerSync();
        // Assert
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void pingServerSync_networkError_failureReturned() throws Exception {
        // Arrange
        networkError();
        // Act
        UseCaseResult result = SUT.pingServerSync();
        // Assert
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    // region helper methods -----------------------------------------------------------------------

    private void success() {
        when(mPingServerHttpEndpointSyncMock.pingServerSync()).thenReturn(PingServerHttpEndpointSync.EndpointResult.SUCCESS);
    }

    private void networkError() {
        when(mPingServerHttpEndpointSyncMock.pingServerSync()).thenReturn(PingServerHttpEndpointSync.EndpointResult.NETWORK_ERROR);
    }

    private void generalError() {
        when(mPingServerHttpEndpointSyncMock.pingServerSync()).thenReturn(PingServerHttpEndpointSync.EndpointResult.GENERAL_ERROR);
    }

    // endregion helper methods --------------------------------------------------------------------

    // region helper classes -----------------------------------------------------------------------
    // endregion helper classes --------------------------------------------------------------------

}