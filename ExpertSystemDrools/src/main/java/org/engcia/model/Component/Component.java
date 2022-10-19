package org.engcia.model.Component;

import java.util.Date;

public class Component {
    public String Manufacturer = "";
    public String Name = "";
    public float BasePrice = 0;
    public Date LaunchDate = new Date();

    public Component(){
    }

    public String toString()
    {
        return Manufacturer + ", " + Name + ", " + BasePrice + ", " + LaunchDate.toString();
    }
}
