package weshare.services.impl;

import weshare.model.Person;
import weshare.services.PersonDaoService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PersonDoaImpl implements PersonDaoService {
    private final Set<Person> setOfPeople;

    public PersonDoaImpl() {
        setOfPeople = new HashSet<>();
    }

    public PersonDoaImpl(Collection<Person> people) {
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
