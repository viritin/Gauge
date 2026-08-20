package in.virit;

import in.virit.color.Color;
import in.virit.color.HexColor;

/**
 * A specialized gauge for displaying temperature values with appropriate
 * color coding and range settings for temperature measurements.
 */
public class TemperatureGauge extends Gauge {

    public TemperatureGauge() {
        this(0.0);
    }

    public TemperatureGauge(double temperature) {
        super();
        resetToDefaults();
        setTemperature(temperature);
    }

    /**
     * Restores the stock range and colours.
     * <p>
     * Public on purpose: an application that colours the dial by its own
     * configured bands needs a way back to the stock gauge when that
     * configuration is cleared — and while this was private, the only way back
     * was to duplicate these values in application code and hope they do not
     * drift.
     */
    public void resetToDefaults() {
        setMinValue(-40);
        setMaxValue(50);
        setState("gaugeType", "temperature");
        /*
           A cold-to-hot ramp, not a traffic light. The old defaults were green,
           yellow, orange and red — a scale of goodness, which is the wrong
           metaphor for a thermometer twice over: green said "cold" rather than
           "fine", and everything above 20 fell in the last band, so an ordinary
           living room glowed like an alarm. Blue-to-red says which direction the
           temperature is, and nothing about whether that is good — judging values
           is an application's job, through setArc.
        */
        setArc(new GaugeArc()
            .setSubArcs(
                new GaugeSubArc(-20, HexColor.of("#4F7CC4")).setTooltip("Cold"),
                new GaugeSubArc(0, HexColor.of("#7FAFD4")).setTooltip("Cool"),
                new GaugeSubArc(20, HexColor.of("#D9A15B")).setTooltip("Warm"),
                new GaugeSubArc(50, HexColor.of("#C4573C")).setTooltip("Hot")
            )
        );
        // Labels with formatTextValue will be handled by React component
    }

    public void setTemperature(double temperature) {
        setValue(temperature);
    }

    /**
     * Sets the temperature, or clears it: {@code null} shows an empty dial with a
     * dash where the reading would be. See {@link Gauge#setValue(Double)}.
     *
     * @param temperature the temperature, or null for no reading
     */
    public void setTemperature(Double temperature) {
        setValue(temperature);
    }

    public void setTemperatureRange(double minTemp, double maxTemp) {
        setMinValue(minTemp);
        setMaxValue(maxTemp);
    }

    public void setTemperatureUnit(TemperatureUnit unit) {
        String unitSymbol = unit == TemperatureUnit.FAHRENHEIT ? "fahrenheit" : "celsius";
        setState("temperatureUnit", unitSymbol);
    }

    public enum TemperatureUnit {
        CELSIUS, FAHRENHEIT
    }
}