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

