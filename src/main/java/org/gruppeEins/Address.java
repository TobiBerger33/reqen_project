package org.gruppeEins;

public class Address
{
    private int id;
    private String postCode;
    private String street;
    private String city;
    private int houseNumber;
    private String country;

    protected Address(String postCode, String street, String city, int houseNumber, String country)
    {
        this.postCode = postCode;
        this.street = street;
        this.city = city;
        this.houseNumber = houseNumber;
        this.country = country;
    }

    protected void updateAddress(String postCode, String street, String city, int houseNumber, String country)
    {
        this.postCode = postCode;
        this.street = street;
        this.city = city;
        this.houseNumber = houseNumber;
        this.country = country;
    }
}
