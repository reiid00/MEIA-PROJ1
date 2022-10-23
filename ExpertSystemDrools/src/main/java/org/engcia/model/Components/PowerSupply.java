package org.engcia.model.Components;

import java.util.ArrayList;
import java.util.List;

public class PowerSupply extends Component {
    public int capacity = 0; // W
    public EnergyEfficiency memoryType = EnergyEfficiency.NA;
    public ModularType modular = ModularType.NA;
    public List<ATXCompatibilityType> atxCompatibilityTypes = new ArrayList<>();

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

