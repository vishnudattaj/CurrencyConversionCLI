import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CurrencyConverterTest {

    @Test
    public void testConversion() {
        assertEquals(8500, CurrencyConverter.convertCurrency(100, 85), 0.001);
        assertEquals(40, CurrencyConverter.convertCurrency(50, 0.8), 0.001);
    }

    @Test
    public void testZeroAmount() {
        assertEquals(0, CurrencyConverter.convertCurrency(0, 85), 0.001);
    }

    @Test
    public void testSameCurrency() {
        assertEquals(100, CurrencyConverter.convertCurrency(100, 1), 0.001);
    }

    @Test
    public void testDecimalAmount() {
        assertEquals(212.5, CurrencyConverter.convertCurrency(2.5, 85), 0.001);
    }

    @Test
    public void testParseRate() throws Exception {
        String json = "{\"result\":\"success\",\"rates\":{\"INR\":85,\"EUR\":0.8}}";

        assertEquals(85, CurrencyConverter.parseRate(json, "INR"), 0.001);
        assertEquals(0.8, CurrencyConverter.parseRate(json, "EUR"), 0.001);
    }

    @Test
    public void testInvalidTargetCurrency() {
        String json = "{\"result\":\"success\",\"rates\":{\"INR\":85}}";

        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyConverter.parseRate(json, "XYZ");
        });
    }

    @Test
    public void testAPIFailure() {
        String json = "{\"result\":\"error\"}";

        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyConverter.parseRate(json, "INR");
        });
    }
}