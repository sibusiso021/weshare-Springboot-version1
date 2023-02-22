package weshare.services.impl;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import weshare.model.Person;
import weshare.services.PersonDaoService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;



@Service
public class PersonDaoServiceImpl implements PersonDaoService {
    private final Set<Person> setOfPeople;

    public PersonDaoServiceImpl() {
        setOfPeople = new HashSet<>();
    }

    public PersonDaoServiceImpl(Collection<Person> people) {
        setOfPeople = new HashSet<>(people);
    }

    @Override
    public Optional<Person> findPersonByEmail(String email) {
        return setOfPeople.stream().filter(p -> p.getEmail().equals(email)).findFirst();
    }

    @Override
    public Person savePerson(Person person) {
        if (findPersonByEmail(person.getEmail()).isEmpty()) setOfPeople.add(person);
        return person;
    }
}
