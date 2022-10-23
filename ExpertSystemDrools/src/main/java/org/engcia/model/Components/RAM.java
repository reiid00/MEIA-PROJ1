package org.engcia.model.Components;

public class RAM extends Component{

    public int Speed = 0; // MHz
    public int Capacity = 0;
    public int SlotsCount = 0;
    public RAMType RamType = RAMType.NA;
    public float Voltage = 0; // W

    public RAM(){
        super();
    }


    @Override
    public String toString()
    {
        return super.toString();
    }

}