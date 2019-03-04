package com.supplytrack.model;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by nosakhare on 15/09/2017.
 */

public class KResponse implements Serializable
{
    public String response_code;
    public String response_message;
    public String meta;
    public boolean status;
    public JsonObject data;
}
