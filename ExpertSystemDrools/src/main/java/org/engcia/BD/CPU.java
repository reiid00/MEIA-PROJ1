package org.engcia.BD;

public class CPU extends Component {

    public int coreCount = 0;
    public int threadsCount = 0;
    public float boostClock = 0; // MHz
    public float voltage = 0; // W
    public int benchmarkScore = 0; // Points
    public SocketType socket = SocketType.NA;

    public boolean hasIntegratedGPU=false;




    public CPU(){
        super();
        this.manufacturer=CPUManufactor.NA.toString();
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    public boolean isAMD(){
        return this.manufacturer.toUpperCase().equals(CPUManufactor.AMD);
    }

    public boolean isIntel(){
        return this.manufacturer.toUpperCase().equals(CPUManufactor.INTEL);
    }

    public enum CPUManufactor{
        NA,
        INTEL,
        AMD,
    }
}

