package weshare.services;

import weshare.model.Person;

import java.util.Optional;

public interface PersonDaoService {
    Optional<Person> findPersonByEmail(String email);
    Person savePerson(Person person);
}
