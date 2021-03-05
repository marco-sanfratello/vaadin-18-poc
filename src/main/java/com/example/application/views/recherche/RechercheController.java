package com.example.application.views.recherche;

import aero.sprouts.fly.controller.BetterPropertySheetBuilder;
import aero.sprouts.fly.controller.masterdetail.TableColumnInfo;
import aero.sprouts.fly.controller.register.airport.BetterAirportViewBuilder;
import aero.sprouts.fly.controller.register.carrentalagency.BetterCarRentalViewBuilder;
import aero.sprouts.fly.controller.register.cateringservice.BetterCateringServiceViewBuilder;
import aero.sprouts.fly.controller.register.handler.AcukwikHandlerViewBuilder;
import aero.sprouts.fly.controller.register.handler.BetterHandlerViewBuilder;
import aero.sprouts.fly.controller.register.hotel.AcukwikHotelViewBuilder;
import aero.sprouts.fly.controller.register.hotel.BetterHotelViewBuilder;
import aero.sprouts.fly.controller.register.localcontact.BetterLocalContactViewBuilder;
import aero.sprouts.fly.controller.register.nationalcontact.BetterNationalContactViewBuilder;
import aero.sprouts.fly.data.domain.*;
import aero.sprouts.fly.feature.airports.AirportService;
import aero.sprouts.fly.feature.cateringCompanies.CateringCompaniesService;
import aero.sprouts.fly.feature.handlers.HandlerService;
import aero.sprouts.fly.feature.hotels.HotelService;
import aero.sprouts.fly.feature.localContacts.LocalContactService;
import aero.sprouts.fly.feature.nationalContacts.NationalContactService;
import aero.sprouts.fly.feature.rentalCars.CarRentalAgencyService;
import aero.sprouts.fly.mixed.SproutsAbstractManagedUiController;
import aero.sprouts.fly.office.controller.Controller;
import aero.sprouts.fly.service.CurrentUserInfoService;
import aero.sprouts.fly.service.SafeAccessService;
import aero.sprouts.fly.service.UserHolder;
import dev.rico.internal.projector.ui.*;
import dev.rico.internal.projector.ui.box.HBoxModel;
import dev.rico.internal.projector.ui.flowpane.FlowPaneModel;
import dev.rico.internal.projector.ui.splitpane.SplitPaneModel;
import dev.rico.internal.projector.ui.table.TableModel;
import dev.rico.internal.projector.ui.table.TableRowModel;
import dev.rico.internal.server.projector.Retained;
import dev.rico.remoting.BeanManager;
import dev.rico.server.remoting.RemotingAction;
import dev.rico.server.remoting.RemotingContext;
import dev.rico.server.remoting.RemotingController;
import dev.rico.server.remoting.RemotingModel;
import dev.rico.server.security.User;
import javafx.geometry.Orientation;
import org.springframework.context.ApplicationContext;
import to.remove.SproutsManagedUiModel;
import to.remove.ui.propertysheet.PropertySheetModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RemotingController(Controller.RECHERCHE)
public class RechercheController extends SproutsAbstractManagedUiController {

    private final boolean sproutsPermission;

    @RemotingModel
    private SproutsManagedUiModel managedUiModel;
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
    private Retained<BorderPaneModel> detailArea;
    private Retained<TableModel> masterTable;
    private Retained<TextFieldModel> searchTextField;
    private List<RechercheResult> things;

    private final List<BetterPropertySheetBuilder<?>> viewBuilders = new ArrayList<>();

    public RechercheController(BeanManager beanManager, RemotingContext session, SafeAccessService safeAccessService, CurrentUserInfoService currentUserInfoService, RechercheService rechercheService, AirportService airportService, HotelService hotelService, HandlerService handlerService, CateringCompaniesService cateringCompaniesService, LocalContactService localContactService, NationalContactService nationalContactService, CarRentalAgencyService carRentalAgencyService, ApplicationContext applicationContext) {
        super(beanManager, session);
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
        sproutsPermission = this.currentUserInfoService.hasSproutsPermission();
    }

    @Override
    public ItemModel buildUi() {
        SplitPaneModel model = ui().splitPane(Orientation.HORIZONTAL,
                ui().splitPaneItem(buildMasterArea(), 0.25),
                ui().splitPaneItem(buildDetailArea(), 0.75));
        masterTable.get().selectedRowProperty().onChanged(valueChangeEvent -> onRowSelection(valueChangeEvent.getNewValue()));
        return model;
    }

    private void onRowSelection(TableRowModel newValue) {
        if (newValue != null && things != null) {
            String reference = newValue.getReference();
            getModel().setIsWorking(true);
            new Thread(() -> runAndForgetInUi(() -> {
                try {
                    things.stream().filter(rechercheResult -> rechercheResult.getId().equals(reference)).findAny().ifPresent(rechercheResult -> detailArea.get().setCenter(buildDetailSheet(rechercheResult)));
                } finally {
                    getModel().setIsWorking(false);
                }
            })).start();
        } else if (newValue == null) {
            detailArea.get().setCenter(null);
        }
    }

