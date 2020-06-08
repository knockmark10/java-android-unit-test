package com.techyourchance.testdrivendevelopment.example10;

import com.techyourchance.testdrivendevelopment.example10.networking.PingServerHttpEndpointSync;

public class PingServerSyncUseCaseSolved {

    public enum UseCaseResult {
        FAILURE,
        SUCCESS
    }

    private final PingServerHttpEndpointSync mPingServerHttpEndpointSync;

    public PingServerSyncUseCaseSolved(PingServerHttpEndpointSync pingServerHttpEndpointSync) {
        mPingServerHttpEndpointSync = pingServerHttpEndpointSync;
    }

    public UseCaseResult pingServerSync() {
        PingServerHttpEndpointSync.EndpointResult result = mPingServerHttpEndpointSync.pingServerSync();
        switch (result) {
            case GENERAL_ERROR:
            case NETWORK_ERROR:
                return UseCaseResult.FAILURE;
            case SUCCESS:
                return UseCaseResult.SUCCESS;
            default:
                throw new RuntimeException("invalid result: " + result);
        }
    }

}
