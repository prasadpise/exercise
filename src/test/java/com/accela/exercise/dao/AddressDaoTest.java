package com.accela.exercise.dao;
import com.accela.exercise.entity.Address;
import com.accela.exercise.entity.Person;
import com.accela.exercise.service.dao.AddressDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class AddressDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressDao addressDao;

    @Test
    public void findAllAddresses(){
        Person person = new Person();
        person.setPersonId("1");
        person.setFirstName("Test");
        person.setLastName("Name");
        Address address = new Address();
        address.setAddressId("1");
        address.setStreet("Old Dun Laoghaire Road");
        address.setCity("Dublin");
        address.setState("Dublin");
        address.setPostalCode("A96XH76");
        address.setPerson(person);

        Address address1 = new Address();
        address1.setAddressId("2");
        address1.setStreet("Sweetmans Road");
        address1.setCity("Blackrock");
        address1.setState("Dublin");
        address1.setPostalCode("A94RV10");
        address1.setPerson(person);


        List<Address> addressListOriginal = new ArrayList<>();
        addressListOriginal.add(address);
        addressListOriginal.add(address1);
        person.setAddressList(addressListOriginal);
        entityManager.persist(person);
        entityManager.flush();
        entityManager.persist(address);
        entityManager.flush();
        entityManager.persist(address1);
        entityManager.flush();

        List<Address> addressList = addressDao.findAll();
        assert(addressList.size()==2);
        assert(addressList.get(0).getAddressId().equals("1"));
        assert(addressList.get(1).getAddressId().equals("2"));
    }

    @Test
    public void findAddressById(){
        Person person = new Person();
        person.setPersonId("1");
        person.setFirstName("Test");
        person.setLastName("Name");
        Address address = new Address();
        address.setAddressId("1");
        address.setStreet("Old Dun Laoghaire Road");
        address.setCity("Dublin");
        address.setState("Dublin");
        address.setPostalCode("A96XH76");
        address.setPerson(person);
        List<Address> addressListOriginal = new ArrayList<>();
        addressListOriginal.add(address);
        entityManager.persist(person);
        entityManager.flush();
        entityManager.persist(address);
        entityManager.flush();
        Optional<Address> returnedAddress = addressDao.findById("1");
        assert(returnedAddress.isPresent());
        assert(returnedAddress.get().getAddressId().equals("1"));
    }

    @Test
    public void updateAddress(){
        Person person = new Person();
        person.setPersonId("1");
        person.setFirstName("Test");
        person.setLastName("Name");
        Address address = new Address();
        address.setAddressId("1");
        address.setStreet("Old Dun Laoghaire Road");
        address.setCity("Dublin");
        address.setState("Dublin");
        address.setPostalCode("A96XH76");
        address.setPerson(person);
        List<Address> addressListOriginal = new ArrayList<>();
        addressListOriginal.add(address);
        entityManager.persist(person);
        entityManager.flush();
        entityManager.persist(address);
        entityManager.flush();

        address.setState("Leicester");
        Address returnedAddress = addressDao.save(address);
        assert(returnedAddress != null);
        assert(returnedAddress.getAddressId().equals("1"));
        assert(returnedAddress.getState().equals("Leicester"));
    }
}
