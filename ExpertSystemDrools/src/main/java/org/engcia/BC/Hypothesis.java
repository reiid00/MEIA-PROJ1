package org.engcia.BC;

import org.engcia.Main;

public class Hypothesis extends Fact{
    public String description; // minRam, Finality...

    public String value; // TRUE, FALSE, 5, 10...

    public String comparator; // !=,  >, <, ==

    public Hypothesis(String description, String value, String comparator) {
        this.description = description;
        this.value = value;
        this.comparator = comparator;
        Main.KS.insert(this);
        Main.agendaEventListener.addRhs(this);
    }

    public String toString() {
        return (description + " " + comparator + " " + value);
    }

}
