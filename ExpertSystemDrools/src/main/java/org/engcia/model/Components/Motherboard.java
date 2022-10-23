package org.engcia.model.Components;

import java.util.ArrayList;
import java.util.List;

public class Motherboard  extends Component {

    public List<SocketType> socketCompatabilityList = new ArrayList<>();
    public ATXCompatibilityType atxType;
    public int maxMemoryRam =0;//GB
    public RAMType ramType;
    public int ramSlots=0;
    public List<String> ramSpeedList = new ArrayList<>();

    public Motherboard(){
        super();
    }


    @Override
    public String toString()
    {
        return super.toString();
    }

}
