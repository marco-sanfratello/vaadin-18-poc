package com.example.application.views.recherche;

import com.example.application.views.recherche.model.RechercheResult;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.util.function.Consumer;

/**
 * A renderer for a grid cell. Renders a checkbox
 */
public class IsAirportRenderer extends ComponentRenderer<Checkbox, RechercheResult> {
    public IsAirportRenderer(Consumer<RechercheResult> onToggleFunction) {
        super(result -> {
            Checkbox checkbox = new Checkbox(result.getType() == RechercheResult.Type.Airport);
            checkbox.addValueChangeListener(isSelected -> {
                if (isSelected.getValue()) {
                    result.setType(RechercheResult.Type.Airport);
                    result.setCaption(RechercheResult.Type.Airport.getClass().getName());
                } else {
                    result.setType(null);
                    result.setCaption(null);
                }
                onToggleFunction.accept(result);
            });
            return checkbox;
        });
    }
}
