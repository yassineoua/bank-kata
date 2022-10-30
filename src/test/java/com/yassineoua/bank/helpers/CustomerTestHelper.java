package com.yassineoua.bank.helpers;

import com.yassineoua.bank.domain.model.Customer;

import java.util.Arrays;
import java.util.function.Consumer;

public class CustomerTestHelper {

    public static Consumer<Customer> withId(Long id) {
        return (customer) -> customer.setId(id);
    }

    public static Consumer<Customer> withName(String firstName, String lastName) {
        return (customer) -> {
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
        };
    }

    public static Consumer<Customer> withEmail(String email) {
        return (customer) -> customer.setEmail(email);
    }

    @SafeVarargs
    public static Customer buildCustomer(Consumer<Customer>... setters) {
        Customer customer = new Customer();
        Arrays.stream(setters).forEach(setter -> setter.accept(customer));
        return customer;
    }
}
