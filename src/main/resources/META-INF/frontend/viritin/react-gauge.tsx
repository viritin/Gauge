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
    const [unit] = hooks.useState('unit', '');
    const [decimals] = hooks.useState('decimals', -1);

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

    // The dial's text follows the page. react-gauge-component's own defaults are
    // fixed white with a black text shadow — a treatment for text drawn over an
    // arbitrary photo, which this is not: the reading sits in the hole of the
    // dial, on whatever surface the page put it on. currentColor is how a
    // component asks its page for the right colour, and it is only a default —
    // labels given from the server win.
    const themedText = { fill: 'currentColor', textShadow: 'none' };
    const applyTextDefaults = (l: any) => {
      l.valueLabel = { style: { ...themedText }, ...l.valueLabel };
      if (l.valueLabel.style === undefined) l.valueLabel.style = { ...themedText };
      l.tickLabels = l.tickLabels || {};
      l.tickLabels.defaultTickValueConfig = {
        style: { fill: 'currentColor', opacity: 0.7 },
        ...l.tickLabels.defaultTickValueConfig
      };
      return l;
    };

    // The reading: rounded to the decimals asked for, followed by the unit, and a
    // dash while empty. An empty gauge keeps its unit next to the dash, so the
    // dial still says what it would measure.
    const labelsToUse = applyTextDefaults(
        (labels && typeof labels === 'object') ? deepClean(labels) : {});
    if (decimals >= 0) {
      labelsToUse.valueLabel.maxDecimalDigits = decimals;
    }
    if (empty || unit || decimals >= 0) {
      const format = (v: number) => decimals >= 0 ? v.toFixed(decimals) : String(v);
      labelsToUse.valueLabel.formatTextValue = (v: number) => (empty ? '–' : format(v)) + unit;
    }
    props.labels = labelsToUse;

    return <GaugeComponent {...props} />;
  }
}

customElements.define('react-gauge', ReactGaugeElement);
