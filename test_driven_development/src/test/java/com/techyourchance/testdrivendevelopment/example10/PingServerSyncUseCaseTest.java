package com.techyourchance.testdrivendevelopment.example10;

import com.techyourchance.testdrivendevelopment.example10.networking.PingServerHttpEndpointSync;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class PingServerSyncUseCaseTest {

    // ---------START CONSTANTS--------------

    // -----------END CONSTANTS--------------

    // -----------START HELPER---------------

    @Mock
    public PingServerHttpEndpointSync mPingServerHttpEndpointSyncMock;

    // -----------END HELPER-----------------

    private PingServerSyncUseCase SUT;

    @Before
    public void setup() throws Exception {
        this.SUT = new PingServerSyncUseCase(mPingServerHttpEndpointSyncMock);
        success();
    }

    @Test
    public void pingServerSync_success_successReturned() throws Exception {
        // Arrange
        // Act
        PingServerSyncUseCase.UseCaseResult result = this.SUT.pingServerSync();
        // Assert
        assertThat(result, is(PingServerSyncUseCase.UseCaseResult.SUCCESS));
    }

    @Test
    public void pingServerSync_generalError_failureReturned() throws Exception {
        // Arrange
        generalError();
        // Act
        PingServerSyncUseCase.UseCaseResult result = this.SUT.pingServerSync();
        // Assert
        assertThat(result, is(PingServerSyncUseCase.UseCaseResult.FAILURE));
    }

    @Test
    public void pingServerSync_networkError_failureReturned() throws Exception {
        // Arrange
        networkError();
        // Act
        PingServerSyncUseCase.UseCaseResult result = this.SUT.pingServerSync();
        // Assert
        assertThat(result, is(PingServerSyncUseCase.UseCaseResult.FAILURE));
    }

    // -----------START HELPER METHODS--------

    private void success() {
        doReturn(PingServerHttpEndpointSync.EndpointResult.SUCCESS)
                .when(this.mPingServerHttpEndpointSyncMock)
                .pingServerSync();
    }

    private void generalError() {
        doReturn(PingServerHttpEndpointSync.EndpointResult.GENERAL_ERROR)
                .when(this.mPingServerHttpEndpointSyncMock)
                .pingServerSync();
    }

    private void networkError() {
        doReturn(PingServerHttpEndpointSync.EndpointResult.NETWORK_ERROR)
                .when(this.mPingServerHttpEndpointSyncMock)
                .pingServerSync();
    }

    // -----------END HELPER METHODS----------

}