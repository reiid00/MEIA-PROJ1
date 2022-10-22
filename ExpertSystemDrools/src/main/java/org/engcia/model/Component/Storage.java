package org.engcia.model.Component;



public class Storage extends Component{
    public boolean isSSD = false;
    public int CapacityGB = 0; // GB
    public int CacheGB = 0; // GB
    public int Benchmark = 0; // Mb/s

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
        return CacheGB;
    }

    public int getCapacityGB() {
        return CapacityGB;
    }

    public int getBenchmark() {
        return Benchmark;
    }

    public void setBenchmark(int benchmark) {
        Benchmark = benchmark;
    }

    public void setCacheGB(int cacheGB) {
        CacheGB = cacheGB;
    }

    public void setCapacityGB(int capacityGB) {
        CapacityGB = capacityGB;
    }

    public void setSSD(boolean SSD) {
        isSSD = SSD;
    }
}
