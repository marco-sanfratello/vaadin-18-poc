package com.example.application.views.recherche;

import com.example.application.views.main.MainView;
import com.example.application.views.recherche.model.RechercheResult;
import com.example.application.views.recherche.service.RechercheResultService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.application.data.ProviderCreator.createSimpleListProvider;

// lazy loading: https://www.flowingcode.com/en/lazy-filtered-and-sorted-vaadin-grid-using-external-data-source/
//@CssImport("./views/cardlist/card-list-view.css")
@Route(value = "grid-example", layout = MainView.class)
@PageTitle("Grid Example")
public class GridController extends VerticalLayout {
    private static final Logger LOG = LoggerFactory.getLogger(GridController.class);

    public GridController(@Autowired RechercheResultService rechercheService) {
        // init components
        final Grid<RechercheResult> grid = new Grid<>(RechercheResult.class, false);
//        final ConfigurableFilterDataProvider<RechercheResult, Void, String> dataProvider = createDataProvider(rechercheService);
        final ListDataProvider<RechercheResult> dataProvider = createSimpleListProvider(rechercheService);
        final TextField filterText = new TextField();
        final Label selectedLabel = new Label();

        // populate grid
        grid.addColumn(RechercheResult::getId)
                .setHeader("Id")
                .setComparator(r -> Integer.parseInt(r.getId()))
                .setResizable(true)
                .setAutoWidth(true)
        ;

        grid.addColumn(RechercheResult::getType)
                .setHeader("Type")
                .setResizable(true)
                .setAutoWidth(true)
                .setSortable(true)
        ;

        grid.addColumn(RechercheResult::getCaption)
                .setHeader("Caption")
                .setResizable(true)
                .setAutoWidth(true)
                .setSortable(true)
        ;

        final IsAirportRenderer isAirportRenderer = new IsAirportRenderer(result -> grid.getDataProvider().refreshItem(result));
        grid.addColumn(isAirportRenderer)
                .setComparator(r -> r.getType().equals(RechercheResult.Type.Airport))
                .setHeader("Is Airport")
                .setResizable(true)
                .setAutoWidth(true)
        ;

        // grid data and eventhandler
//        grid.setItems(dataProvider);
//        grid.setItems(rechercheService.getAll());
        grid.setItems(dataProvider);

        grid.asSingleSelect().addValueChangeListener(event -> selectedLabel.setText(event.getHasValue().getOptionalValue().map(RechercheResult::toString).orElse(null)));

        // populate filter text
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(event -> {
//            dataProvider.setFilter(filterText.getValue());
            dataProvider.setFilter(result -> StringUtils.containsIgnoreCase(result.toString(), event.getValue()));
            dataProvider.refreshAll();
        });

        // build ui
        grid.setSizeFull();
        this.setSizeFull();
        this.add(new HorizontalLayout(filterText, selectedLabel), grid);
    }
}
