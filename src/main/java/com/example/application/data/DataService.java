package com.example.application.data;

import com.vaadin.flow.data.provider.Query;

import java.util.List;
import java.util.stream.Stream;

public interface DataService<E, F> {
    Stream<E> fetch(Query<E, F> query);

    int getCount(Query<E, F> query);

    List<E> getAll();
}
