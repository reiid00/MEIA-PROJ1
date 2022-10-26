/**package org.engcia.listeners;


import org.drools.core.factmodel.Fact;

import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.*;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import java.util.*;

public class TrackingAgendaListener implements AgendaEventListener {
    private static KieSession kieSession;
    private static List<Object> activations;
    private static String ruleName;
    private static double ruleCF;
    private static List<Fact> lhs = new ArrayList<>();
    private static List<Fact> rhs = new ArrayList<>();

    public TrackingAgendaListener() {
        kieSession = null;
        activations = new ArrayList<>();
        ruleName = null;
        ruleCF = 0.0;
    }

    static public KieSession getKieSession() {
        return kieSession;
    }

    static public List<Object> getActivations() {
        return activations;
    }

    static public double getRuleCF() {
        return ruleCF;
    }

    static public String getRuleName() {
        return ruleName;
    }

    static public void resetLhs(){ lhs.clear();}

    static public void resetRhs(){ rhs.clear();}



    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        // Clear activation object list to the next activation
        activations.clear();

        //add lhs
        List <Object> list = event.getMatch().getObjects();
        for (Object o : list) {
            if (o instanceof Fact) {
                lhs.add((Fact)o);
            }
        }

        for (Fact f: rhs) {
            Justification j = new Justification(ruleName, lhs, f);
            How.addJustification(f.getIdFact(), j);
        }

        resetLhs();
        resetRhs();

    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void agendaGroupPushed(AgendaGroupPushedEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        kieSession = (KieSession) event.getKieRuntime().getKieBase().getKieSessions()
                .toArray()[0];
        activations.addAll(event.getMatch().getObjects());
        Map<String, Object> metaData = event.getMatch().getRule().getMetaData();
        if (metaData.containsKey("CF")) {
            if (metaData.get("CF") instanceof Double) {
                ruleCF = ((Double) metaData.get("CF"));
            }
        }
        ruleName = event.getMatch().getRule().getName();
    }

    @Override
    public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void matchCancelled(MatchCancelledEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void matchCreated(MatchCreatedEvent event) {
        // TODO Auto-generated method stub

    }

}
**/