package com.yassineoua.bank.domain.repositories;

import com.yassineoua.bank.domain.model.Customer;

public interface CustomerRepository {

    Customer findById(Long customerId);

    Customer save(Customer customer);
}
