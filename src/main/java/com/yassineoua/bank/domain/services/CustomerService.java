package com.yassineoua.bank.domain.services;

import com.yassineoua.bank.domain.model.Customer;

public interface CustomerService {

    Customer saveCustomer(Customer customer);

    Customer getCustomer(Long customerId);
}
