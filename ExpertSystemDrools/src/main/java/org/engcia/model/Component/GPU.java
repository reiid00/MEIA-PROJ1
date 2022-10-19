package org.engcia.model.Component;

import java.util.ArrayList;
import java.util.List;

public class GPU extends Component {
    public String Brand = "";
    public int Memory = 0; // GB
    public GPUMemoryType MemoryType = GPUMemoryType.NA;
    public int MaxClock = 0; // MHz
    public int Voltage = 0; // W
    public int FansCount = 0;
    public List<ATXCompatibilityType> ATXCompatibilityList = new ArrayList<>();
    public int BenchmarkScore = 0; // Points

    public GPU(){
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

    public boolean isNVIDIA(){
        return this.Brand.toUpperCase().equals("NVIDIA");
    }

    public enum GPUMemoryType{
        NA,
        GDDR4,
        GDDR5,
        GDDR6
    }
}

