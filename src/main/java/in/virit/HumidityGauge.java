package in.virit;

import in.virit.color.NamedColor;

/**
 * A specialized gauge for displaying humidity values with appropriate
 * color coding and range settings for humidity measurements.
 */
public class HumidityGauge extends Gauge {

    public HumidityGauge() {
        this(50.0);
    }

    public HumidityGauge(double humidity) {
        super();
        setupHumidityDefaults();
        setHumidity(humidity);
    }

    private void setupHumidityDefaults() {
        setState("gaugeType", "humidity");
        setArc(new GaugeArc()
            .setSubArcs(
                new GaugeSubArc(30, NamedColor.RED).setTooltip("Dry"),
                new GaugeSubArc(50, NamedColor.YELLOW).setTooltip("Comfortable"),
                new GaugeSubArc(70, NamedColor.GREEN).setTooltip("Optimal"),
                new GaugeSubArc(100, NamedColor.LIGHTBLUE).setTooltip("Humid")
            )

        );
        setPointer(new GaugePointer()
            .setType(PointerType.BLOB)
            .setElastic(true)
            .setColor(NamedColor.BLUE)
        );
        // Labels with formatTextValue will be handled by React component
    }

    public void setHumidity(double humidity) {
        setValue(humidity);
    }

    public HumidityLevel getHumidityLevel(double humidity) {
        if (humidity < 20) return HumidityLevel.VERY_DRY;
        if (humidity < 30) return HumidityLevel.DRY;
        if (humidity < 40) return HumidityLevel.LOW;
        if (humidity < 50) return HumidityLevel.COMFORTABLE;
        if (humidity < 65) return HumidityLevel.OPTIMAL;
        if (humidity < 75) return HumidityLevel.GOOD;
        if (humidity < 85) return HumidityLevel.HIGH;
        return HumidityLevel.VERY_HUMID;
    }

    public String getHumidityRecommendation(double humidity) {
        HumidityLevel level = getHumidityLevel(humidity);
        return switch (level) {
            case VERY_DRY, DRY -> "Consider using a humidifier to add moisture to the air";
            case LOW -> "Indoor plants can help increase humidity naturally";
            case COMFORTABLE, OPTIMAL, GOOD -> "Perfect humidity range for comfort and health";
            case HIGH -> "Consider improving ventilation to reduce moisture";
            case VERY_HUMID -> "Use a dehumidifier to prevent mold growth and improve air quality";
        };
    }

    public enum HumidityLevel {
        VERY_DRY("Very Dry", "🏜️"),
        DRY("Dry", "😤"),
        LOW("Low Humidity", "😐"),
        COMFORTABLE("Comfortable", "😊"),
        OPTIMAL("Optimal", "🌟"),
        GOOD("Good", "👍"),
        HIGH("High Humidity", "😓"),
        VERY_HUMID("Very Humid", "💧");

        private final String description;
        private final String emoji;

        HumidityLevel(String description, String emoji) {
            this.description = description;
            this.emoji = emoji;
        }

        public String getDescription() { return description; }
        public String getEmoji() { return emoji; }
        public String getDisplayText() { return description + " " + emoji; }
    }
}