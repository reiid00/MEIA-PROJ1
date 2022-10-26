package org.engcia.BaseConhecimento;

public class Evidence extends Fact{
    public static final String BLOOD_EAR = "There is blood in the ear";
    public static final String EARACHE = "Do you have earache";
    public static final String DEAFNESS = "Do you have deafness";
    public static final String CEREBROSPINAL = "Is there crebrospinal fluid spill";
    public static final String HEADACHE = "Do you have headache";
    public static final String BLOOD_NOSE = "Is there blood in the nose";
    public static final String BLOOD_MOUTH = "Is there blood in the mouth";
    public static final String BLOOD_BROWN = "Is the blood colour dark brown";
    public static final String VOMITING = "Is there vomiting";
    public static final String BLOOD_VAGINA = "Is there blood in the vagina";
    public static final String BLOOD_PENIS = "Is there blood in the penis";
    public static final String BLOOD_ANUS = "Is there blood in the anus";
    public static final String BLOOD_COFFEE = "Has the blood in the anus the apperance of coffee grounds";

    private String evidence;
    private String value;

    public Evidence(String ev, String v) {
        evidence = ev;
        value = v;
    }

    public String getEvidence() {
        return evidence;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return (evidence + " = " + value);
    }

}