    private ItemModel buildMasterArea() {
        BorderPaneModel borderPane = ui().borderPane();
        borderPane.getStyleClass().add("recherchePane");
        ToolBarModel toolBarModel = ui().toolBar();
        toolBarModel.getItems().add(buildSearchField());
        borderPane.setTop(toolBarModel);
        borderPane.setCenter(buildMasterList());
        return borderPane;
    }

    private ItemModel buildDetailArea() {
        detailArea = retain(ui().borderPane());
        detailArea.get().setCenter(buildDetailSheet(null));
        return detailArea.get();
    }

    private ItemModel buildDetailSheet(RechercheResult rechercheResult) {
        if (rechercheResult == null) return ui().label("");
        ItemModel result;
        if (rechercheResult.getType() == RechercheResult.Type.Airport) {
            result = buildAirportDetailView(rechercheResult);
        } else if (rechercheResult.getType() == RechercheResult.Type.Handler) {
            result = buildHandlerDetailView(rechercheResult);
        } else if (rechercheResult.getType() == RechercheResult.Type.Hotel) {
            result = buildHotelDetailView(rechercheResult);
        } else if (rechercheResult.getType() == RechercheResult.Type.CateringCompany) {
            result = buildCateringDetailView(rechercheResult);
        } else if (rechercheResult.getType() == RechercheResult.Type.LocalContact) {
            result = buildLocalContactDetailView(rechercheResult);
        } else if (rechercheResult.getType() == RechercheResult.Type.NationalContact) {
            result = buildNationalContactDetailView(rechercheResult);
        } else if (rechercheResult.getType() == RechercheResult.Type.RentalCar) {
            result = buildRentalCarDetailView(rechercheResult);
        } else {
            result = ui().label("");

        }

        return ui().scrollPane(result);
    }

    private ItemModel buildRentalCarDetailView(RechercheResult rechercheResult) {
        FlowPaneModel result = ui().flowPane(Orientation.HORIZONTAL);
        result.setHgap(10.0);
        result.setVgap(10.0);
        result.setPadding(10);
        CarRentalAgency carRentalAgency = carRentalAgencyService.findById(rechercheResult.getId());
        result.add(ui().flowPaneItem(wrapPane(newBuilder(BetterCarRentalViewBuilder.class, carRentalAgency).buildUi(), "Mietwagen-Service in " + safeAccessService.caption(carRentalAgency.getWhichAirport()))));
        return result;
    }

    private <T extends BetterPropertySheetBuilder<E>, E> T newBuilder(Class<T> type, E entity) {
        T bean = applicationContext.getBean(type);
        bean.setDelegate(this);
        bean.setEntity(entity);
        viewBuilders.add(bean);
        return bean;
    }

    private ItemModel buildNationalContactDetailView(RechercheResult rechercheResult) {
        FlowPaneModel result = ui().flowPane(Orientation.HORIZONTAL);
        result.setHgap(10.0);
        result.setVgap(10.0);
        result.setPadding(10);
        NationalContact nationalContact = nationalContactService.findById(rechercheResult.getId());
        result.add(ui().flowPaneItem(wrapPane(newBuilder(BetterNationalContactViewBuilder.class, nationalContact).buildUi(), "Nationaler Kontakt in " + nationalContact.getWhichCountry().getCountryName())));
        return result;
    }

    private ItemModel buildLocalContactDetailView(RechercheResult rechercheResult) {
        FlowPaneModel result = ui().flowPane(Orientation.HORIZONTAL);
        result.setHgap(10.0);
        result.setVgap(10.0);
        result.setPadding(10);
        LocalContact localContact = localContactService.findById(rechercheResult.getId());
        result.add(ui().flowPaneItem(wrapPane(newBuilder(BetterLocalContactViewBuilder.class, localContact).buildUi(), "Lokaler Kontakt in " + safeAccessService.caption(localContact.getWhichAirport()))));
        return result;
    }

    private ItemModel buildCateringDetailView(RechercheResult rechercheResult) {
        FlowPaneModel result = ui().flowPane(Orientation.HORIZONTAL);
        result.setHgap(10.0);
        result.setVgap(10.0);
        result.setPadding(10);
        CateringService cateringService = cateringCompaniesService.findById(rechercheResult.getId());
        result.add(ui().flowPaneItem(wrapPane(newBuilder(BetterCateringServiceViewBuilder.class, cateringService).buildUi(), "Catering in " + safeAccessService.caption(cateringService.getWhichAirport()))));
        return result;
    }

