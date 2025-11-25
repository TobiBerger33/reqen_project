package org.gruppeEins;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InvoiceManager {

    private final List<Invoice> invoices = new ArrayList<>();

    public void addInvoice(Invoice invoice) {
        if (invoice != null) {
            invoices.add(invoice);
            // When an invoice is added, the customer's credit should be reduced.
            Customer customer = invoice.getChargingSession().getCustomer();
            customer.reduceCredit(invoice.getAmount());
        }
    }

    public Optional<Invoice> getInvoiceById(int id) {
        return invoices.stream()
                .filter(invoice -> invoice.getId() == id)
                .findFirst();
    }

    public void updateInvoice(Invoice updatedInvoice) {
        if (updatedInvoice == null) {
            return;
        }
        getInvoiceById(updatedInvoice.getId()).ifPresent(existingInvoice -> {
            int index = invoices.indexOf(existingInvoice);
            invoices.set(index, updatedInvoice);
        });
    }

    public void removeInvoice(int id) {
        invoices.removeIf(invoice -> invoice.getId() == id);
    }
    
    public List<Invoice> getAllInvoices() {
        return new ArrayList<>(invoices);
    }

    @Override
    public String toString() {
        return "InvoiceManager{" +
                "invoices=" + invoices +
                '}';
    }
}
