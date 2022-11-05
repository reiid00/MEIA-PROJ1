package org.engcia.whynot;

import org.drools.compiler.compiler.DrlParser;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.lang.descr.*;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.builder.conf.LanguageLevelOption;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * KnowledgeBase class is used to represent in memory the contents of the DRL files included in the project,
 * plus a set of dynamically generated queries to evaluate rule conditions.
 */
class KnowledgeBase {
    /**
     * Package name including all classes used to create Drools Working Memory facts
     */
    private final String factsPackage;
    /**
     * logger reference
     */
    private final Logger LOG = LoggerFactory.getLogger(KnowledgeBase.class);
    /**
     * Reference to KieBase object
     */
    private KieBase kieBase;
    /**
     * List of RuleDescr objects describing each rule parsed from DRL files
     */
    private List<RuleDescr> rulesDescr;
    /**
     * Map containing queries created dynamically used to validate rule conditions;
     * key: drl condition; value: query name
     */
    private Map<String,String> dynamicQueries;
    /**
     * List of DRL paths included in the project
     */
    private final List<String> drlPaths;
    /**
     * Map containing RuleWM objects with description about each rule parsed from DRL files;
     * key: rule name; value: RuleWM reference
     */
    private Map<String,RuleWM> rules;

    /**
     * Constructor
     * @param factsPackage Package name including all classes used to create Drools Working Memory facts
     */
    protected KnowledgeBase(String factsPackage) {
        this.factsPackage = factsPackage;
        this.drlPaths = findDrlFiles();
        this.rulesDescr = getRulesDescriptionFromDRL();
        this.rules = getRulesWM();
        this.dynamicQueries = new HashMap<>();
        this.kieBase = createKieBase();
    }

    /**
     * Getter method to access factsPackage attribute
     * @return
     */
    protected String getFactsPackage() {
        return factsPackage;
    }

    /**
     * Getter method to access reference to KieBase
     * @return
     */
    protected KieBase getKieBase() {
        return this.kieBase;
    }

    /**
     * Get Map collection containing queries created dynamically - key: drl condition; value: query name
     * @return map of queries - key: drl condition; value: query name
     */
    protected Map<String,String> getDynamicQueries() {
        return dynamicQueries;
    }

    /**
     * Returns rule description from rules Map given rule name
     * @param ruleName Rule name
     * @return RuleWM from rules Map containing rule description
     */
    protected RuleWM getRuleByName(String ruleName) {
        RuleWM rule = rules.get(ruleName);
        if (rule == null) {
            throw new RuntimeException("Unknown rule");
        }
        return rule;
    }

    /**
     * Creates a KieBase with the contents from DRL files and with queries created dynamically
     * @return KieBase object
     */
    private KieBase createKieBase() {
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();
        KieFileSystem kfs = ks.newKieFileSystem();
        KieBase kieBase;
        KieContainer kContainer;

        String queriesDrl = generateQueries();

        for (String path: this.drlPaths) {
            File drlFile = new File(path);
            Resource resource = ks.getResources().newFileSystemResource(drlFile);
            kfs.write(resource); // rules
        }
        kfs.write("src/main/resources/org/dei/queries.drl", queriesDrl); // dynamic generated queries

        KieBuilder kb = ks.newKieBuilder(kfs);
        kb.buildAll();
        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
        }
        kContainer = ks.newKieContainer(kr.getDefaultReleaseId());

        LOG.info("Creating kieBase");
        kieBase = kContainer.getKieBase();

