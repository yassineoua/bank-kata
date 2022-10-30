package com.yassineoua.bank.infrastructure.repositories;

import com.yassineoua.bank.domain.model.Customer;
import com.yassineoua.bank.domain.repositories.CustomerRepository;

public class InMemoryCustomerRepository extends AbstractInMemoryRepository<Customer> implements CustomerRepository {
}
