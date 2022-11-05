package org.engcia;

import java.io.IOException;
import java.util.*;

import org.engcia.BC.KnowledgeBase;
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

            kSession.addEventListener(agendaEventListener);

            // Query listener
            ViewChangedEventListener listener = new ViewChangedEventListener() {
                @Override
                public void rowDeleted(Row row) {
                }

                @Override
                public void rowInserted(Row row) {
                    System.out.println("insert!!!");
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
                    System.out.println("ENTREI NO UPDATE!!!");
                }

            };

//            LiveQuery query = kSession.openLiveQuery("Conclusions", null, listener);

            KnowledgeBase kb = new KnowledgeBase();
            kSession.setGlobal("$kb", kb);


//          kSession.fireUntilHalt();

//            query.close();

            Stack<Integer> keys= new Stack<>();
            How how = new How(Main.justifications);
            kSession.fireAllRules();

            for ( int key : Main.justifications.keySet() ) {
                keys.push(key);

                System.out.println( key );
            }
           List<Integer> id = new ArrayList<>();
            for (int js= keys.size()-1;js>=0;js--){

                id.add(keys.get(js));
            }
            int selected = -1;
            while (selected!=0)
            {
                Scanner sc = new Scanner(System.in);
                System.out.println("Escolha a justificação!");
                for (int i = 0; i < id.size(); i++) {
                    System.out.println(i +  "-" + Main.justifications.get(id.get(i)).getConclusion());
                }
                System.out.println("0. Sair");
                selected = sc.nextInt();
                System.out.println(how.getHowExplanation(id.get(selected)));
            }


            System.out.println( Main.justifications.size());


        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

