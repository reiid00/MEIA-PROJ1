package org.engcia.model;

public class Conclusion {
    public static final String OTORRHAGIA = "Otorrhagia";
    public static final String SKULL_FRACTURE = "Skull fracture";
    public static final String EPISTAXE = "Epistaxe";
    public static final String HEMATHESE = "Hemathese";
    public static final String MOUTH_HAEMORRHAGE = "Mouth haemorrhage";
    public static final String METRORRHAGIA = "Metrorrhagia";
    public static final String HEMATURIA = "Hematuria";
    public static final String MELENA = "Melena";
    public static final String RECTAL_BLEEDING = "Rectal bleeding";
    public static final String UNKNOWN = "Look for the the doctor!";


    private String description;

    public Conclusion(String description) {
        super();
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "Diagnosis: " + description;
    }
}
