package com.example.application.views.recherche.service;

import com.example.application.data.DataService;
import com.example.application.views.recherche.model.RechercheResult;
import com.vaadin.flow.data.provider.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class RechercheResultService implements DataService<RechercheResult, String> {
    private static final Logger LOG = LoggerFactory.getLogger(RechercheResultService.class);

    private final List<RechercheResult> data;

    public RechercheResultService() {
        data = generateData(10000);
    }

    @Override
    public Stream<RechercheResult> fetch(Query<RechercheResult, String> query) {
        LOG.info("fetch called");
        boolean present = query.getSortingComparator().isPresent();
        Stream<RechercheResult> dataStream = data.stream();
        if (present) {
            dataStream = dataStream.sorted((r1, r2) -> query.getSortingComparator().get().compare(r1, r2));
        }
        return dataStream
                .filter(s -> s.toString().toLowerCase().contains(query.getFilter().orElse("").toLowerCase()))
                .skip(query.getOffset())
                .limit(query.getLimit());
    }

    @Override
    public int getCount(Query<RechercheResult, String> query) {
        LOG.info("getCount called");
        return (int) data
                .stream()
                .filter(s -> s.toString().toLowerCase().contains(query.getFilter().orElse("").toLowerCase()))
                .count();
    }

    @Override
    public List<RechercheResult> getAll() {
        LOG.info("getAll called");
        return data;
    }

    private List<RechercheResult> generateData(int amount) {
        List<RechercheResult> items = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            RechercheResult item = new RechercheResult();
            item.setId(String.valueOf(i + 1));
            RechercheResult.Type randomType;
            switch ((int) (Math.random() * 7)) {
                case 0:
                    randomType = RechercheResult.Type.Airport;
                    break;
                case 1:
                    randomType = RechercheResult.Type.Handler;
                    break;
                case 2:
                    randomType = RechercheResult.Type.Hotel;
                    break;
                case 3:
                    randomType = RechercheResult.Type.LocalContact;
                    break;
                case 4:
                    randomType = RechercheResult.Type.NationalContact;
                    break;
                case 5:
                    randomType = RechercheResult.Type.RentalCar;
                    break;
                default:
                    randomType = RechercheResult.Type.CateringCompany;
            }
            item.setCaption(randomType.getClass().getName());
            item.setType(randomType);
            item.setPersistent(Math.random() > .5);
            item.setTransient(Math.random() > .75);
            items.add(item);
        }
        return items;
    }
}
