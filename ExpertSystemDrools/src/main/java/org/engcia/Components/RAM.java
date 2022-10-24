package org.engcia.Components;

public class RAM extends Component{

    public int speed = 0; // MHz
    public int capacity = 0;
    public int slotsCount = 0;
    public RAMType ramType = RAMType.NA;
    public float voltage = 0; // W

    public RAM(){
        super();
    }


    @Override
    public String toString()
    {
        return super.toString();
    }

}
