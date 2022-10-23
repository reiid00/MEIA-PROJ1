package org.engcia.model.Components;

import java.util.ArrayList;
import java.util.List;

public class GPU extends Component {
    public String brand = "";
    public int memory = 0; // GB
    public GPUMemoryType memorytype = GPUMemoryType.NA;
    public int maxClock = 0; // MHz
    public int voltage = 0; // W
    public int fansCount = 0;
    public List<ATXCompatibilityType> atxCompatibilityTypes = new ArrayList<>();
    public int benchmarkScore = 0; // Points

    public GPU(){
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

    public boolean isNVIDIA(){
        return this.brand.toUpperCase().equals("NVIDIA");
    }

    public enum GPUMemoryType{
        NA,
        GDDR4,
        GDDR5,
        GDDR6
    }
}

