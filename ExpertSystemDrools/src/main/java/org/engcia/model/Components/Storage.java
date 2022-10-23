package org.engcia.model.Components;



public class Storage extends Component{
    public boolean isSSD = false;
    public int capacityGB = 0; // GB
    public int cacheGB = 0; // GB
    public int benchmark = 0; // Mb/s

    public Storage(){
        super();
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