        return kieBase;

    }

    /**
     * Find paths of DRL files included in the project
     * @return List of paths
     */
    private List<String> findDrlFiles() {
        String baseDir = System.getProperty("user.dir");
        File fileDir = new File(baseDir + "/ExpertSystemDrools/src");
        ArrayList<String> lst = new ArrayList<String>();
        findFile(fileDir, lst);
        return lst;

    }

    /**
     * Recursive method to search files with drl extension
     * @param file Search base file
     * @param lst List of files found with drl extension
     */
    private void findFile(File file, List<String> lst) {
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
    private List<RuleDescr> getRulesDescriptionFromDRL() {
        String drl;
        StringBuffer drlBuffer = new StringBuffer();

        try {
            for (String path: this.drlPaths) {
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

    /**
     * Creates HashMap with RuleWM objects containing description of rules from parsed DRL files
     * @return HashMap with rules' description
     */
    private Map<String,RuleWM> getRulesWM() {
        this.rules = new HashMap<>();
        for (RuleDescr rule: rulesDescr) {
            String ruleName = rule.getName();
            this.rules.put(ruleName, new RuleWM(ruleName, rule));
        }
        return rules;
    }

    /**
     * Get all queries in drl format corresponding all conclusions present in all rules
     * @return string containing generated queries
     */
    private String generateQueries() {
        Set<String> condSet = getAllRuleConditionsList();

        Set<String> concSet = getAllRuleActionsList();
        Set<String> consSet = concSet.stream().map(c -> {
            try {
                return convertConstructorToDRL(c);
            } catch (Exception e) {
                System.out.println(e.toString());
                System.exit(0);
            }
            return null;
        }).collect(Collectors.toSet());

        condSet.addAll(consSet);

        StringBuffer drl = new StringBuffer(getImportsString() + "\n" );
        int n = 0;
        for (String c: condSet) {
            String queryName = "confirmCondition" + ++n;
            drl.append("query ").append(queryName).append("\n").append("\t").append(c).append("\n").
                    append("end").append("\n");
            dynamicQueries.put(c,queryName);
        }

        return drl.toString();
    }

    /**
     * Get all conditions present in all rules from DRL files
     * @return Set with rules' conditions
     */
    private Set<String> getAllRuleConditionsList() {
        Set<String> set = rulesDescr.stream().
                map(RuleDescr::getLhs).
                map(AndDescr::getAllPatternDescr).
                flatMap(List::stream).
                map(p -> p.getObjectType() + "(" + p.getDescrs().stream().
                        map(BaseDescr::getText).
                        reduce((s1,s2) -> s1 + " , " + s2).get() + ")").
                collect(Collectors.toSet());
        return set;
    }

    /**
     * Get all actions present in all rules from DRL files corresponding to constructor calls
     * @return Set with rules' actions (constructor calls)
     */
    private Set<String> getAllRuleActionsList() {
        Set<String> set = rulesDescr.stream().
                map(RuleDescr::getConsequence).
                map(Object::toString).
                map(this::getConstructorCalls).flatMap(Set::stream).
                collect(Collectors.toSet());
        return set;
    }

    /**
     * Get constructors' calls from a rule consequent
     * @param consequent Consequent from a RHS rule
     * @return Set of constructor calls
     */
    private Set<String> getConstructorCalls(String consequent) {
        Set<String> set = new HashSet();
        Pattern pattern = Pattern.compile("(?<=new\\s).*?(?=;)");
        Matcher matcher = pattern.matcher(consequent);
        while (matcher.find()) {
            String str = matcher.group().replaceAll(" ", "");
            set.add(str);
        }
        return set;
    }

    /**
     * Get class import directives to be included in queries file to be generated dynamically
     * @return Import directives
     */
    private String getImportsString() {
        Reflections reflections = new Reflections(this.factsPackage, new SubTypesScanner(false));
        String str1;
        String str2;
        try {
            Set<Class> setObjects = new HashSet<>(reflections.getSubTypesOf(Object.class));
            str1 = setObjects.stream().map(Class::getName).collect(Collectors.joining(";\nimport ", "import ", ";\n"));
            Set<Class> setEnums = new HashSet<>(reflections.getSubTypesOf(Enum.class));
            str2 = setEnums.stream().map(Class::getName).collect(Collectors.joining(";\nimport ", "import ", ";\n"));
        } catch (Exception e) {
            throw new RuntimeException("Facts package name incorrectly defined: " + this.factsPackage, e);
        }

        return str1 + str2;
    }

    /**
     * Convert class constructor call to drl pattern (condition)
     * @param constructor string with class constructor
     * @return drl pattern (condition)
     * @throws Exception in case of unknown class constructor or undefined class
     */
    protected String convertConstructorToDRL(String constructor) throws Exception {
        String objectType = constructor.substring(0, constructor.indexOf('(')).
                replaceAll("\\s+","");
        StringBuffer DRL = new StringBuffer(objectType);
        String[] constructorArgs = constructor.substring(constructor.indexOf('(')+1, constructor.indexOf(')')).
                replaceAll("\\s+","").split(",");
        String[] parameters = null;
        Class type = null;
        try {
            type = Class.forName(this.factsPackage + "." + objectType);
        } catch (ClassNotFoundException e) {
            throw new Exception("Unknown class: " + this.factsPackage + "." + objectType +
                    "\nCheck FACTS_PACKAGE constant: " + this.factsPackage +
                    "\nCheck conclusion functor: " + objectType, e);
        }
        parameters = getConstructorParameters(type); // fact classes must be all in the same package

        if (parameters != null) {
            if (parameters.length != constructorArgs.length) {
                throw new Exception("Invalid number of parameters in conclusion " + objectType);
            }
            Stream<String> stream = Stream.empty();
            int i=0;
            for (String par: parameters) {
                stream = Stream.concat(stream, Stream.of(par + " == " + constructorArgs[i++]));
            }
            String args = stream.reduce((s1,s2)->s1 + " , " + s2).get();

            DRL.append("(").append(args).append(")");
        }

        return DRL.toString();
    }

    /**
     * Obtain class constructor parameters through reflection
     * @param c Class
     * @return Array with parameters names
     * @throws Exception
     */
    private String[] getConstructorParameters(Class c) throws Exception {
        Constructor[] allConstructors = c.getConstructors();
        if (allConstructors.length == 0) {
            throw new Exception("Undefined constructor");
        }
        Parameter[] params = allConstructors[0].getParameters();
        return Arrays.stream(params).map(Parameter::getName).toArray(String[]::new);
    }

    /**
     * Check if a object/fact type is a basic fact (a fact that never appears in rules' RHS)
     * @param Fact object/fact type
     * @return true if Fact is a basic fact
     */
    protected boolean isBasicFact(String Fact) {
        // Search 'Fact' in rules' RHS; if none occurrence is found, return true

        // Get fact name (functor) from 'Fact':
        final String functor = Fact.replaceAll("\\s+","").split("\\(")[0];
        return !rulesDescr.stream().
                map(RuleDescr::getConsequence).
                map(c -> c.toString()).
                anyMatch(s -> s.contains(functor));
    }

    /**
     * Get list of rule names containing a given conclusion in RHS
     * @param conclusion string containing a conclusion according class constructor format
     * @return list of rule names
     */
    protected List<String> getRulesObtainingConclusion(String conclusion) {
        String regex = "(?s).*" + conclusion + "\\(" + ".*" + "\\)" + ".*";
        List<String> lst = rulesDescr.stream().
                filter(r -> r.getConsequence().toString().
                        replaceAll("\\s+","").
                        matches(regex)).
                map(RuleDescr::getName).collect(Collectors.toList());
        return lst;

    }

    /**
     * Convert drl pattern (condition) to class constructor format
     * @param patt drl pattern (condition)
     * @return condition converted to class constructor format
     * @throws Exception in case of unknown class constructor
     */
    protected String convertDRLPatternToConstructor(PatternDescr patt) throws Exception {
        final String objectType = patt.getObjectType();
        StringBuilder conclusion = new StringBuilder(objectType + "(");

        Map<String,String> map = patt.getDescrs().stream().
                map(BaseDescr::getText).
                map(s -> s.replaceAll("\\s+","")).
                collect(Collectors.toMap(k->k.split("[=:<>]{1,3}")[0],v->v.split("[=:<>]{1,3}")[1]));

        // Create constructor call as a string
        String[] parameters = null;
        Class type = Class.forName(this.factsPackage + "." + objectType); // fact classes must be all in the same package
        parameters = getConstructorParameters(type); // fact classes must be all in the same package

        if (parameters != null)
            conclusion.append(Arrays.stream(parameters).map(map::get).reduce((p1, p2) -> p1 + "," + p2).get());

        conclusion.append(")");

        return conclusion.toString();
    }

}
