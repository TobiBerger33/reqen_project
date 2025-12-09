package org.gruppeEins;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerManager {

    private final List<Customer> customers = new ArrayList<>();
    private final List<String> emailList = new ArrayList<>();

    public void addCustomer(Customer customer) {
        if (customer != null) {
            if (emailList.contains(customer.getEmail())) {
                throw new IllegalArgumentException("Email already registered");
            }
            customers.add(customer);
            emailList.add(customer.getEmail());
        }
    }

    public Optional<Customer> getCustomerById(int id) {
        return customers.stream()
                .filter(customer -> customer.getId() == id)
                .findFirst();
    }

    public void updateCustomer(int id, String newName, String newEmail) {
        getCustomerById(id).ifPresent(customer -> {
            if (newName != null) {
                if (newName.isEmpty()) {
                    throw new IllegalArgumentException("Name cannot be empty");
                }
                customer.setName(newName);
            }
            if (newEmail != null && !newEmail.isEmpty()) {
                if (emailList.contains(newEmail) && !customer.getEmail().equals(newEmail)) {
                    throw new IllegalArgumentException("Email already registered");
                }
                String oldEmail = customer.getEmail();
                customer.setEmail(newEmail);
                emailList.remove(oldEmail);
                emailList.add(newEmail);
            }
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
            emailList.remove(customer.getEmail());
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