    private ItemModel buildHandlerDetailView(RechercheResult rechercheResult) {
        FlowPaneModel result = ui().flowPane(Orientation.HORIZONTAL);
        result.setHgap(10.0);
        result.setVgap(10.0);
        result.setPadding(10);
        Handler handler = handlerService.findById(Handler.class, rechercheResult.getId());
        if (handler instanceof Acukwik) {
            AcukwikHandlerViewBuilder betterHandlerViewBuilder = newBuilder(AcukwikHandlerViewBuilder.class, ((Acukwik) handler).getData());
            result.add(ui().flowPaneItem(wrapPane(betterHandlerViewBuilder.buildUi(), "Handler " + safeAccessService.caption(handler))));
        } else {
            BetterHandlerViewBuilder handlerViewBuilder = newBuilder(BetterHandlerViewBuilder.class, handler);
            result.add(ui().flowPaneItem(wrapPane(handlerViewBuilder.buildUi(), "Handler " + safeAccessService.caption(handler))));
        }
        return result;
    }

    private ItemModel buildHotelDetailView(RechercheResult rechercheResult) {
        FlowPaneModel result = ui().flowPane(Orientation.HORIZONTAL);
        result.setHgap(10.0);
        result.setVgap(10.0);
        result.setPadding(10);
        Hotel hotel = hotelService.findById(Hotel.class, rechercheResult.getId());
        if (hotel instanceof Acukwik) {
            AcukwikHotelViewBuilder viewBuilder = newBuilder(AcukwikHotelViewBuilder.class, ((Acukwik) hotel).getData());
            result.add(ui().flowPaneItem(wrapPane(viewBuilder.buildUi(), "Hotel " + safeAccessService.caption(hotel))));
        } else {
            BetterHotelViewBuilder viewBuilder = newBuilder(BetterHotelViewBuilder.class, hotel);
            result.add(ui().flowPaneItem(wrapPane(viewBuilder.buildUi(), "Hotel " + safeAccessService.caption(hotel))));
        }
        return result;
    }

    private ItemModel buildAirportDetailView(RechercheResult rechercheResult) {
        FlowPaneModel result = ui().flowPane(Orientation.HORIZONTAL);
        result.setHgap(15.0);
        result.setVgap(15.0);
        result.setPadding(10);

        Airport airport = airportService.findById(rechercheResult.getId());
        BetterAirportViewBuilder airportViewBuilder = newBuilder(BetterAirportViewBuilder.class, airport);
        result.add(ui().flowPaneItem(wrapPane(airportViewBuilder.buildUi(), "Flughafen " + safeAccessService.caption(airport))));

        handlerService.findAllHandlersAt(airport.getId())
                .stream().map(basicData -> handlerService.findById(Handler.class, basicData.getId()))
                .forEach(handler -> {
                    if (handler instanceof Acukwik) {
                        AcukwikHandlerViewBuilder betterHandlerViewBuilder = newBuilder(AcukwikHandlerViewBuilder.class, ((Acukwik) handler).getData());
                        result.add(ui().flowPaneItem(wrapPane(betterHandlerViewBuilder.buildUi(), "Handler " + safeAccessService.caption(handler))));
                    } else {
                        BetterHandlerViewBuilder handlerViewBuilder = newBuilder(BetterHandlerViewBuilder.class, handler);
                        result.add(ui().flowPaneItem(wrapPane(handlerViewBuilder.buildUi(), "Handler " + safeAccessService.caption(handler))));
                    }
                });
        airportService.findCarRentalAgenciesAt(CarRentalAgency.class, airport)
                .forEach(carRentalAgency -> {
                    BetterCarRentalViewBuilder viewBuilder = newBuilder(BetterCarRentalViewBuilder.class, carRentalAgency);
                    result.add(ui().flowPaneItem(wrapPane(viewBuilder.buildUi(), "Mietwagen " + carRentalAgency.getCompanyName())));
                });
        airportService.findCateringServicesAt(CateringService.class, airport)
                .forEach(cateringService -> {
                    BetterCateringServiceViewBuilder viewBuilder = newBuilder(BetterCateringServiceViewBuilder.class, cateringService);
                    result.add(ui().flowPaneItem(wrapPane(viewBuilder.buildUi(), "Catering " + cateringService.getNameOrOffice())));
                });
        hotelService.findAllHotelsAt(airport.getId())
                .stream().map(basicData -> hotelService.findById(Hotel.class, basicData.getId()))
                .forEach(hotel -> {
                    if (hotel instanceof Acukwik) {
                        AcukwikHotelViewBuilder viewBuilder = newBuilder(AcukwikHotelViewBuilder.class, ((Acukwik) hotel).getData());
                        result.add(ui().flowPaneItem(wrapPane(viewBuilder.buildUi(), "Hotel " + safeAccessService.caption(hotel))));
                    } else {
                        BetterHotelViewBuilder viewBuilder = newBuilder(BetterHotelViewBuilder.class, hotel);
                        result.add(ui().flowPaneItem(wrapPane(viewBuilder.buildUi(), "Hotel " + safeAccessService.caption(hotel))));
                    }
                });
        airportService.findLocalContactsAt(LocalContact.class, airport)
                .forEach(localContact -> {
                    BetterLocalContactViewBuilder viewBuilder = newBuilder(BetterLocalContactViewBuilder.class, localContact);
                    result.add(ui().flowPaneItem(wrapPane(viewBuilder.buildUi(), "Lokaler Kontakt " + localContact.getLocalContactName())));
                });
        return result;
    }

