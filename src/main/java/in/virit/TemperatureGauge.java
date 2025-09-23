package in.virit;

import in.virit.color.Color;
import in.virit.color.NamedColor;

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
        setupTemperatureDefaults();
        setTemperature(temperature);
    }

    private void setupTemperatureDefaults() {
        setMinValue(-40);
        setMaxValue(50);
        setState("gaugeType", "temperature");
        setArc(new GaugeArc()
            .setSubArcs(
                new GaugeSubArc(-20, NamedColor.GREEN).setTooltip("Cold"),
                new GaugeSubArc(0, NamedColor.YELLOW).setTooltip("Cool"),
                new GaugeSubArc(20, NamedColor.ORANGE).setTooltip("Warm"),
                new GaugeSubArc(50, NamedColor.RED).setTooltip("Hot")
            )
        );
        // Labels with formatTextValue will be handled by React component
    }

    public void setTemperature(double temperature) {
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