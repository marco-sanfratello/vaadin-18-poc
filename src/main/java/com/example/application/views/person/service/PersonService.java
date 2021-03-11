package com.example.application.views.person.service;

import com.example.application.data.DataService;
import com.example.application.views.person.model.Company;
import com.example.application.views.person.model.Person;
import com.vaadin.flow.data.provider.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PersonService implements DataService<Person, String> {
    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

    private final List<Person> data;

    public PersonService() {
        data = generateData(10000);
    }

    @Override
    public Stream<Person> fetch(Query<Person, String> query) {
        LOG.info("fetch called");
        return data.stream().filter(p -> p.contains(query.getFilter().orElse(""))).skip(query.getOffset()).limit(query.getLimit());
    }

    @Override
    public int getCount(Query<Person, String> query) {
        LOG.info("getCount called");
        return (int) data.stream().filter(p -> p.contains(query.getFilter().orElse(""))).count();
    }

    @Override
    public List<Person> getAll() {
        LOG.info("getAll called");
        return data;
    }

    public Optional<Person> get(String id) {
        return data.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public void update(Person sp) {
        int index = -1;
        for (int i = 0; i < data.size() && index == -1; i++) {
            Person p = data.get(i);
            if (p.getId().equals(sp.getId())) {
                index = i;
            }
        }
        if (index == -1) {
            sp.setId(String.valueOf(data.size()));
            data.add(sp);
        } else {
            data.remove(index);
            data.add(index, sp);
        }
    }

    private List<Person> generateData(int amount) {
        List<Person> result = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Person person = new Person();
            person.setId(String.valueOf(i));
            person.setFirstName("Firstname " + i);
            person.setLastName("Lastname " + i);
            Company company1 = new Company();
            company1.setShortName("Company 1." + i);
            Company company2 = new Company();
            company2.setShortName("Company 2." + i);
            HashSet<Company> companies = new HashSet<>();
            companies.add(company1);
            companies.add(company2);
            person.setCompanies(companies);
            result.add(person);
        }
        return result;
    }
}
