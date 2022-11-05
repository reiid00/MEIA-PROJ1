package org.engcia.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import org.engcia.BC.KnowledgeBase;
import org.kie.api.runtime.ClassObjectFilter;

import org.engcia.Main;
import org.engcia.BC.Evidence;

public class UI {
    private static BufferedReader br;

    public static void uiInit() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public static void uiClose() {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean answer(String ev, String v) {
        @SuppressWarnings("unchecked")
        Collection<Evidence> evidences = (Collection<Evidence>) Main.KS.getObjects(new ClassObjectFilter(Evidence.class));
        boolean questionFound = false;
        Evidence evidence = null;
        for (Evidence e: evidences) {
            if (e.getEvidence().compareTo(ev) == 0) {
                questionFound = true;
                evidence = e;
                break;
            }
        }
        if (questionFound) {
            if (evidence.getValue().compareTo(v) == 0) {
                Main.agendaEventListener.addLhs(evidence);
                return true;
            } else {
                // Clear LHS conditions set if a condition is false (conjunctive rules)
                Main.agendaEventListener.resetLhs();
                return false;
            }
        }
        System.out.print(ev + "? ");
        String value = readLine();

        Evidence e = new Evidence(ev, value);
        Main.KS.insert(e);

        if (value.compareTo(v) == 0) {
            Main.agendaEventListener.addLhs(e);
            return true;
        } else {
            // Clear LHS conditions set if a condition is false (conjunctive rules)
            Main.agendaEventListener.resetLhs();
            return false;
        }
    }

    public static boolean compare(String evidenceValue,int min, int max){
        if(Integer.parseInt(evidenceValue) > min && Integer.parseInt(evidenceValue) <= max){
            return true;
        }else{
            return false;
        }
    }
    public static boolean answerBudget(String ev, int intMin, int intMax) {
        @SuppressWarnings("unchecked")
        Collection<Evidence> evidences = (Collection<Evidence>) Main.KS.getObjects(new ClassObjectFilter(Evidence.class));
        boolean questionFound = false;
        Evidence evidence = null;
        for (Evidence e: evidences) {
            if (e.getEvidence().compareTo(ev) == 0) {
                questionFound = true;
                evidence = e;
                break;
            }
        }
        if (questionFound) {
            if (compare(evidence.getValue(),intMin,intMax)) {
                Main.agendaEventListener.addLhs(evidence);
                return true;
            } else {
                // Clear LHS conditions set if a condition is false (conjunctive rules)
                Main.agendaEventListener.resetLhs();
                return false;
            }
        }
        System.out.print(ev + " ");
        String value = readLine();
        System.out.println(value);

        Evidence e = new Evidence(ev, value);
        Main.KS.insert(e);
        KnowledgeBase kb = (KnowledgeBase) Main.KS.getGlobal("$kb");
        kb.setMaxBudget(Integer.parseInt(value));
        if (compare(value,intMin,intMax)) {
            Main.agendaEventListener.addLhs(e);
            return true;
        } else {
            // Clear LHS conditions set if a condition is false (conjunctive rules)
            Main.agendaEventListener.resetLhs();
            return false;
        }
    }

    private static String readLine() {
        String input = "";

        try {
            input = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

}
