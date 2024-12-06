/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.three_amigas.LaundryOps.Models;

public class SQLquery {
    public int id;
    public String name;
    public String number;
    public String email;
    public String date;
    public boolean done;
    public boolean mailed; 
    
    public SQLquery (int id, String name, String number, String email, String date, boolean done, boolean mailed) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.date = date;
        this.done = done;
        this.mailed = mailed;
    }
}
