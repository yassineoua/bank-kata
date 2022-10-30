package com.yassineoua.bank.domain.services;


import com.yassineoua.bank.domain.exceptions.NotFoundCustomerException;
import com.yassineoua.bank.helpers.CustomerTestHelper;
import com.yassineoua.bank.domain.model.Customer;
import com.yassineoua.bank.domain.repositories.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.yassineoua.bank.helpers.CustomerTestHelper.buildCustomer;
import static com.yassineoua.bank.helpers.CustomerTestHelper.withName;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomer() {
        Customer customer = buildCustomer(CustomerTestHelper.withId(1L), withName("Jack", "mimoun"));

        Mockito.when(customerRepository.findById(customer.getId())).thenReturn(customer);

        Customer retrievedCustomer = customerService.getCustomer(1L);

        Assertions.assertNotNull(retrievedCustomer);
        Assertions.assertEquals(customer.getId(), retrievedCustomer.getId());
    }

    @Test
    void testGetCustomerWhenNotFound() {
        Mockito.when(customerRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(NotFoundCustomerException.class, () -> {
            customerService.getCustomer(1L);
        });
    }

    @Test
    void testSaveCustomer() {
        Customer customer = buildCustomer(CustomerTestHelper.withId(1L), withName("Jack", "mimoun"));

        customerService.saveCustomer(customer);

        Mockito.verify(customerRepository, Mockito.times(1)).save(customer);
    }

}
