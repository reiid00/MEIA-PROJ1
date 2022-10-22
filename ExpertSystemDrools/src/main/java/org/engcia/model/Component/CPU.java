package org.engcia.model.Component;

import java.util.ArrayList;
import java.util.List;

public class CPU extends Component {
    public String brand = "";
    public int coreCount = 0;
    public int threadsCount = 0;
    public int boosClock = 0; // MHz
    public float voltage = 0; // W
    public int benchmark = 0;
    public SocketType socket = SocketType.NA;

    public CPU(){
        super();
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    public boolean isAMD(){
        return this.brand.toUpperCase().equals("AMD");
    }

    public boolean isIntel(){
        return this.brand.toUpperCase().equals("Intel");
    }

}

