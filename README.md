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
