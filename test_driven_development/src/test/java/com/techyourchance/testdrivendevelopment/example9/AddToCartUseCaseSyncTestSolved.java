package com.techyourchance.testdrivendevelopment.example9;

import com.techyourchance.testdrivendevelopment.example9.AddToCartUseCaseSyncSolved.UseCaseResult;
import com.techyourchance.testdrivendevelopment.example9.networking.AddToCartHttpEndpointSync;
import com.techyourchance.testdrivendevelopment.example9.networking.CartItemScheme;
import com.techyourchance.testdrivendevelopment.example9.networking.NetworkErrorException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.techyourchance.testdrivendevelopment.example9.networking.AddToCartHttpEndpointSync.EndpointResult.AUTH_ERROR;
import static com.techyourchance.testdrivendevelopment.example9.networking.AddToCartHttpEndpointSync.EndpointResult.GENERAL_ERROR;
import static com.techyourchance.testdrivendevelopment.example9.networking.AddToCartHttpEndpointSync.EndpointResult.SUCCESS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddToCartUseCaseSyncTestSolved {

    // region constants ----------------------------------------------------------------------------

    public static final String OFFER_ID = "offerId";
    public static final int AMOUNT = 4;

    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------
    @Mock
    AddToCartHttpEndpointSync mAddToCartHttpEndpointSyncMock;
    // endregion helper fields ---------------------------------------------------------------------

    AddToCartUseCaseSyncSolved SUT;

    @Before
    public void setup() throws Exception {
        SUT = new AddToCartUseCaseSyncSolved(mAddToCartHttpEndpointSyncMock);
        success();
    }

    @Test
    public void addToCartSync_correctParametersPassedToEndpoint() throws Exception {
        // Arrange
        ArgumentCaptor<CartItemScheme> ac = ArgumentCaptor.forClass(CartItemScheme.class);
        // Act
        SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
        verify(mAddToCartHttpEndpointSyncMock).addToCartSync(ac.capture());
        assertThat(ac.getValue().getOfferId(), is(OFFER_ID));
        assertThat(ac.getValue().getAmount(), is(AMOUNT));
    }

    @Test
    public void addToCartSync_success_successReturned() throws Exception {
        // Arrange
        // Act
        UseCaseResult result = SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
        assertThat(result, is(UseCaseResult.SUCCESS));
    }

    @Test
    public void addToCartSync_authError_failureReturned() throws Exception {
        // Arrange
        authError();
        // Act
        UseCaseResult result = SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void addToCartSync_generalError_failureReturned() throws Exception {
        // Arrange
        generalError();
        // Act
        UseCaseResult result = SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void addToCartSync_networkError_networkErrorReturned() throws Exception {
        // Arrange
        networkError();
        // Act
        UseCaseResult result = SUT.addToCartSync(OFFER_ID, AMOUNT);
        // Assert
        assertThat(result, is(UseCaseResult.NETWORK_ERROR));
    }

    // region helper methods -----------------------------------------------------------------------

    private void success() throws NetworkErrorException {
        when(mAddToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
                .thenReturn(SUCCESS);
    }

    private void authError() throws NetworkErrorException {
        when(mAddToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
                .thenReturn(AUTH_ERROR);
    }

    private void generalError() throws NetworkErrorException {
        when(mAddToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
                .thenReturn(GENERAL_ERROR);
    }

    private void networkError() throws NetworkErrorException {
        when(mAddToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
                .thenThrow(new NetworkErrorException());
    }

    // endregion helper methods --------------------------------------------------------------------

    // region helper classes -----------------------------------------------------------------------
    // endregion helper classes --------------------------------------------------------------------

}