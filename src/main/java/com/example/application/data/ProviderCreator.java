package com.example.application.data;

import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;

public class ProviderCreator {
    /**
     * For getting data lazy from server
     */
    public static <E, F> ConfigurableFilterDataProvider<E, Void, F> createDataProvider(DataService<E, F> service) {
        return DataProvider.fromFilteringCallbacks(
                service::fetch,
                service::getCount
        ).withConfigurableFilter();
    }

    /**
     * For holding all the data in ui
     */
    public static <E> ListDataProvider<E> createSimpleListProvider(DataService<E, ?> service) {
        return new ListDataProvider<>(service.getAll());
    }
}
