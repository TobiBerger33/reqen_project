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
        Optional<Customer> customerOpt = getCustomerById(id);

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();

            if (customer.getCredit() < 0) {
                throw new IllegalArgumentException("Cannot delete customer with outstanding debt");
            }

            customers.remove(customer);
        }
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
