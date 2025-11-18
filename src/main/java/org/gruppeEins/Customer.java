package org.gruppeEins;

import java.util.List;
import java.util.ArrayList;

public class Customer
{
    private int id;
    private static int nextID = 1;
    private String name;
    private String email;
    private double credit = 0.00;
    private static List<String> emailList= new ArrayList<>();

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

    public int getId()
    {
        return id;
    }

    public double getCredit()
    {
        return credit;
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
