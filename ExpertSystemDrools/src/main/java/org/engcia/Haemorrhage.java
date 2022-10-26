package org.engcia;

import org.engcia.BaseConhecimento.Conclusion;
import org.engcia.BaseConhecimento.Evidences;
import org.engcia.Services.Boostrap;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.LiveQuery;
import org.kie.api.runtime.rule.Row;
import org.kie.api.runtime.rule.ViewChangedEventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class Haemorrhage {
    static final Logger LOG = LoggerFactory.getLogger(Haemorrhage.class);
    public static KieSession KS;
    public static BufferedReader BR;

    public static final void main(String[] args) throws IOException {
       /* Evidences evidences = new Evidences();
        evidences.setBloodAnus("no");
        evidences.setBloodBrown("no");
        evidences.setBloodCoffee("no");
        evidences.setBloodEar("yes");
        evidences.setBloodMouth("no");
        evidences.setBloodNose("no");
        evidences.setBloodPenis("no");
        evidences.setBloodVagina("no");
        evidences.setCerebrospinal("no");
        evidences.setDeafness("no");
        evidences.setEarAche("yes");
        evidences.setHeadAche("no");
        evidences.setVomiting("no");

        runEngine(evidences);*/
        Boostrap.loadDatabase();
        runEngine();
    }

    private static void runEngine() {
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            final KieSession kSession = kContainer.newKieSession("ksession-rules");
            // session name defined in kmodule.xml"
            Haemorrhage.KS = kSession;
            // Query listener

            ViewChangedEventListener listener = new ViewChangedEventListener() {
                @Override
                public void rowDeleted(Row row) {
                }

                @Override
                public void rowInserted(Row row) {
                    Conclusion conclusion = (Conclusion) row.get("$conclusion");
                    //System.out.println(">>>" + conclusion.toString());
                    LOG.info(">>>" + conclusion.toString());

                    // stop inference engine after as soon as got a conclusion
                    kSession.halt();

                }

                @Override
                public void rowUpdated(Row row) {
                }

            };
            LiveQuery query = kSession.openLiveQuery("Conclusions", null, listener);



            kSession.fireAllRules();
            // kSession.fireUntilHalt();

            query.close();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}
