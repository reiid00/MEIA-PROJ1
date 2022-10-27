package org.engcia.BD;

import java.util.ArrayList;
import java.util.List;

public class Case extends Component {
    public TowerSizeType sizeType = TowerSizeType.NA;
    public List<ATXCompatibilityType> atxCompatibilityList = new ArrayList<>();
    public String color = "";

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
