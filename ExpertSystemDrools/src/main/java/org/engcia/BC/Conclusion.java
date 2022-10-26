package org.engcia.BC;

import org.engcia.Main;

public class Conclusion extends Fact{
    public static final String OTORRHAGIA = "Otorrhagia";
    public static final String SKULL_FRACTURE = "Skull fracture";
    public static final String EPISTAXE = "Epistaxe";
    public static final String HEMATHESE = "Hemathese";
    public static final String MOUTH_HAEMORRHAGE = "Mouth haemorrhage";
    public static final String METRORRHAGIA = "Metrorrhagia";
    public static final String HEMATURIA = "Hematuria";
    public static final String MELENA = "Melena";
    public static final String RECTAL_BLEEDING = "Rectal bleeding";
    public static final String UNKNOWN = "Consult the doctor!";

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
