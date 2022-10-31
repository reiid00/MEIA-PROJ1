package org.engcia.BD;

import java.util.ArrayList;
import java.util.List;

public class CPUCooler extends Component {

    public int voltage = 0;
    public boolean isWaterCooled = false;
    public boolean isFanless = false;
    public List<SocketType> socketCompatabilityList = new ArrayList<>();
    public CoolerType cooler=CoolerType.NA;

    public CPUCooler(){
        super();
    }

    public CoolerType getCooler() {
        return cooler;
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    public boolean isCompatible (SocketType socket){
        return socketCompatabilityList.contains(socket);
    }
    public enum CoolerType{
        NA,
        NORMAL,
        WATERCOOLING,
        FANLESS
    }
}

