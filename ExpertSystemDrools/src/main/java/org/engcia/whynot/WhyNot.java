package org.engcia.whynot;

import org.drools.compiler.lang.descr.BaseDescr;
import org.drools.compiler.lang.descr.PatternDescr;
import org.drools.core.util.StringUtils;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// Singleton pattern with configurable initialization

/**
 * WhyNot class implements the singleton pattern to ensure the creation of an unique instance.
 * Used to build whynot explanations in text format
 */
public class WhyNot {

    private static WhyNot singleton = null;
    /**
     * Logger reference
     */
    static final Logger LOG = LoggerFactory.getLogger(WhyNot.class);
    /**
     * Drools session reference
     */
    private final KieSession session;
    /**
     * Reference to a KnowledgeBase object containing a representation in memory of DRL file contents,
     * and utils' methods
     */
    private final org.engcia.whynot.KnowledgeBase kb;
    /**
     * Reference to a CheckWorkingMemory object providing the mechanisms to check rule conditions
     */
    private final CheckWorkingMemory chkWM;

    /**
     * Constructor for class WhyNot used to build whynot explanations
     * @param session Drools session
     * @param kb KnowledgeBase object containing a description of the Knowledge Base
     */
    private WhyNot(KieSession session, org.engcia.whynot.KnowledgeBase kb) {
        this.session = session;
        this.kb = kb;
        this.chkWM = CheckWorkingMemory.init(session, kb);
    }

    /**
     * Returns the singleton object
     * @return WhyNot instance
     */
    public static WhyNot getInstance() {
        if(singleton == null) {
            throw new AssertionError("You have to call init() first");
        }

        return singleton;
    }

    /**
     * Method that ensures the creation of a unique instance of WhyNot class
     * @param drools DroolsInit object used to create a session with the environment needed to build whynot explanations
     * @return WhyNot singleton object
     */
    public synchronized static WhyNot init(org.engcia.whynot.DroolsWithWhyNot drools) {
        if (singleton != null)
        {
            // ensure that we only ever get the same instance when we call getInstance
            throw new AssertionError("You already initialized me");
        }

        singleton = new WhyNot(drools.getKieSession(), drools.getKnowledgeBase());
        return singleton;
    }

    /**
     * Method used to obtain a whynot explanation
     * @param expectedConclusion The expected conclusion in class constructor format
     * @return The whynot explanation text
     */
    public String getWhyNotExplanation(String expectedConclusion) {
        StringBuffer explanation = new StringBuffer();

        String DRLconclusion = null;
        try {
            DRLconclusion = kb.convertConstructorToDRL(expectedConclusion);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(0);
        }

        try {
            generateExplanation(expectedConclusion, DRLconclusion, explanation,0);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(0);
        }

        return explanation.toString();
    }

    /**
     * Auxiliary recursive method to generate whynot explanations
     * @param expectedConclusion The expected conclusion according the constructor format
     * @param DRLConclusion The expected conclusion in DRL format
     * @param explanation Text explanation being formed incrementally across multiple recursive method calls
     * @param level Text explanation indentation level
     * @throws Exception
     */
    private void generateExplanation(String expectedConclusion, String DRLConclusion, StringBuffer explanation, int level) throws Exception {
        String tabs = StringUtils.repeat("\t", level * 2);

        // expectedConclusion is a basic fact
        if (kb.isBasicFact(expectedConclusion)) {
            explanation.append(tabs);
            explanation.append(DRLConclusion);
            explanation.append(" is a basic fact not defined\n");

            return;
        }

        final String functor = expectedConclusion.split("\\(")[0];
        List<String> rules = kb.getRulesObtainingConclusion(functor);

        for (String ruleName : rules) {
            org.engcia.whynot.RuleWM rule = kb.getRuleByName(ruleName);

            if (chkWM.conclusionFromRuleDoesNotExist(ruleName, functor, DRLConclusion)) {
                explanation.append(tabs);
                explanation.append(DRLConclusion);
                explanation.append(" was not concluded because rule ");
                explanation.append(ruleName);
                explanation.append(" did not fire due to:\n");

                int condNoLocal = 1;
                List<PatternDescr> conditions = rule.getRuleConditions();
                for (PatternDescr patt : conditions) {
                    String drlCondition = patt.getObjectType() + "(" + patt.getDescrs().stream().
                            map(BaseDescr::getText).reduce((s1, s2) -> s1 + " , " + s2).get() + ")";

                    if (chkWM.conditionIsFalse(drlCondition)) {
                        explanation.append(tabs).append("\t").append("Rule condition ").append(condNoLocal).append(": ").
                                append(drlCondition).append(" is false\n");
                        // convert patt to conclusion (constructor format): conc
                        String conc = kb.convertDRLPatternToConstructor(patt);
                        generateExplanation(conc, drlCondition, explanation, level + 1);
                    }
                    condNoLocal++;
                }
            }
        }
    }

}
