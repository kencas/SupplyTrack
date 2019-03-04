package com.supplytrack.model;

import java.io.Serializable;

public class Customer implements Serializable {
    public String id;
    public String first_name;
    public String last_name;
    public String email;
    public boolean loggedOn;
}
