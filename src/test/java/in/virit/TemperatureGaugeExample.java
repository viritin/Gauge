package in.virit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import in.virit.color.Color;
import in.virit.color.NamedColor;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.util.VStyle;

@Route("temperature")
public class TemperatureGaugeExample extends VVerticalLayout {

    private TemperatureGauge temperatureGauge;
    private NumberField temperatureInput;
    private Span statusLabel;

    public TemperatureGaugeExample() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(new H2("Temperature Gauge Example"));

        // Create temperature gauge with default temperature (20°C)
        temperatureGauge = new TemperatureGauge(20);
        add(temperatureGauge);

        // Add input controls
        createControls();

        // Add status display
        statusLabel = new Span(getTemperatureStatus(20));
        statusLabel.getStyle().setFontSize("18px");
        statusLabel.getStyle().setFontWeight("bold");
        add(statusLabel);
    }

    private void createControls() {
        temperatureInput = new NumberField("Temperature (°C)");
        temperatureInput.setValue(20.0);
        temperatureInput.setStep(0.1);
        temperatureInput.setMin(-40.0);
        temperatureInput.setMax(50.0);
        temperatureInput.setStepButtonsVisible(true);

        Button updateButton = new Button("Update Temperature", e -> updateTemperature());

        Button coldButton = new Button("Simulate Cold (-10°C)", e -> setTemperature(-10));
        Button normalButton = new Button("Simulate Normal (22°C)", e -> setTemperature(22));
        Button hotButton = new Button("Simulate Hot (35°C)", e -> setTemperature(35));
        Button noReading = new Button("No reading",
                e -> temperatureGauge.setTemperature((Double) null));

        HorizontalLayout controls = new HorizontalLayout(temperatureInput, updateButton);
        controls.setAlignItems(Alignment.END);

        HorizontalLayout presets = new HorizontalLayout(coldButton, normalButton, hotButton, noReading);

        add(controls, presets);
    }

    private void updateTemperature() {
        if (temperatureInput.getValue() != null) {
            setTemperature(temperatureInput.getValue());
        }
    }

    private void setTemperature(double temperature) {
        temperatureGauge.setTemperature(temperature);
        temperatureInput.setValue(temperature);
        statusLabel.setText(getTemperatureStatus(temperature));
        updateStatusColor(temperature);
    }

    private String getTemperatureStatus(double temperature) {
        if (temperature < -20) return "Extremely Cold ❄️";
        if (temperature < 0) return "Very Cold 🥶";
        if (temperature < 10) return "Cold 😰";
        if (temperature < 20) return "Cool 😊";
        if (temperature < 30) return "Warm 😌";
        if (temperature < 40) return "Hot 🥵";
        return "Extremely Hot 🔥";
    }

    private void updateStatusColor(double temperature) {
        Color color;
        if (temperature < 0) color = NamedColor.GREEN;  // Green for cold
        else if (temperature < 20) color = NamedColor.YELLOW;  // Yellow for cool
        else if (temperature < 30) color = NamedColor.ORANGE;  // Orange for warm
        else color = NamedColor.RED;  // Red for hot

        VStyle.wrap(statusLabel.getStyle()).setColor(color);
    }
}