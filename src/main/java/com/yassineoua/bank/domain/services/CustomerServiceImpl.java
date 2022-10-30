package com.yassineoua.bank.domain.services;

import com.yassineoua.bank.domain.exceptions.NotFoundCustomerException;
import com.yassineoua.bank.domain.model.Customer;
import com.yassineoua.bank.domain.repositories.CustomerRepository;
import com.yassineoua.bank.domain.util.ValidationUtils;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        ValidationUtils.shouldBeNotNull(customer, "customer must be not null");
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long customerId) {
        ValidationUtils.shouldBeNotNull(customerId, "customerId must be not null");
        Customer customer = customerRepository.findById(customerId);
        if (Objects.isNull(customer)) {
            throw new NotFoundCustomerException(customerId);
        }
        return customer;
    }
}
