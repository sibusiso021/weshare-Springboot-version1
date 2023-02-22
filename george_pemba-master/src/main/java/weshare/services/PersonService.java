package weshare.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weshare.model.Person;

import java.util.HashMap;
import java.util.Map;

@Service
public class PersonService {
    private final Map<String, Person> persons = new HashMap<>();

    @Autowired
    public PersonService() {
        String email = "john@example.com";
        persons.put("person1", new Person(email));
    }

    public Person findPersonById(String personId) {
        return persons.get(personId);
    }
}

