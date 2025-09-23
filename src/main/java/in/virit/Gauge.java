package in.virit;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.react.ReactAdapterComponent;
import in.virit.color.Color;

/**
 * Vaadin wrapper for the react-gauge-component React component.
 * Provides a comprehensive API for creating customizable gauge charts.
 */
@NpmPackage(value = "react-gauge-component", version = "1.2.64")
@JsModule("./viritin/react-gauge.tsx")
@Tag("react-gauge")
public class Gauge extends ReactAdapterComponent {

    public Gauge() {
        initializeDefaults();
    }

    private void initializeDefaults() {
        setValue(0.0);
        setMinValue(0.0);
        setMaxValue(100.0);
        getStyle().setBackground("rgb(40, 44, 52)");
        getStyle().setBorderRadius("2em");
    }

    public Gauge(double value) {
        initializeDefaults();
        setValue(value);
    }

    public void setValue(double value) {
        setState("value", value);
    }

    public void setMinValue(double minValue) {
        setState("minValue", minValue);
    }

    public void setMaxValue(double maxValue) {
        setState("maxValue", maxValue);
    }

    public void setType(GaugeType type) {
        setState("type", type.getValue());
    }

    public void setArcWidth(int width) {
        GaugeArc currentArc = getCurrentArc();
        currentArc.setWidth(width);
        setState("arc", currentArc);
    }

    public void setArc(GaugeArc arc) {
        setState("arc", arc != null ? arc : new GaugeArc());
    }

    public void setPointer(GaugePointer pointer) {
        setState("pointer", pointer != null ? pointer : new GaugePointer());
    }

    public void setLabels(GaugeLabels labels) {
        setState("labels", labels != null ? labels : new GaugeLabels());
    }

    private GaugeArc getCurrentArc() {
        return new GaugeArc(); // In a real implementation, you might want to get the current arc
    }

    public static class GaugeArc {
        private Integer width;
        private Double padding;
        private String[] colorArray;
        private GaugeSubArc[] subArcs;

        public GaugeArc() {
            // Initialize with defaults to avoid null serialization issues
        }

        public GaugeArc setWidth(int width) {
            this.width = width;
            return this;
        }

        public GaugeArc setPadding(double padding) {
            this.padding = padding;
            return this;
        }

        public GaugeArc setColorArray(Color... colors) {
            this.colorArray = new String[colors.length];
            for (int i = 0; i < colors.length; i++) {
                this.colorArray[i] = colors[i] != null ? colors[i].toString() : "#000000";
            }
            return this;
        }

        public GaugeArc setSubArcs(GaugeSubArc... subArcs) {
            this.subArcs = subArcs;
            return this;
        }

        public Integer getWidth() { return width; }
        public Double getPadding() { return padding; }
        public String[] getColorArray() { return colorArray; }
        public GaugeSubArc[] getSubArcs() { return subArcs; }
    }

    public static class GaugeSubArc {
        private Double limit;
        private String color;
        private Boolean showTick;
        private String tooltip;

        public GaugeSubArc(double limit, Color color) {
            this.limit = limit;
            this.color = color != null ? color.toString() : "#000000";
        }

        public GaugeSubArc setShowTick(boolean showTick) {
            this.showTick = showTick;
            return this;
        }

        public GaugeSubArc setTooltip(String tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public Double getLimit() { return limit; }
        public String getColor() { return color; }
        public Boolean getShowTick() { return showTick; }
        public String getTooltip() { return tooltip; }
    }

    public static class GaugePointer {
        private String type;
        private String color;
        private Double length;
        private Integer width;
        private Boolean elastic;
        private Integer animationDelay;

        public GaugePointer() {
            // Initialize with defaults to avoid null serialization issues
        }

        public GaugePointer setType(PointerType type) {
            this.type = type.getValue();
            return this;
        }

        public GaugePointer setColor(Color color) {
            this.color = color != null ? color.toString() : null;
            return this;
        }

        public GaugePointer setLength(double length) {
            this.length = length;
            return this;
        }

        public GaugePointer setWidth(int width) {
            this.width = width;
            return this;
        }

        public GaugePointer setElastic(boolean elastic) {
            this.elastic = elastic;
            return this;
        }

        public GaugePointer setAnimationDelay(int delay) {
            this.animationDelay = delay;
            return this;
        }

        public String getType() { return type; }
        public String getColor() { return color; }
        public Double getLength() { return length; }
        public Integer getWidth() { return width; }
        public Boolean getElastic() { return elastic; }
        public Integer getAnimationDelay() { return animationDelay; }
    }

    public static class GaugeLabels {
        private GaugeValueLabel valueLabel;
        private GaugeTickLabels tickLabels;

        public GaugeLabels() {
            // Initialize with defaults to avoid null serialization issues
        }

        public GaugeLabels setValueLabel(GaugeValueLabel valueLabel) {
            this.valueLabel = valueLabel;
            return this;
        }

        public GaugeLabels setTickLabels(GaugeTickLabels tickLabels) {
            this.tickLabels = tickLabels;
            return this;
        }

        public GaugeValueLabel getValueLabel() { return valueLabel; }
        public GaugeTickLabels getTickLabels() { return tickLabels; }
    }

    public static class GaugeValueLabel {
        private Boolean hide;
        private String formatTextValue;
        private GaugeLabelStyle style;

        public GaugeValueLabel setHide(boolean hide) {
            this.hide = hide;
            return this;
        }

        public GaugeValueLabel setFormatTextValue(String format) {
            this.formatTextValue = format;
            return this;
        }

        public GaugeValueLabel setStyle(GaugeLabelStyle style) {
            this.style = style;
            return this;
        }

        public Boolean getHide() { return hide; }
        public String getFormatTextValue() { return formatTextValue; }
        public GaugeLabelStyle getStyle() { return style; }
    }

    public static class GaugeTickLabels {
        private String type;
        private Double[] ticks;
        private String formatTextValue;

        public GaugeTickLabels setType(TickLabelType type) {
            this.type = type.getValue();
            return this;
        }

        public GaugeTickLabels setTicks(Double... ticks) {
            this.ticks = ticks;
            return this;
        }

        public GaugeTickLabels setFormatTextValue(String format) {
            this.formatTextValue = format;
            return this;
        }

        public String getType() { return type; }
        public Double[] getTicks() { return ticks; }
        public String getFormatTextValue() { return formatTextValue; }
    }

    public static class GaugeLabelStyle {
        private String fontSize;
        private String fill;
        private String textShadow;

        public GaugeLabelStyle setFontSize(String fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public GaugeLabelStyle setFill(String fill) {
            this.fill = fill;
            return this;
        }

        public GaugeLabelStyle setTextShadow(String textShadow) {
            this.textShadow = textShadow;
            return this;
        }

        public String getFontSize() { return fontSize; }
        public String getFill() { return fill; }
        public String getTextShadow() { return textShadow; }
    }

    public enum GaugeType {
        SEMICIRCLE("semicircle"),
        RADIAL("radial");

        private final String value;

        GaugeType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum PointerType {
        ARROW("arrow"),
        BLOB("blob");

        private final String value;

        PointerType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum TickLabelType {
        INNER("inner"),
        OUTER("outer");

        private final String value;

        TickLabelType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


}
