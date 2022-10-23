package org.engcia.model.Components;

public class CPU extends Component {

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
        return this.Manufacturer.toUpperCase().equals("AMD");
    }

    public boolean isIntel(){
        return this.Manufacturer.toUpperCase().equals("INTEL");
    }

}

