package org.gruppeEins;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Customer(int id, String name, String email) {
        this.id = id;
        setName(name);
        setEmail(email);
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
        if (amount <= 0) {
            throw new IllegalArgumentException("Please select a positive amount greater than 0");
        } else {
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

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Required information missing");
        }
        this.name = name;
    }

    void setEmail(String email) {
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format");
        }
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

    public ChargingSession startSession(Station station, LocalDateTime startTime)
    {
        return new ChargingSession(station, this, startTime);
    }

    public ChargingSession endSession(LocalDateTime endTime, double totalEnergy, ChargingSessionManager csManager)
    {
        if (csManager != null) {
            LocalDateTime startTime = csManager.getSessionByCustomerId(id).getStartTime();

            if (startTime.isAfter(endTime)) {
                throw new IllegalArgumentException("End time cannot be before start time");
            }

            if (endTime.isEqual(startTime) ||totalEnergy <= 0) {
                throw new IllegalArgumentException("Charging session failed. Please try again");
            }

            ChargingSession session = csManager.getSessionByCustomerId(id);
            session.end(endTime, totalEnergy);

            return session;
        }
        throw new IllegalArgumentException("No ChargingSessionManager provided");
    }

    private boolean validInput(String name, String email)
    {
        if(name.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("Required information missing");
        }

        return true;
    }

    private boolean validEmail(String email)
    {
        if(!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return true;
    }

    private boolean availableEmail(String email)
    {
        if(emailList.contains(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        return true;
    }
}
