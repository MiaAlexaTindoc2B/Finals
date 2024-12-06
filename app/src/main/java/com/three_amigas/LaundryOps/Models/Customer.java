package com.three_amigas.LaundryOps.Models;

public class Customer {
    public final String name;
    public final String number;
    public final String email;
    public final String date;
    public final boolean done;
    public final boolean mailed;

    public Customer(String name, String number, String email, String date, boolean done, boolean mailed) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.date = date;
        this.done = done;
        this.mailed = mailed;
    }
}
