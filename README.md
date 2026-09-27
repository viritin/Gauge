# Gauge

A Gauge component for Vaadin 24+. See [Directory page](https://vaadin.com/directory/component/gauge) for more details.

Built on top of https://github.com/antoniolago/react-gauge-component

Also contains more specific components like HumidityGauge, TemperatureGauge and EnvironmentMonitor.

The `test` directory contains a small Spring Boot application with examples.

Trivial usage example:

```java
Gauge gauge = new Gauge();
gauge.setValue(75.0);
add(gauge);
```



The reading can carry a unit and a fixed number of decimals; `TemperatureGauge`
and `HumidityGauge` set theirs (°C with one decimal, % with none):

```java
Gauge pressure = new Gauge();
pressure.setUnit(" hPa");
pressure.setDecimals(0);
```

## Size

The gauge is a block as wide as its container, and it reserves its height
before the dial is drawn (5:3 for a semicircle), so a page does not jump when it
appears. Limit and place it like any component:

```java
gauge.setMaxWidth("20rem");
gauge.getStyle().setMargin("0 auto");
```

## Theming

The gauge takes its colours from the page. The dial's reading and range labels
follow `currentColor`, so they are readable on any surface in any colour scheme
without configuration; the component paints no background of its own. The
arc colours are yours through `setArc`, and `TemperatureGauge`/`HumidityGauge`
carry sensible defaults you can return to with `resetToDefaults()`.

For anything further, the rendered SVG marks its parts with stable classes —
`value-text`, `tick-value`, `subArc`, `tick-line` — which page CSS can target
(the component renders in the light DOM). Some of the inner styles come from
react-gauge-component as inline styles and need `!important` to override.
