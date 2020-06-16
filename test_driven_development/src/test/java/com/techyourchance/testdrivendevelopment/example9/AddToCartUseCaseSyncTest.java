package com.techyourchance.testdrivendevelopment.example9;

import com.techyourchance.testdrivendevelopment.example9.networking.AddToCartHttpEndpointSync;
import com.techyourchance.testdrivendevelopment.example9.networking.CartItemScheme;
import com.techyourchance.testdrivendevelopment.example9.networking.NetworkErrorException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddToCartUseCaseSyncTest {

    // ---------START CONSTANTS--------------

    public final String OFFER_ID = "offerId";

    public final int AMOUNT = 4;

    // -----------END CONSTANTS--------------

    // -----------START HELPER---------------
    @Mock
    public AddToCartHttpEndpointSync mAddToCartHttpEndpointSyncMock;

    // -----------END HELPER-----------------

    private AddToCartUseCaseSync SUT;

    @Before
    public void setup() throws Exception {
        this.SUT = new AddToCartUseCaseSync(mAddToCartHttpEndpointSyncMock);
        success();
    }

    @Test
    public void addToCartSync_parametersPassedToEndpoint() throws Exception {
        // Arrange
        ArgumentCaptor<CartItemScheme> cartItemCaptor = ArgumentCaptor.forClass(CartItemScheme.class);
        // Act
        this.SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
        verify(this.mAddToCartHttpEndpointSyncMock).addToCartSync(cartItemCaptor.capture());
        assertThat(cartItemCaptor.getValue().getOfferId(), is(OFFER_ID));
        assertThat(cartItemCaptor.getValue().getAmount(), is(AMOUNT));
    }

    @Test
    public void addToCartSync_success_successReturned() throws Exception {
        // Arrange
        // Act
        AddToCartUseCaseSync.UseCaseResult result = this.SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
        assertThat(result, is(AddToCartUseCaseSync.UseCaseResult.SUCCESS));
    }

    @Test
    public void addToCartSync_generalError_failureReturned() throws Exception {
        // Arrange
        generalError();
        // Act
        AddToCartUseCaseSync.UseCaseResult result = this.SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
        assertThat(result, is(AddToCartUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void addToCartSync_authError_failureReturned() throws Exception {
        // Arrange
        authError();
        // Act
        AddToCartUseCaseSync.UseCaseResult result = this.SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
        assertThat(result, is(AddToCartUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void addToCartSync_networkError_networkErrorReturned() throws Exception {
        // Arrange
        networkError();
        // Act
        AddToCartUseCaseSync.UseCaseResult result = this.SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
        assertThat(result, is(AddToCartUseCaseSync.UseCaseResult.NETWORK_ERROR));
    }

    // -----------START HELPER METHODS--------

    private void success() throws NetworkErrorException {
        doReturn(AddToCartHttpEndpointSync.EndpointResult.SUCCESS)
                .when(this.mAddToCartHttpEndpointSyncMock)
                .addToCartSync(any(CartItemScheme.class));
    }

    private void authError() throws NetworkErrorException {
        doReturn(AddToCartHttpEndpointSync.EndpointResult.AUTH_ERROR)
                .when(this.mAddToCartHttpEndpointSyncMock)
                .addToCartSync(any(CartItemScheme.class));
    }

    private void generalError() throws NetworkErrorException {
        doReturn(AddToCartHttpEndpointSync.EndpointResult.GENERAL_ERROR)
                .when(this.mAddToCartHttpEndpointSyncMock)
                .addToCartSync(any(CartItemScheme.class));
    }

    private void networkError() throws NetworkErrorException {
        doThrow(new NetworkErrorException())
                .when(this.mAddToCartHttpEndpointSyncMock)
                .addToCartSync(any(CartItemScheme.class));
    }

    // -----------END HELPER METHODS----------

}