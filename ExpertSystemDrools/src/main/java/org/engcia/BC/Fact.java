package org.engcia.BC;

public class Fact {
    static private int lastId = 0;
    private int id;

    public Fact() {
        Fact.lastId ++;
        this.id = lastId;
    }

    public int getId() {
        return this.id;
    }
}
