package org.engcia.BC;

import org.engcia.Main;

public class Conclusion extends Fact{
    private String description;

    public Conclusion(String description) {
        this.description = description;
        Main.agendaEventListener.addRhs(this);
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return ("Conclusion: " + description);
    }

}
