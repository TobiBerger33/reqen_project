package org.gruppeEins;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private static int nextID = 0;
    private int id;
    private String name;
    private String email;
    private double credit = 0.00;
    private static final List<String> emailList= new ArrayList<>();

    public Customer(String name, String email, double initialCredit) {
        this.id = ++nextID;
        this.name = name;
        this.email = email;
        this.credit = initialCredit;
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

    public void setEmail(String email)
    {
        this.email = email;
    }

    protected Customer(String name, String email)
    {
        if (validInput(name, email) && validEmail(email) && availableEmail(email))
        {
            this.id = nextID++;
            this.name = name;
            this.email = email;
            emailList.add(email);
        }
    }

    private boolean validInput(String name, String email)
    {
        if(name.isEmpty() || email.isEmpty())
        {
            throw new IllegalArgumentException("Required information missing");
        }

        return true;
    }

    private boolean validEmail(String email)
    {
        if(!email.contains("@") || !email.contains("."))
        {
            throw new IllegalArgumentException("Invalid email format");
        }
        return true;
    }

    private boolean availableEmail(String email)
    {
        if(emailList.contains(email))
        {
            throw new IllegalArgumentException("Email already registered");
        }

        return true;
    }
}
