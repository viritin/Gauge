package in.virit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

@Route("humidity")
public class HumidityGaugeExample extends VVerticalLayout {

    private HumidityGauge humidityGauge;
    private NumberField humidityInput;
    private Span statusLabel;
    private Span recommendationLabel;

    public HumidityGaugeExample() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(new H2("Humidity Gauge Example"));

        // Create humidity gauge with default humidity (45%)
        humidityGauge = new HumidityGauge(45);
        add(humidityGauge);

        // Add input controls
        createControls();

        // Add status and recommendation displays
        statusLabel = new Span(getHumidityStatus(45));
        statusLabel.getStyle().setFontSize("18px");
        statusLabel.getStyle().setFontWeight("bold");

        recommendationLabel = new Span(getHumidityRecommendation(45));
        recommendationLabel.getStyle().setFontSize("14px");

        add(statusLabel, recommendationLabel);
    }

    private void createControls() {
        humidityInput = new NumberField("Humidity (%)");
        humidityInput.setValue(45.0);

        Button updateButton = new Button("Update Humidity", e -> updateHumidity());

        Button dryButton = new Button("Simulate Dry (20%)", e -> setHumidity(20));
        Button comfortableButton = new Button("Simulate Comfortable (45%)", e -> setHumidity(45));
        Button optimalButton = new Button("Simulate Optimal (60%)", e -> setHumidity(60));
        Button humidButton = new Button("Simulate Humid (80%)", e -> setHumidity(80));

        HorizontalLayout controls = new HorizontalLayout(humidityInput, updateButton);
        controls.setAlignItems(Alignment.END);

        HorizontalLayout presets = new HorizontalLayout(dryButton, comfortableButton, optimalButton, humidButton);

        add(controls, presets);
    }

    private void updateHumidity() {
        if (humidityInput.getValue() != null) {
            setHumidity(humidityInput.getValue());
        }
    }

    private void setHumidity(double humidity) {
        humidityGauge.setHumidity(humidity);
        humidityInput.setValue(humidity);
        statusLabel.setText(getHumidityStatus(humidity));
        recommendationLabel.setText(getHumidityRecommendation(humidity));
        updateStatusColor(humidity);
    }

    private String getHumidityStatus(double humidity) {
        return humidityGauge.getHumidityLevel(humidity).getDisplayText();
    }

    private String getHumidityRecommendation(double humidity) {
        return "💡 " + humidityGauge.getHumidityRecommendation(humidity);
    }

    private void updateStatusColor(double humidity) {
        String color;
        if (humidity < 30) color = "#EA4228";  // Red for dry
        else if (humidity < 40) color = "#FF8C00";  // Orange for low
        else if (humidity <= 70) color = "#5BE12C";  // Green for optimal
        else color = "#F5CD19";  // Yellow for high

        statusLabel.getStyle().set("color", color);
    }
}