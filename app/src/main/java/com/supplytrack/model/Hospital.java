package com.supplytrack.model;

import java.io.Serializable;

import ir.mirrajabi.searchdialog.core.Searchable;

public class Hospital implements Serializable, Searchable {

    private String id;
    private String mTitle;
    private String hospital_name;

    public Hospital(String title)
    {
        this.mTitle = title;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    public Hospital setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }
}
