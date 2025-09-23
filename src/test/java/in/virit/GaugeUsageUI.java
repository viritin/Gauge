package in.virit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

@Route
public class GaugeUsageUI extends VVerticalLayout {

    private EnvironmentMonitor environmentMonitor;

    public GaugeUsageUI() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        // Header
        add(new H1("🌡️💧 Environment Monitor Demo"));

        // Create the environment monitor with default values
        environmentMonitor = new EnvironmentMonitor(22.5, 45.0);
        add(environmentMonitor);

        // Add some demo controls
        createDemoControls();
    }

    private void createDemoControls() {
        Button coldButton = new Button("❄️ Cold Weather", e ->
            environmentMonitor.setEnvironmentValues(-5, 60));

        Button normalButton = new Button("🌤️ Normal Weather", e ->
            environmentMonitor.setEnvironmentValues(22, 45));

        Button hotButton = new Button("🔥 Hot Weather", e ->
            environmentMonitor.setEnvironmentValues(35, 30));

        Button humidButton = new Button("🌧️ Humid Weather", e ->
            environmentMonitor.setEnvironmentValues(28, 80));

        HorizontalLayout controls = new HorizontalLayout(
            coldButton, normalButton, hotButton, humidButton);
        controls.setSpacing(true);

        add(controls);
    }
}
