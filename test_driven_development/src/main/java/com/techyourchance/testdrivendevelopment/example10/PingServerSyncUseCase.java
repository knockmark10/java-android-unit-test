package com.techyourchance.testdrivendevelopment.example10;

import com.techyourchance.testdrivendevelopment.example10.networking.PingServerHttpEndpointSync;

public class PingServerSyncUseCase {

    public enum UseCaseResult {
        FAILURE,
        SUCCESS
    }

    private PingServerHttpEndpointSync mPingServerHttpEndpointSync;

    public PingServerSyncUseCase(PingServerHttpEndpointSync mPingServerHttpEndpointSync) {
        this.mPingServerHttpEndpointSync = mPingServerHttpEndpointSync;
    }

    public UseCaseResult pingServerSync() {
        PingServerHttpEndpointSync.EndpointResult result = this.mPingServerHttpEndpointSync.pingServerSync();
        switch (result) {
            case SUCCESS:
                return UseCaseResult.SUCCESS;
            case GENERAL_ERROR:
            case NETWORK_ERROR:
                return UseCaseResult.FAILURE;
            default:
                throw new RuntimeException("Invalid result: " + result);
        }
    }

}
