package org.gruppeEins;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerManager {

    private final List<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer) {
        if (customer != null) {
            customers.add(customer);
        }
    }

    public Optional<Customer> getCustomerById(int id) {
        return customers.stream()
                .filter(customer -> customer.getId() == id)
                .findFirst();
    }
    
    // The update method in the diagram is generic. A common way to implement it
    // is to find the customer by ID and then update its properties.
    // However, since the Customer object is mutable, the caller can just get the
    // customer and call its setters. An explicit update method here might be redundant
    // unless it's for replacement. I will assume it's for replacement.
    public void updateCustomer(Customer updatedCustomer) {
        if (updatedCustomer == null) {
            return;
        }
        getCustomerById(updatedCustomer.getId()).ifPresent(existingCustomer -> {
            int index = customers.indexOf(existingCustomer);
            customers.set(index, updatedCustomer);
        });
    }

    public void removeCustomer(int id) {
        customers.removeIf(customer -> customer.getId() == id);
    }
    
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    @Override
    public String toString() {
        return "CustomerManager{" +
                "customers=" + customers +
                '}';
    }
}
