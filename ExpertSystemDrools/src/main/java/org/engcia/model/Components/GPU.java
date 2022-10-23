package org.engcia.model.Components;

import java.util.ArrayList;
import java.util.List;

public class GPU extends Component {
    public GPUBrand Brand = GPUBrand.NA;
    public int Memory = 0; // GB
    public GPUMemoryType MemoryType = GPUMemoryType.NA;
    public int MaxClock = 0; // MHz
    public int Voltage = 0; // W
    public int FansCount = 0;
    public List<ATXCompatibilityType> ATXCompatibilityList = new ArrayList<>();
    public int BenchmarkScore = 0; // Avg. FPS --> 3D DX9

    public GPU(){
        super();
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    public boolean isAMD(){
        return this.Brand.equals(GPUBrand.AMD);
    }

    public boolean isNVIDIA(){
        return this.Brand.equals(GPUBrand.NVIDIA);
    }

    public enum GPUMemoryType{
        NA,
        GDDR2,
        GDDR3,
        GDDR5,
        GDDR6
    }

    public enum GPUBrand{
        NA,
        NVIDIA,
        AMD,
    }
}

