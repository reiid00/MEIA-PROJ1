package org.engcia;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
                    Conclusion conclusion = (Conclusion) row.get("$conclusion");
                    System.out.println(">>>" + conclusion.toString());

                    // stop inference engine after as soon as got a conclusion
                    kSession.halt();
                }

                @Override
                public void rowUpdated(Row row) {
                }

            };

            KnowledgeBase kb = new KnowledgeBase();
            kSession.setGlobal("$kb", kb);
//
//            Stack<Integer> keys = new Stack<>();
//            How how = new How(Main.justifications);
//            kSession.fireAllRules();
//
//            for (int key : Main.justifications.keySet()) {
//                keys.push(key);
//
//            }
//            List<Integer> id = new ArrayList<>();
//            for (int js = keys.size() - 1; js >= 0; js--) {
//
//                id.add(keys.get(js));
//            }
//            int selected = -1;
//            while (selected != 0) {
//                Scanner sc = new Scanner(System.in);
//                System.out.println("Escolha a justificação:");
//                for (int i = 0; i < id.size(); i++) {
//                    System.out.println((i + 1) + "-" + Main.justifications.get(id.get(i)).getConclusion());
//                }
//                System.out.println("0. Sair");
//
//                selected = sc.nextInt();
//                if (selected > 0) System.out.println(how.getHowExplanation(id.get(selected - 1)));
//            }

            // Criar json e meter no post body.
            String urlParameters  = "{}";
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = "http://localhost:5110/findBestPC";
            URL url            = new URL( request );
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/json");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write( postData );
            }
            conn.getResponseMessage();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

