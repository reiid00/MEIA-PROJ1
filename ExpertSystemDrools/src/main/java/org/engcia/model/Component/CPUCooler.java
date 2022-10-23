package org.engcia.model.Component;

import java.util.ArrayList;
import java.util.List;

public class CPUCooler extends Component {

    public boolean IsWaterCooled = false;
    public boolean IsFanless = false;
    public List<SocketType> SocketCompatabilityList = new ArrayList<>();

    public CPUCooler(){
        super();
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    public boolean isCompatible (SocketType socket){
        return SocketCompatabilityList.contains(socket);
    }

    public boolean isWaterCooled(){
        return IsWaterCooled;
    }

    public boolean isFanless(){
        return IsFanless;
    }

}

