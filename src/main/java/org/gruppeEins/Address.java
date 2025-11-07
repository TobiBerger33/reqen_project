package org.gruppeEins;

public class Address
{
    private int ID;
    private String postCode;
    private String street;
    private String city;
    private int houseNumber;
    private String country;

    protected Address(int ID, String postCode, String street, String city, int houseNumber, String country)
    {
        this.ID = ID;
        this.postCode = postCode;
        this.street = street;
        this.city = city;
        this.houseNumber = houseNumber;
        this.country = country;
    }

    protected void updateAddress(int ID, String postCode, String street, String city, int houseNumber, String country)
    {
        this.ID = ID;
        this.postCode = postCode;
        this.street = street;
        this.city = city;
        this.houseNumber = houseNumber;
        this.country = country;
    }
}
