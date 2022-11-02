package org.engcia.BD;

import java.util.ArrayList;
import java.util.List;

public class GPU extends Component {
    public GPUBrand brand = GPUBrand.NA;
    public int memory = 0; // GB
    public GPUMemoryType memoryType = GPUMemoryType.NA;
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
        return this.manufacturer.equals("AMD");
    }

    public boolean isNVIDIA(){
        return this.manufacturer.equals("NVIDIA");
    }

    public enum GPUMemoryType{
        NA,
        GDDR4,
        GDDR5,
        GDDR6,
        GDDR6X
    }



    public enum GPUBrand{
        NA,
        EVGA,
        ASUS,
        GIGABYTE,
        MSI,
        SAPPHIRE
    }
}

