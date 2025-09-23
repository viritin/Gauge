package in.virit;

import com.vaadin.flow.router.Route;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

@Route
public class GaugeUsageUI extends VVerticalLayout {

    public GaugeUsageUI() {
        Gauge gauge = new Gauge();
        gauge.setValue(50);
        add(gauge);
    }

}
