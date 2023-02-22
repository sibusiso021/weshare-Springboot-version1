package weshare.services;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import weshare.model.Person;

import java.util.Optional;
@Service
public interface PersonDaoService {
    Optional<Person> findPersonByEmail(String email);
    Person savePerson(Person person);
}
