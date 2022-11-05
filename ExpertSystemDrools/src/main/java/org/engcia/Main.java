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

public class Main {
    public static KieSession KS;
    public static TrackingAgendaEventListener agendaEventListener;
    public static Map<Integer, Justification> justifications;
    private static final List<String> drlPaths=findDrlFiles();
    public static final void main(String[] args) throws IOException {
        UI.uiInit();
        Boostrap.loadBD();
        runEngine();
        UI.uiClose();
    }

    private static void runEngine() {
        try {
            Main.justifications = new TreeMap<Integer, Justification>();

            //DroolsWithWhyNot drools = DroolsWithWhyNot.init("org.dei.facts");
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

            Stack<Integer> keys= new Stack<>();
            How how = new How(Main.justifications);
            kSession.fireAllRules();

            for ( int key : Main.justifications.keySet() ) {
                keys.push(key);

            }
           List<Integer> id = new ArrayList<>();
            for (int js= keys.size()-1;js>=0;js--){

                id.add(keys.get(js));
            }
            int selected = -1;
            while (selected!=0)
            {
                Scanner sc = new Scanner(System.in);
                System.out.println("Escolha a justificação:");
                for (int i = 0; i < id.size(); i++) {
                    System.out.println((i+1) +  "-" + Main.justifications.get(id.get(i)).getConclusion());
                }
                System.out.println("0. Sair");

                selected = sc.nextInt();
                if(selected>0) System.out.println(how.getHowExplanation(id.get(selected-1)));
            }
        /*    // Getting a WhyNot explanation:
            String explanationText = drools.getWhyNotExplanation("Fruit(Values.WATERMELON)");
            System.out.println("Explanation:");
            System.out.println(explanationText);*/
            List<RuleDescr>  ob=getRulesDescriptionFromDRL();
            System.out.println(ob.size());


        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    private static List<String> findDrlFiles() {
        String baseDir = System.getProperty("user.dir");
        File fileDir = new File(baseDir + "/src");
        ArrayList<String> lst = new ArrayList<String>();
        findFile(fileDir, lst);
        return lst;

    }
    /**
     * Recursive method to search files with drl extension
     * @param file Search base file
     * @param lst List of files found with drl extension
     */
    private static void findFile(File file, List<String> lst) {
        final String name = "drl";
        File[] list = file.listFiles();
        if(list!=null)
            for (File fil : list) {
                if (fil.isDirectory()) {
                    findFile(fil, lst);
                }
                else if (fil.getName().endsWith(name.toLowerCase())) {
                    lst.add(fil.getParentFile() + "/" + fil.getName());
                }
            }
    }

    /**
     * Get List containing rules description
     * @return list containing rules description
     */
    private static List<RuleDescr> getRulesDescriptionFromDRL() {
        String drl;
        StringBuffer drlBuffer = new StringBuffer();

        try {
            for (String path: Main.drlPaths) {
                drlBuffer.append(new String(Files.readAllBytes(Paths.get(path)), Charset.defaultCharset()));
            }
            drl = drlBuffer.toString();
        } catch (IOException e) {
            throw new RuntimeException("File not found", e);
        }

        DrlParser parser = new DrlParser(LanguageLevelOption.DRL6);
        PackageDescr pkgDescr;
        try {
            pkgDescr = parser.parse(null, drl);
        } catch (DroolsParserException e) {
            throw new RuntimeException("DRL parse error", e);
        } catch (NullPointerException e) {
            throw new RuntimeException("Path incorrectly defined: ", e);
        }

        if (pkgDescr == null) {
            throw new RuntimeException("Path incorrectly defined");
        }
        return pkgDescr.getRules();
    }
}

