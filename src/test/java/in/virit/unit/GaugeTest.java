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

    private static class StateProbe extends TemperatureGauge {
        boolean empty() {
            return getState("empty", Boolean.class);
        }
    }
}
