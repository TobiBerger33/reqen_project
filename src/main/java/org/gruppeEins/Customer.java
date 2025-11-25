package org.gruppeEins;

public class Customer {

    private static int nextID = 0;
    private final int id;
    private String name;
    private String email;
    private double credit;

    public Customer(String name, String email, double initialCredit) {
        this.id = ++nextID;
        this.name = name;
        this.email = email;
        this.credit = initialCredit;
    }

    public Customer(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getCredit() {
        return credit;
    }

    public Customer increaseCredit(double amount) {
        if (amount > 0) {
            this.credit += amount;
        }
        return this;
    }

    public Customer reduceCredit(double amount) {
        if (amount > 0) {
            this.credit -= amount;
        }
        return this;
    }

    // Setters for name and email might be useful
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
