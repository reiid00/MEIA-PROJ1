package org.engcia;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import org.drools.compiler.compiler.DrlParser;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.lang.descr.PackageDescr;
import org.drools.compiler.lang.descr.RuleDescr;
import org.engcia.BC.KnowledgeBase;
import org.engcia.Listeners.TrackingAgendaEventListener;
import org.engcia.Utils.Boostrap;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Row;
import org.kie.api.runtime.rule.ViewChangedEventListener;

import org.engcia.BC.Conclusion;
import org.engcia.BC.Justification;
import org.engcia.View.UI;
import org.kie.internal.builder.conf.LanguageLevelOption;

import org.engcia.whynot.*;

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


//            DroolsWithWhyNot drools = DroolsWithWhyNot.init("org.engcia.BC");
//            KieSession session = drools.getKieSession();
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
                   /* How how = new How(Main.justifications);
                    System.out.println(how.getHowExplanation(conclusion.getId()));*/

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

            Stack<Integer> keys = new Stack<>();
            How how = new How(Main.justifications);
            kSession.fireAllRules();

            for (int key : Main.justifications.keySet()) {
                keys.push(key);

            }
            List<Integer> id = new ArrayList<>();
            for (int js = keys.size() - 1; js >= 0; js--) {

                id.add(keys.get(js));
            }
            int selected = -1;
            while (selected != 0) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Escolha a justificação:");
                for (int i = 0; i < id.size(); i++) {
                    System.out.println((i + 1) + "-" + Main.justifications.get(id.get(i)).getConclusion());
                }
                System.out.println("0. Sair");

                selected = sc.nextInt();
                if (selected > 0) System.out.println(how.getHowExplanation(id.get(selected - 1)));
            }
            // Getting a WhyNot explanation:
//            String explanationText = drools.getWhyNotExplanation("Hypothesis( description == Finalidade, value != EDICAO_DE_VIDEO_4k_8K)");
//            System.out.println("Explanation:");
//            System.out.println(explanationText);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

