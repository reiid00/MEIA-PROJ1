package org.engcia.model.Components;

import java.util.Date;

public class Component {
    public String ID = "";
    public String Manufacturer = "";
    public String Name = "";
    public float BasePrice = 0;
    public Date LaunchDate = new Date();

    public Component(){
    }

    public String toString()
    {
        return ID + ", " + Manufacturer + ", " + Name + ", " + BasePrice + ", " + LaunchDate.toString();
    }
}
