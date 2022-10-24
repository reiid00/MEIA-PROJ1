package org.engcia.Components;

import java.util.Date;

public class Component {
    public String id = "";
    public String manufacturer = "";
    public String name = "";
    public float basePrice = 0;
    public Date launchDate = new Date();

    public Component(){
    }

    public String toString()
    {
        return id + ", " + manufacturer + ", " + name + ", " + basePrice + ", " + launchDate.toString();
    }
}
