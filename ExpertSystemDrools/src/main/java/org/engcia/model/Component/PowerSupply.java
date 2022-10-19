package org.engcia.model.Component;

public class PowerSupply extends Component {
    public int Capacity = 0; // W
    public EnergyEfficiency MemoryType = EnergyEfficiency.NA;
    public ATXType ATXType;

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
        _80PLUS_COPPER,
        _80PLUS_SILVER,
        _80PLUS_GOLD
    }
}

