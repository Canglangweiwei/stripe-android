package com.stripe.android.util;

import android.support.annotation.NonNull;

import com.stripe.android.model.Card;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for {@link StripeNetworkUtils}
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 23)
public class StripeNetworkUtilsTest {

    private static final String CARD_NUMBER = "4242424242424242";
    private static final String CARD_CVC = "123";
    private static final String CARD_CITY = "San Francisco";
    private static final String CARD_CURRENCY = "USD";
    private static final String CARD_ADDRESS_L1 = "123 Main Street";
    private static final String CARD_ADDRESS_L2 = "906";
    private static final String CARD_ZIP = "94107";
    private static final String CARD_STATE = "CA";
    private static final String CARD_COUNTRY = "US";
    private static final String CARD_NAME = "J Q Public";

    @Test
    public void hashMapFromCard_mapsCorrectFields() {
        Card card = new Card.Builder(CARD_NUMBER, 8, 2019, CARD_CVC)
                .addressCity(CARD_CITY)
                .addressLine1(CARD_ADDRESS_L1)
                .addressLine2(CARD_ADDRESS_L2)
                .addressCountry(CARD_COUNTRY)
                .addressState(CARD_STATE)
                .addressZip(CARD_ZIP)
                .currency(CARD_CURRENCY)
                .name(CARD_NAME)
                .build();

        Map<String, Object> cardMap = getCardMapFromHashMappedCard(card);

        assertEquals(CARD_NUMBER, cardMap.get("number"));
        assertEquals(CARD_CVC, cardMap.get("cvc"));
        assertEquals(8, cardMap.get("exp_month"));
        assertEquals(2019, cardMap.get("exp_year"));
        assertEquals(CARD_NAME, cardMap.get("name"));
        assertEquals(CARD_CURRENCY, cardMap.get("currency"));
        assertEquals(CARD_ADDRESS_L1, cardMap.get("address_line1"));
        assertEquals(CARD_ADDRESS_L2, cardMap.get("address_line2"));
        assertEquals(CARD_CITY, cardMap.get("address_city"));
        assertEquals(CARD_ZIP, cardMap.get("address_zip"));
        assertEquals(CARD_STATE, cardMap.get("address_state"));
        assertEquals(CARD_COUNTRY, cardMap.get("address_country"));
    }

    @Test
    public void hashMapFromCard_omitsEmptyFields() {
        Card card = new Card.Builder(CARD_NUMBER, 8, 2019, CARD_CVC).build();

        Map<String, Object> cardMap = getCardMapFromHashMappedCard(card);

        assertEquals(CARD_NUMBER, cardMap.get("number"));
        assertEquals(CARD_CVC, cardMap.get("cvc"));
        assertEquals(8, cardMap.get("exp_month"));
        assertEquals(2019, cardMap.get("exp_year"));
        assertFalse(cardMap.containsKey("name"));
        assertFalse(cardMap.containsKey("currency"));
        assertFalse(cardMap.containsKey("address_line1"));
        assertFalse(cardMap.containsKey("address_line2"));
        assertFalse(cardMap.containsKey("address_city"));
        assertFalse(cardMap.containsKey("address_zip"));
        assertFalse(cardMap.containsKey("address_state"));
        assertFalse(cardMap.containsKey("address_country"));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getCardMapFromHashMappedCard(@NonNull Card card) {
        Map<String, Object> tokenMap = StripeNetworkUtils.hashMapFromCard(card);
        assertNotNull(tokenMap);
        return (Map<String, Object>) tokenMap.get("card");
    }
}
