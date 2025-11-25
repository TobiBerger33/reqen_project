package org.gruppeEins;

public class Address {

    private static int nextID = 0;
    private final int id;
    private String postCode;
    private String street;
    private String city;
    private int houseNumber;
    private String country;

    public Address(String postCode, String street, String city, int houseNumber, String country) {
        this.id = ++nextID;
        this.postCode = postCode;
        this.street = street;
        this.city = city;
        this.houseNumber = houseNumber;
        this.country = country;
    }

    // updateAddress method could take new values as parameters
    public void updateAddress(String postCode, String street, String city, int houseNumber, String country) {
        this.postCode = postCode;
        this.street = street;
        this.city = city;
        this.houseNumber = houseNumber;
        this.country = country;
    }

    // Getters for all fields
    public int getId() {
        return id;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getCountry() {
        return country;
    }
}
