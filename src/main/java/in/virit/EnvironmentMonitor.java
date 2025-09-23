package in.virit;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * A reusable component that displays both temperature and humidity gauges
 * with appropriate titles. Provides methods to update the environmental values.
 */
public class EnvironmentMonitor extends HorizontalLayout {

    private TemperatureGauge temperatureGauge;
    private HumidityGauge humidityGauge;

    public EnvironmentMonitor() {
        this(20.0, 50.0); // Default values
    }

    public EnvironmentMonitor(double initialTemperature, double initialHumidity) {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.AROUND);

        // Create temperature section
        VerticalLayout tempSection = createTemperatureSection(initialTemperature);

        // Create humidity section
        VerticalLayout humiditySection = createHumiditySection(initialHumidity);

        add(tempSection, humiditySection);
    }

    private VerticalLayout createTemperatureSection(double initialTemperature) {
        VerticalLayout tempSection = new VerticalLayout();
        tempSection.setAlignItems(Alignment.CENTER);
        tempSection.setSpacing(false);

        H3 tempTitle = new H3("🌡️ Temperature");
        tempTitle.getStyle().setMargin("0 0 1rem 0");

        temperatureGauge = new TemperatureGauge(initialTemperature);

        tempSection.add(tempTitle, temperatureGauge);
        return tempSection;
    }

    private VerticalLayout createHumiditySection(double initialHumidity) {
        VerticalLayout humiditySection = new VerticalLayout();
        humiditySection.setAlignItems(Alignment.CENTER);
        humiditySection.setSpacing(false);

        H3 humidityTitle = new H3("💧 Humidity");
        humidityTitle.getStyle().setMargin("0 0 1rem 0");

        humidityGauge = new HumidityGauge(initialHumidity);

        humiditySection.add(humidityTitle, humidityGauge);
        return humiditySection;
    }

    /**
     * Updates the temperature value displayed on the gauge.
     * @param temperature the new temperature value
     */
    public void setTemperature(double temperature) {
        temperatureGauge.setTemperature(temperature);
    }

    /**
     * Updates the humidity value displayed on the gauge.
     * @param humidity the new humidity value (0-100)
     */
    public void setHumidity(double humidity) {
        humidityGauge.setHumidity(humidity);
    }

    /**
     * Updates both temperature and humidity values.
     * @param temperature the new temperature value
     * @param humidity the new humidity value (0-100)
     */
    public void setEnvironmentValues(double temperature, double humidity) {
        setTemperature(temperature);
        setHumidity(humidity);
    }

    /**
     * Gets the current temperature gauge component.
     * @return the TemperatureGauge instance
     */
    public TemperatureGauge getTemperatureGauge() {
        return temperatureGauge;
    }

    /**
     * Gets the current humidity gauge component.
     * @return the HumidityGauge instance
     */
    public HumidityGauge getHumidityGauge() {
        return humidityGauge;
    }

    /**
     * Sets the temperature unit for the temperature gauge.
     * @param unit the temperature unit (CELSIUS or FAHRENHEIT)
     */
    public void setTemperatureUnit(TemperatureGauge.TemperatureUnit unit) {
        temperatureGauge.setTemperatureUnit(unit);
    }

    /**
     * Sets the temperature range for the temperature gauge.
     * @param minTemp minimum temperature value
     * @param maxTemp maximum temperature value
     */
    public void setTemperatureRange(double minTemp, double maxTemp) {
        temperatureGauge.setTemperatureRange(minTemp, maxTemp);
    }
}