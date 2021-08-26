package com.accela.exercise.dao;

import com.accela.exercise.entity.Person;
import com.accela.exercise.service.dao.PersonDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class PersonDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PersonDao personDao;

    @Test
    public void findAllPersons(){
        Person person = new Person();
        person.setPersonId("1");
        person.setFirstName("Test");
        person.setLastName("Name");
        person.setAddressList(null);
        entityManager.persist(person);
        entityManager.flush();
        Person person2 = new Person();
        person2.setPersonId("2");
        person2.setFirstName("Test2");
        person2.setLastName("Name2");
        person2.setAddressList(null);
        entityManager.persist(person2);
        entityManager.flush();

        List<Person> personList = personDao.findAll();
        assert(personList.size()==2);
        assert(personList.get(0).getPersonId().equals("1"));
        assert(personList.get(1).getPersonId().equals("2"));
    }

    @Test
    public void findPersonById(){
        Person person = new Person();
        person.setPersonId("1");
        person.setFirstName("Test");
        person.setLastName("Name");
        person.setAddressList(null);
        entityManager.persist(person);
        entityManager.flush();
        Optional<Person> returnedPerson = personDao.findById("1");
        assert(returnedPerson.isPresent());
        assert(returnedPerson.get().getPersonId().equals("1"));
    }

    @Test
    public void updatePerson(){
        Person person = new Person();
        person.setPersonId("1");
        person.setFirstName("Test");
        person.setLastName("Name");
        person.setAddressList(null);
        entityManager.persist(person);
        entityManager.flush();
        person.setLastName("NewLastName");
        Person returnedPerson= personDao.save(person);
        assert(returnedPerson!=null);
        assert(returnedPerson.getLastName().equals("NewLastName"));
    }
}
