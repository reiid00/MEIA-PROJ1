package org.engcia.model.Components;

public class CPU extends Component {

    public int coreCount = 0;
    public int threadsCount = 0;
    public int boostClock = 0; // MHz
    public float voltage = 0; // W
    public int benchmarkScore = 0; // Points
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
        return this.manufacturer.toUpperCase().equals("AMD");
    }

    public boolean isIntel(){
        return this.manufacturer.toUpperCase().equals("INTEL");
    }

}

