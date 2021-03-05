package com.example.application.views.recherche;

import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.application.views.recherche.NonControllerUtils.startSearching;

@Route(value = "recherche", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Recherche")
@Controller // Does this work?
public class RechercheController extends Div {
    private final SafeAccessService safeAccessService;
    private final CurrentUserInfoService currentUserInfoService;
    private final RechercheService rechercheService;
    private final AirportService airportService;
    private final HotelService hotelService;
    private final HandlerService handlerService;
    private final CateringCompaniesService cateringCompaniesService;
    private final LocalContactService localContactService;
    private final NationalContactService nationalContactService;
    private final CarRentalAgencyService carRentalAgencyService;

    private final ApplicationContext applicationContext;

    private SproutsManagedUiModel managedUiModel;

    // BorderPane -> nur center wird verwendet -> stackpane?
    private Div detailArea;
    // table
    private Grid masterTable;
    // textfield
    private TextField searchTextField;

    private List<RechercheResult> things;
    // TODO: BetterPropertySheetBuilder is also a controller
    private final List<BetterPropertySheetBuilder<?>> viewBuilders = new ArrayList<>();

    @Autowired
    public RechercheController(
            SafeAccessService safeAccessService,
            CurrentUserInfoService currentUserInfoService,
            RechercheService rechercheService,
            AirportService airportService,
            HotelService hotelService,
            HandlerService handlerService,
            CateringCompaniesService cateringCompaniesService,
            LocalContactService localContactService,
            NationalContactService nationalContactService,
            CarRentalAgencyService carRentalAgencyService,
            ApplicationContext applicationContext
    ) {
        this.safeAccessService = safeAccessService;
        this.currentUserInfoService = currentUserInfoService;
        this.rechercheService = rechercheService;
        this.airportService = airportService;
        this.hotelService = hotelService;
        this.handlerService = handlerService;
        this.cateringCompaniesService = cateringCompaniesService;
        this.localContactService = localContactService;
        this.nationalContactService = nationalContactService;
        this.carRentalAgencyService = carRentalAgencyService;
        this.applicationContext = applicationContext;

        buildUi();
    }

    public Component buildUi() {
        SplitLayout model = new SplitLayout(buildMasterArea(), buildDetailArea());
        model.setSplitterPosition(75);
        model.setSizeFull();

        masterTable.get().selectedRowProperty().onChanged(valueChangeEvent -> onRowSelection(valueChangeEvent.getNewValue()));
        return model;
    }

    private void onRowSelection(TableRowModel newValue) {
        if (newValue != null && things != null) {
            String reference = newValue.getReference();
            getModel().setIsWorking(true);
            new Thread(() -> runAndForgetInUi(() -> {
                try {
                    things.stream().filter(rechercheResult -> rechercheResult.getId().equals(reference)).findAny().ifPresent(rechercheResult -> {
                        detailArea.removeAll();
                        detailArea.add(new Scroller(getDetailViewForResult(rechercheResult))); // TODO: is this the equivalent to scrollpane?
                    });
                } finally {
                    getModel().setIsWorking(false);
                }
            })).start();
        } else if (newValue == null) {
            detailArea.removeAll();
        }
    }

    private Component buildMasterArea() {
        VerticalLayout borderPane = new VerticalLayout(); // TODO was once a borderpane. VBox still ok?
        borderPane.setClassName("recherchePane");
        HorizontalLayout toolBarModel = new HorizontalLayout();
        toolBarModel.add(buildSearchField());
        borderPane.add(toolBarModel);
        borderPane.add(buildMasterList());
        return borderPane;
    }

    private Component buildDetailArea() {
        detailArea = new Div(new Label(""));
        return detailArea;
    }

    private Component getDetailViewForResult(RechercheResult rechercheResult) {
        if (rechercheResult == null) {
            return new Label("");
        }
        switch (rechercheResult.getType()) {
            case Airport:
                return buildAirportDetailView(rechercheResult);
            case Handler:
                return buildHandlerDetailView(rechercheResult);
            case Hotel:
                return buildHotelDetailView(rechercheResult);
            case CateringCompany:
                CateringService cateringService = cateringCompaniesService.findById(rechercheResult.getId());
                return buildDetailView(BetterCateringServiceViewBuilder.class, cateringService, "Catering in " + safeAccessService.caption(cateringService.getWhichAirport()));
            case LocalContact:
                LocalContact localContact = localContactService.findById(rechercheResult.getId());
                return buildDetailView(BetterLocalContactViewBuilder.class, localContact, "Lokaler Kontakt in " + safeAccessService.caption(localContact.getWhichAirport()));
            case NationalContact:
                NationalContact nationalContact = nationalContactService.findById(rechercheResult.getId());
                return buildDetailView(BetterNationalContactViewBuilder.class, nationalContact, "Nationaler Kontakt in " + nationalContact.getWhichCountry().getCountryName());
            case RentalCar:
                CarRentalAgency carRentalAgency = carRentalAgencyService.findById(rechercheResult.getId());
                return buildDetailView(BetterCarRentalViewBuilder.class, carRentalAgency, "Mietwagen-Service in " + safeAccessService.caption(carRentalAgency.getWhichAirport()));
            default:
                return new Label("");
        }
    }

    private Component buildAirportDetailView(RechercheResult rechercheResult) {
        HorizontalLayout result = getDefaultFlowPane();
//        result.setHgap(15.0); //TODO
//        result.setVgap(15.0);

        Airport airport = airportService.findById(rechercheResult.getId());
        result.add(getComponent(BetterAirportViewBuilder.class, airport, "Flughafen " + safeAccessService.caption(airport)));

        handlerService
                .findAllHandlersAt(airport.getId())
                .stream()
                .map(basicData -> handlerService.findById(Handler.class, basicData.getId()))
                .forEach(handler -> result.add(getModelForHandler(handler)));

        airportService
                .findCarRentalAgenciesAt(CarRentalAgency.class, airport)
                .forEach(carRentalAgency -> result.add(getComponent(BetterCarRentalViewBuilder.class, carRentalAgency, "Mietwagen " + carRentalAgency.getCompanyName())));

        airportService
                .findCateringServicesAt(CateringService.class, airport)
                .forEach(cateringService -> result.add(getComponent(BetterCateringServiceViewBuilder.class, cateringService, "Catering " + cateringService.getNameOrOffice())));

        hotelService
                .findAllHotelsAt(airport.getId())
                .stream()
                .map(basicData -> hotelService.findById(Hotel.class, basicData.getId()))
                .forEach(hotel -> result.add(getModelForHotel(hotel)));

        airportService
                .findLocalContactsAt(LocalContact.class, airport)
                .forEach(localContact -> result.add(getComponent(BetterLocalContactViewBuilder.class, localContact, "Lokaler Kontakt " + localContact.getLocalContactName())));

        return result;
    }

    private Component buildHandlerDetailView(RechercheResult rechercheResult) {
        HorizontalLayout result = getDefaultFlowPane();
        Handler handler = handlerService.findById(Handler.class, rechercheResult.getId());
        result.add(getModelForHandler(handler));
        return result;
    }

    private Component buildHotelDetailView(RechercheResult rechercheResult) {
        HorizontalLayout result = getDefaultFlowPane();
        Hotel hotel = hotelService.findById(Hotel.class, rechercheResult.getId());
        result.add(getModelForHotel(hotel));
        return result;
    }

    private Component getModelForHandler(Handler handler) {
        if (handler instanceof Acukwik) {
            return getComponent(AcukwikHandlerViewBuilder.class, ((Acukwik) handler).getData(), "Handler " + safeAccessService.caption(handler));
        } else {
            return getComponent(BetterHandlerViewBuilder.class, handler, "Handler " + safeAccessService.caption(handler));
        }
    }

    private Component getModelForHotel(Hotel hotel) {
        if (hotel instanceof Acukwik) {
            return getComponent(AcukwikHotelViewBuilder.class, ((Acukwik) hotel).getData(), "Hotel " + safeAccessService.caption(hotel));
        } else {
            return getComponent(BetterHotelViewBuilder.class, hotel, "Hotel " + safeAccessService.caption(hotel));
        }
    }

    private <T extends BetterPropertySheetBuilder<E>, E> Component buildDetailView(Class<T> type, E entity, String caption) {
        HorizontalLayout result = getDefaultFlowPane();
        Component Component = getComponent(type, entity, caption);
        result.add(Component);
        return result;
    }

    private HorizontalLayout getDefaultFlowPane() {
        HorizontalLayout flowPane = new HorizontalLayout();
        // TODO
//        flowPane.setHgap(10.0);
//        flowPane.setVgap(10.0);
//        flowPane.setPadding(10);
        return flowPane;
    }

    private <T extends BetterPropertySheetBuilder<E>, E> Component getComponent(Class<T> type, E entity, String caption) {
        Component wrapThis = buildPropertySheetModel(type, entity);
        return wrapPane(wrapThis, caption); // FlowPaneItem not necessary, in vaadin -> ui().hbox(ui().hboxItem(myModel)) == new Hbox(myModel)
    }

    private <T extends BetterPropertySheetBuilder<E>, E> Component buildPropertySheetModel(Class<T> type, E entity) {
        T bean = applicationContext.getBean(type);
        bean.setDelegate(this);
        bean.setEntity(entity);
        viewBuilders.add(bean);
        return bean.buildUi();
    }

    private Component wrapPane(Component wrapThis, String caption) {
        // TODO ???
        wrapThis.setSkin(PropertySheetModel.Skin.NoGroups);

        VerticalLayout borderPane = new VerticalLayout();
        borderPane.setId("dropshadow" + borderPane.getId());
        borderPane.setClassName("flow-pane-style");
        Label label = new Label(caption);
        label.setSizeFull();
        Div borderPaneContent = new Div();
        borderPaneContent.add(label);
        borderPaneContent.setClassName("border-pane-content");
        label.setClassName("detail-view");
        HorizontalLayout hBoxFooter = new HorizontalLayout();
        Button changeDataButton = new Button("Eintrag ändern");
        hBoxFooter.add(changeDataButton);
//        hBoxFooter.setSpacing(30);
//        hBoxFooter.setPadding(20);
        borderPane.add(borderPaneContent);
        borderPane.add(wrapThis);
        return borderPane;
    }

    private Component buildMasterList() {
        masterTable = new Grid();
        Component tableColumnInfo = TableColumnInfo.builder().column(ui().tableStringColumn("Zusammenfassung", 250.0)).build();
        masterTable.get().getColumns().addAll(tableColumnInfo.getColumns());
        masterTable.get().getSortOrder().addAll(tableColumnInfo.getSortOrder());
        return masterTable.get();
    }

    private Component buildSearchField() {
        searchTextField = new TextField("", "Suchen nach...");
        searchTextField.addValueChangeListener(event -> {
            // was once the remoting action
            String newValue = event.getValue();
            if (startSearching(newValue)) {
                populateMasterTable(newValue);
            } else {
                masterTable.get().getRows().clear();
            }
        });
        searchTextField.setClassName("searchTextField-style");
        return searchTextField;
    }

    private void populateMasterTable(String searchText) {
        User user = currentUserInfoService.getUser();
        getModel().setIsWorking(true);
        new Thread(() -> {
            UserHolder.setUser(user);
            try {
                things = rechercheService.findMatchingThings(searchText);
                runAndForgetInUi(() -> {
                    masterTable.get().getRows().setAll(things.stream().map(this::buildMasterTableRow).collect(Collectors.toList()));

                    Component tableModel = masterTable.get();
                    if (tableModel.getRows().size() == 1) {
                        tableModel.setSelectedRow(tableModel.getRows().get(0));
                    }

                    getModel().setIsWorking(false);
                });
            } finally {
                UserHolder.clearUser();
            }
        }).start();
    }

    private Component buildMasterTableRow(RechercheResult rechercheResult) {
        Component tableRow = ui().tableRow();
        tableRow.setReference(rechercheResult.getId());
        tableRow.getCells().add(ui().tableStringCell(rechercheResult.getType() + ": " + rechercheResult.getCaption()));
        return tableRow;
    }
}
