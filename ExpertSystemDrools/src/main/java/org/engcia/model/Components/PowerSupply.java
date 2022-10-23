package org.engcia.model.Components;

import java.util.ArrayList;
import java.util.List;

public class PowerSupply extends Component {
    public int Capacity = 0; // W
    public EnergyEfficiency MemoryType = EnergyEfficiency.NA;
    public ModularType Modular = ModularType.NA;
    public List<ATXCompatibilityType> ATXCompatibilityList = new ArrayList<>();

    public PowerSupply(){
        super();
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    public enum EnergyEfficiency{
        NA,
        _80PLUS,
        _80PLUS_BRONZE,
        _80PLUS_SILVER,
        _80PLUS_GOLD,
        _80PLUS_PLATINUM,
        _80PLUS_TITANIUM
        }

    public enum ModularType{
        NA,
        FULL,
        SEMI,
        NO,
    }
}

