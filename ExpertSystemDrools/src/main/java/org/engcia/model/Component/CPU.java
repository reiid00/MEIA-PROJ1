package org.engcia.model.Component;

import java.util.ArrayList;
import java.util.List;

public class CPU extends Component {
    public String Brand = "";
    public int CoreCount = 0;
    public int ThreadsCount = 0;
    public int BoostClock = 0; // MHz
    public float Voltage = 0; // W
    public int BenchmarkScore = 0; // Points
    public SocketType Socket = SocketType.NA;

    public CPU(){
        super();
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    public boolean isAMD(){
        return this.Brand.toUpperCase().equals("AMD");
    }

    public boolean isIntel(){
        return this.Brand.toUpperCase().equals("INTEL");
    }

}

