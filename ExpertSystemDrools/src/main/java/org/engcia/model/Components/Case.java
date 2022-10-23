package org.engcia.model.Components;

import java.util.ArrayList;
import java.util.List;

public class Case extends Component {
    public TowerSizeType SizeType = TowerSizeType.NA;
    public List<ATXCompatibilityType> ATXCompatibilityList = new ArrayList<>();
    public String Color = "";

    public Case(){
        super();
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    public enum TowerSizeType{
        NA,
        MICRO_ATX_TOWER,
        MINI_ATX_TOWER,
        MID_TOWER,
        FULL_TOWER,
    }
}
