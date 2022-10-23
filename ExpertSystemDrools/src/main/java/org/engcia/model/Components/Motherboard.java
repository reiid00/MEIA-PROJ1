package org.engcia.model.Components;

import java.util.ArrayList;
import java.util.List;

public class Motherboard  extends Component {
    public List<SocketType> socketCompatabilityList = new ArrayList<>();
    public ATXCompatibilityType atxType;
    public int maxMemoryRam =0;//GB
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
        return socketCompatabilityList;
    }

    public ATXCompatibilityType getATXType() {
        return atxType;
    }

    public int getMaxMemoryRam() {
        return maxMemoryRam;
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
        socketCompatabilityList = socketCompatabilityList;
    }

    public void setATXType(ATXCompatibilityType ATXType) {
        this.atxType = ATXType;
    }

    public void setMaxMemoryRam(int maxMemoryRam) {
        maxMemoryRam = maxMemoryRam;
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
