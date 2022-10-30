package org.engcia.BD;



public class Storage extends Component{
    public boolean isSSD = false;
    public boolean isSATA = false;
    public int capacity = 0; // GB
    public int cache = 0; // GB
    public int benchmarkScore = 0; // MB/s

    public Storage(){
        super();
    }

    public Storage(int cap){
        super();
        this.capacity=cap;
    }

    @Override
    public String toString()
    {
        if (isSSD) return "SSD " + capacity;
        return "HDD " + capacity;
    }
}
