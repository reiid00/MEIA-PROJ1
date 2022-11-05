package org.engcia.BC;

import java.util.Arrays;

public class Evidence extends Fact{
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
        String[] evidenceSplit = evidence.split("\n");
        String question = evidenceSplit[0];
        String answer = value;
        for (String possibleAnswer: evidenceSplit) {
            if(!possibleAnswer.equalsIgnoreCase(question) && possibleAnswer.substring(0,1).equalsIgnoreCase(value)){
                answer = possibleAnswer.substring(2).trim();
                break;
            }
        }

        return ("respondeu "+ answer + " à questão '" + question.trim() + "'");
    }

}

