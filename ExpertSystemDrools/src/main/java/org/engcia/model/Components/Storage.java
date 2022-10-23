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

    public boolean isSSD() {
        return isSSD;
    }

    public int getCacheGB() {
        return cacheGB;
    }

    public int getCapacityGB() {
        return capacityGB;
    }

    public int getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(int benchmark) {
        benchmark = benchmark;
    }

    public void setCacheGB(int cacheGB) {
        cacheGB = cacheGB;
    }

    public void setCapacityGB(int capacityGB) {
        capacityGB = capacityGB;
    }

    public void setSSD(boolean SSD) {
        isSSD = SSD;
    }
}
