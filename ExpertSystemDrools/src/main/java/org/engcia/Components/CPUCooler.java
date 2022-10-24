package org.engcia.Components;

import java.util.ArrayList;
import java.util.List;

public class CPUCooler extends Component {

    public boolean isWaterCooled = false;
    public boolean isFanless = false;
    public List<SocketType> socketCompatabilityList = new ArrayList<>();

    public CPUCooler(){
        super();
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    public boolean isCompatible (SocketType socket){
        return socketCompatabilityList.contains(socket);
    }

}

