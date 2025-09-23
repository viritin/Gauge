package in.virit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import in.virit.color.Color;
import in.virit.color.HexColor;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.util.VStyle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Route("weather-dashboard")
public class WeatherDashboardExample extends VVerticalLayout {

    private EnvironmentMonitor environmentMonitor;
    private Span lastUpdated;
    private Span weatherStatus;

    // Weather conditions for simulation
    private static final WeatherCondition[] CONDITIONS = {
        new WeatherCondition("Sunny", 28, 45, "☀️"),
        new WeatherCondition("Cloudy", 22, 55, "☁️"),
        new WeatherCondition("Rainy", 18, 75, "🌧️"),
        new WeatherCondition("Hot Summer Day", 35, 40, "🌡️"),
        new WeatherCondition("Cold Winter Day", -5, 60, "❄️"),
        new WeatherCondition("Humid Tropical", 30, 85, "🌴"),
        new WeatherCondition("Dry Desert", 40, 15, "🏜️")
    };

    public WeatherDashboardExample() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        // Header
        add(new H1("🌤️ Weather Dashboard"));

        // Create environment monitor
        environmentMonitor = new EnvironmentMonitor();
        add(environmentMonitor);

        // Status and controls
        createStatusAndControls();

        // Initialize with sunny conditions
        updateWeather(CONDITIONS[0]);
    }


    private void createStatusAndControls() {
        // Weather status
        weatherStatus = new Span() {{
            getStyle().setFontSize("24px");
            getStyle().setFontWeight("bold");
            getStyle().setMargin("20px 0");
        }};

        // Last updated timestamp
        lastUpdated = new Span() {{
            getStyle().setFontSize("14px");
            getStyle().setColor("#666");
        }};

        // Weather condition buttons
        HorizontalLayout conditionsLayout = new HorizontalLayout();
        for (WeatherCondition condition : CONDITIONS) {
            Button button = new Button(condition.icon + " " + condition.name,
                e -> updateWeather(condition));
            button.getStyle().set("margin", "5px");
            conditionsLayout.add(button);
        }

        // Auto-refresh button
        Button autoRefreshButton = new Button("🔄 Auto Refresh", e -> startAutoRefresh());

        add(weatherStatus, lastUpdated,
            new H3("Simulate Weather Conditions:"),
            conditionsLayout,
            autoRefreshButton);
    }

    private void updateWeather(WeatherCondition condition) {
        environmentMonitor.setEnvironmentValues(condition.temperature, condition.humidity);

        weatherStatus.setText(condition.icon + " " + condition.name +
            " - " + condition.temperature + "°C, " + condition.humidity + "%");

        lastUpdated.setText("Last updated: " +
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, HH:mm:ss")));

        // Update weather status color based on conditions
        updateWeatherStatusColor(condition);
    }

    private void updateWeatherStatusColor(WeatherCondition condition) {
        Color color = new HexColor("#000");

        if (condition.temperature > 30 && condition.humidity < 30) {
            color = new HexColor("#EA4228"); // Red for hot and dry
        } else if (condition.temperature < 0) {
            color = new HexColor("#5BE12C"); // Green for cold
        } else if (condition.humidity > 70) {
            color = new HexColor("#0066CC"); // Blue for humid
        } else if (condition.temperature > 25) {
            color = new HexColor("#FF8C00"); // Orange for warm
        }

        VStyle.wrap(weatherStatus.getStyle()).setColor(color);
    }

    private void startAutoRefresh() {
        // Simulate changing weather conditions every 3 seconds
        getUI().ifPresent(ui -> {
            Thread thread = new Thread(() -> {
                try {
                    for (int i = 0; i < CONDITIONS.length; i++) {
                        Thread.sleep(3000);
                        final int index = i;
                        ui.access(() -> updateWeather(CONDITIONS[index]));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            thread.setDaemon(true);
            thread.start();
        });
    }

    private static class WeatherCondition {
        final String name;
        final double temperature;
        final double humidity;
        final String icon;

        WeatherCondition(String name, double temperature, double humidity, String icon) {
            this.name = name;
            this.temperature = temperature;
            this.humidity = humidity;
            this.icon = icon;
        }
    }
}