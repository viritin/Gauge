import type { ReactElement } from 'react';
import { ReactAdapterElement, type RenderHooks } from 'Frontend/generated/flow/ReactAdapter';
import { GaugeComponent } from 'react-gauge-component';

class ReactGaugeElement extends ReactAdapterElement {
  protected override render(hooks: RenderHooks): ReactElement | null {
    const [value] = hooks.useState('value', 0);
    const [empty] = hooks.useState('empty', false);
    const [minValue] = hooks.useState('minValue', 0);
    const [maxValue] = hooks.useState('maxValue', 100);
    const [type] = hooks.useState('type', 'semicircle');
    const [arc] = hooks.useState('arc', {});
    const [pointer] = hooks.useState('pointer', {});
    const [labels] = hooks.useState('labels', {});
    const [gaugeType] = hooks.useState('gaugeType', 'default');
    const [temperatureUnit] = hooks.useState('temperatureUnit', 'celsius');

    // Deep clean function to remove null/undefined values
    const deepClean = (obj: any): any => {
      if (obj === null || obj === undefined || typeof obj !== 'object') {
        return obj;
      }

      if (Array.isArray(obj)) {
        return obj.map(deepClean).filter(item => item !== null && item !== undefined);
      }

      const cleaned: any = {};
      for (const [key, value] of Object.entries(obj)) {
        if (value !== null && value !== undefined) {
          const cleanedValue = deepClean(value);
          if (cleanedValue !== null && cleanedValue !== undefined) {
            cleaned[key] = cleanedValue;
          }
        }
      }
      return cleaned;
    };

    // Emptiness is a state of its own rather than a sentinel number, since any
    // number a gauge can show is a value somebody's sensor can produce. The dial
    // is drawn, nothing on it is reached, the pointer is hidden and the reading
    // is a dash; the component itself needs a number, so it sits at its minimum.
    const props: any = {
      value: empty ? (minValue || 0) : (value || 0),
      minValue: minValue || 0,
      maxValue: maxValue || 100
    };

    // Only add optional props if they exist and have content
    if (type && type !== 'semicircle') {
      props.type = type;
    }

    if (arc && typeof arc === 'object') {
      const cleanedArc = deepClean(arc);
      if (Object.keys(cleanedArc).length > 0) {
        props.arc = cleanedArc;
      }
    }

    const cleanedPointer = (pointer && typeof pointer === 'object') ? deepClean(pointer) : {};
    if (empty) {
      cleanedPointer.hide = true;
    }
    if (Object.keys(cleanedPointer).length > 0) {
      props.pointer = cleanedPointer;
    }

    // Handle labels and formatTextValue for specialized gauge types
    if (gaugeType === 'temperature' || gaugeType === 'humidity') {
      const labelsToUse = (labels && typeof labels === 'object') ? deepClean(labels) : {};

      // Ensure valueLabel exists
      if (!labelsToUse.valueLabel) {
        labelsToUse.valueLabel = {};
      }

      // Add appropriate formatTextValue function. An empty gauge keeps its
      // unit next to the dash, so the dial still says what it would measure.
      if (gaugeType === 'temperature') {
        const unit = temperatureUnit === 'fahrenheit' ? '°F' : '°C';
        labelsToUse.valueLabel.formatTextValue = (value: number) => (empty ? '–' : value) + unit;
      } else if (gaugeType === 'humidity') {
        labelsToUse.valueLabel.formatTextValue = (value: number) => (empty ? '–' : value) + '%';
      }

      props.labels = labelsToUse;
    } else {
      const cleanedLabels = (labels && typeof labels === 'object') ? deepClean(labels) : {};
      if (empty) {
        if (!cleanedLabels.valueLabel) {
          cleanedLabels.valueLabel = {};
        }
        cleanedLabels.valueLabel.formatTextValue = () => '–';
      }
      if (Object.keys(cleanedLabels).length > 0) {
        props.labels = cleanedLabels;
      }
    }

    return <GaugeComponent {...props} />;
  }
}

customElements.define('react-gauge', ReactGaugeElement);
