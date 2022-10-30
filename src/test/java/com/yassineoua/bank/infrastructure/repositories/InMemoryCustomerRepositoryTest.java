package com.yassineoua.bank.infrastructure.repositories;

import com.yassineoua.bank.domain.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static com.yassineoua.bank.helpers.CustomerTestHelper.*;

class InMemoryCustomerRepositoryTest {

    @InjectMocks
    private InMemoryCustomerRepository inMemoryCustomerRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Customer customer = buildCustomer(withName("Jack", "mimoun"), withEmail("jack@example.com"));
        Customer savedCustomer = inMemoryCustomerRepository.save(customer);

        Assertions.assertNotNull(savedCustomer);
        Assertions.assertNotNull(savedCustomer.getId());
    }

    @Test
    void testFindById() {
        Customer customer1 = buildCustomer(withName("Jack", "mimoun"), withEmail("jack@example.com"));
        Customer customer2 = buildCustomer(withName("Peter", "hash"), withEmail("peter@example.com"));
        inMemoryCustomerRepository.save(customer1);
        inMemoryCustomerRepository.save(customer2);

        Customer foundedCustomer = inMemoryCustomerRepository.findById(customer2.getId());

        Assertions.assertNotNull(foundedCustomer);
        Assertions.assertEquals("Peter", foundedCustomer.getFirstName());
    }
}
