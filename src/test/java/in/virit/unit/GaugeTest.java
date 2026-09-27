package in.virit.unit;

import in.virit.Gauge;
import in.virit.TemperatureGauge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GaugeTest {

    @Test
    public void aGaugeCanBeConstructed() {
        new Gauge();
    }

    /**
     * Emptiness travels as a boolean state, not as a null value. The React
     * adapter hands the browser the default for a null state, which once turned
     * "no reading" into a reading of zero — this pins the shape of the fix.
     */
    @Test
    public void clearingTheValueSetsTheEmptyState() {
        StateProbe gauge = new StateProbe();

        gauge.setTemperature((Double) null);
        assertTrue(gauge.empty(), "null means no reading");

        gauge.setTemperature(21.5);
        assertFalse(gauge.empty(), "a reading ends the emptiness");
    }

    @Test
    public void aTemperatureReadsInTenthsOfADegreeCelsius() {
        StateProbe gauge = new StateProbe();
        assertEquals("°C", gauge.state("unit", String.class));
        assertEquals(1, gauge.state("decimals", Integer.class));

        gauge.setTemperatureUnit(TemperatureGauge.TemperatureUnit.FAHRENHEIT);
        assertEquals("°F", gauge.state("unit", String.class));
        assertThrows(IllegalArgumentException.class, () -> gauge.setDecimals(-1));
    }

    /** The stylesheet reserves the height by type, so the type is also an attribute. */
    @Test
    public void theTypeIsAnAttributeForTheStylesheet() {
        Gauge gauge = new Gauge();
        gauge.setType(Gauge.GaugeType.RADIAL);
        assertEquals("radial", gauge.getElement().getAttribute("type"));
    }

    private static class StateProbe extends TemperatureGauge {
        boolean empty() {
            return getState("empty", Boolean.class);
        }

        <T> T state(String name, Class<T> type) {
            return getState(name, type);
        }
    }
}