    private ItemModel wrapPane(PropertySheetModel wrapThis, String caption) {
        wrapThis.setSkin(PropertySheetModel.Skin.NoGroups);
        BorderPaneModel borderPane = ui().borderPane();
        borderPane.setId("dropshadow" + borderPane.getId());
        borderPane.getStyleClass().add("flow-pane-style");
        LabelModel label = ui().label(caption);
        label.setMaxWidth(Double.MAX_VALUE);
        BorderPaneModel borderPaneContent = ui().borderPane();
        borderPaneContent.setCenter(label);
//        ButtonModel cardMenuButton = ui().button(null);
//        cardMenuButton.setGraphic(ui().imageView("classpath:/image/menu_icon_white.png"));
//        cardMenuButton.getStyleClass().add("menuPaperButton");
//        cardMenuButton.setPrefHeight(26.0);
//        borderPaneContent.setRight(cardMenuButton);
        borderPaneContent.getStyleClass().add("border-pane-content");
        borderPane.setTop(borderPaneContent);
        label.getStyleClass().add("detail-view");
        HBoxModel hBoxFooter = ui().hBox();
        ButtonModel changeDataButton = ui().button("Eintrag ändern");
        hBoxFooter.add(ui().hBoxItem(changeDataButton));
        hBoxFooter.setSpacing(30);
        hBoxFooter.setPadding(20);
        borderPane.setCenter(wrapThis);
        return borderPane;
    }

    private ItemModel buildMasterList() {
        masterTable = retain(ui().table());
        TableColumnInfo tableColumnInfo = buildMasterTableColumns(sproutsPermission);
        masterTable.get().getColumns().addAll(tableColumnInfo.getColumns());
        masterTable.get().getSortOrder().addAll(tableColumnInfo.getSortOrder());
        return masterTable.get();
    }

    private ItemModel buildSearchField() {
        searchTextField = retain(ui().textField());
        searchTextField.get().setPromptText("Suchen nach...");
        searchTextField.get().setAction("searchTextChange");
        searchTextField.get().getStyleClass().add("searchTextField-style");
        return searchTextField.get();
    }

    @RemotingAction
    public void searchTextChange() {
        if (startSearching(searchTextField.get().getText()))
            populateMasterTable();
        else masterTable.get().getRows().clear();
    }

    private void populateMasterTable() {
        populateMasterTableWithSproutsPermission();
    }

    private void populateMasterTableWithSproutsPermission() {
        String searchText = searchTextField.get().getText();
        User user = currentUserInfoService.getUser();
        getModel().setIsWorking(true);
        new Thread(() -> {
            UserHolder.setUser(user);
            try {
                things = rechercheService.findMatchingThings(searchText);
                runAndForgetInUi(() -> {
                    masterTable.get().getRows().setAll(things.stream().map(this::buildMasterTableRowWithSproutsPermission).collect(Collectors.toList()));
                    selectRowIfOnlyOne();
                    getModel().setIsWorking(false);
                });
            } finally {
                UserHolder.clearUser();
            }
        }).start();
    }

    private void selectRowIfOnlyOne() {
        TableModel tableModel = masterTable.get();
        if (tableModel.getRows().size() == 1) {
            tableModel.setSelectedRow(tableModel.getRows().get(0));
        }
    }

    @Override
    public ManagedUiModel getModel() {
        return managedUiModel;
    }

    protected TableRowModel buildMasterTableRowWithSproutsPermission(RechercheResult rechercheResult) {
        return buildMasterTableRow(rechercheResult);
    }

    protected TableColumnInfo buildMasterTableColumns(boolean withSproutsPermissions) {
        return TableColumnInfo.builder().column(ui().tableStringColumn("Zusammenfassung", 250.0)).build();
    }

    private TableRowModel buildMasterTableRow(RechercheResult rechercheResult) {
        TableRowModel tableRow = ui().tableRow();
        tableRow.setReference(rechercheResult.getId());
        tableRow.getCells().add(ui().tableStringCell(rechercheResult.getType() + ": " + rechercheResult.getCaption()));
        return tableRow;
    }

    private boolean startSearching(String searchText) {
        return searchText != null && searchText.length() >= 2;
    }
}
