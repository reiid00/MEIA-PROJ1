package org.engcia;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.engcia.BC.Hypothesis;
import org.engcia.Listeners.TrackingAgendaEventListener;
import org.engcia.Utils.Boostrap;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.LiveQuery;
import org.kie.api.runtime.rule.Row;
import org.kie.api.runtime.rule.ViewChangedEventListener;

import org.engcia.BC.Conclusion;
import org.engcia.BC.Justification;
import org.engcia.View.UI;

public class Main {
    public static KieSession KS;
    public static TrackingAgendaEventListener agendaEventListener;
    public static Map<Integer, Justification> justifications;

    public static final void main(String[] args) throws IOException {
        UI.uiInit();
        Boostrap.loadBD();
        runEngine();
        UI.uiClose();
    }

    private static void runEngine() {
        try {
            Main.justifications = new TreeMap<Integer, Justification>();

            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            final KieSession kSession = kContainer.newKieSession("ksession-rules");
            Main.KS = kSession;
            Main.agendaEventListener = new TrackingAgendaEventListener();
            Hypothesis teste = new Hypothesis();
            teste.minBudget = 0;
            teste.maxBudget = 500;
            kSession.insert(teste);
            kSession.addEventListener(agendaEventListener);

            // Query listener
            ViewChangedEventListener listener = new ViewChangedEventListener() {
                @Override
                public void rowDeleted(Row row) {
                }

                @Override
                public void rowInserted(Row row) {
                    Conclusion conclusion = (Conclusion) row.get("$conclusion");
                    System.out.println(">>>" + conclusion.toString());

                    //System.out.println(Haemorrhage.justifications);
                    How how = new How(Main.justifications);
                    System.out.println(how.getHowExplanation(conclusion.getId()));

                    // stop inference engine after as soon as got a conclusion
                    kSession.halt();

                }

                @Override
                public void rowUpdated(Row row) {
                }

            };

            LiveQuery query = kSession.openLiveQuery("Conclusions", null, listener);

            kSession.fireAllRules();
//            kSession.fireUntilHalt();

            query.close();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

