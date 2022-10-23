package org.engcia.model.Component;

import java.util.ArrayList;
import java.util.List;

public class Motherboard  extends Component {
    public List<SocketType> SocketCompatabilityList = new ArrayList<>();
    public ATXCompatibilityType ATXType;
    public int MaxMemoryRam =0;//GB
    public RAMType ramType;

    public Motherboard(){
        super();
    }

    public int ramSlots=0;
    public List<String> ramSpeedList = new ArrayList<>();

    @Override
    public String toString()
    {
        return super.toString();
    }

    public List<SocketType> getSocketCompatabilityList() {
        return SocketCompatabilityList;
    }

    public ATXCompatibilityType getATXType() {
        return ATXType;
    }

    public int getMaxMemoryRam() {
        return MaxMemoryRam;
    }

    public RAMType getRamType() {
        return ramType;
    }

    public int getRamSlots() {
        return ramSlots;
    }

    public List<String> getRamSpeedList() {
        return ramSpeedList;
    }

    public void setSocketCompatabilityList(List<SocketType> socketCompatabilityList) {
        SocketCompatabilityList = socketCompatabilityList;
    }

    public void setATXType(ATXCompatibilityType ATXType) {
        this.ATXType = ATXType;
    }

    public void setMaxMemoryRam(int maxMemoryRam) {
        MaxMemoryRam = maxMemoryRam;
    }

    public void setRamType(RAMType ramType) {
        this.ramType = ramType;
    }

    public void setRamSlots(int ramSlots) {
        this.ramSlots = ramSlots;
    }

    public void setRamSpeedList(List<String> ramSpeedList) {
        this.ramSpeedList = ramSpeedList;
    }


}
