package com.techyourchance.testdrivendevelopment.example9;

import com.techyourchance.testdrivendevelopment.example9.networking.AddToCartHttpEndpointSync;
import com.techyourchance.testdrivendevelopment.example9.networking.CartItemScheme;
import com.techyourchance.testdrivendevelopment.example9.networking.NetworkErrorException;

public class AddToCartUseCaseSync {

    public enum UseCaseResult {
        SUCCESS,
        FAILURE,
        NETWORK_ERROR,
    }

    private AddToCartHttpEndpointSync mAddToCartHttpEndpointSyncMock;

    public AddToCartUseCaseSync(AddToCartHttpEndpointSync addToCartHttpEndpointSyncMock) {
        mAddToCartHttpEndpointSyncMock = addToCartHttpEndpointSyncMock;
    }

    public UseCaseResult addToCartSync(String offerId, int amount) {
        AddToCartHttpEndpointSync.EndpointResult result;
        try {
            result = this.mAddToCartHttpEndpointSyncMock.addToCartSync(new CartItemScheme(offerId, amount));
            switch (result) {
                case SUCCESS:
                    return UseCaseResult.SUCCESS;
                case AUTH_ERROR:
                case GENERAL_ERROR:
                    return UseCaseResult.FAILURE;
                default:
                    throw new RuntimeException("Invalid result: " + result);
            }
        } catch (NetworkErrorException e) {
            return UseCaseResult.NETWORK_ERROR;
        }
    }

}
