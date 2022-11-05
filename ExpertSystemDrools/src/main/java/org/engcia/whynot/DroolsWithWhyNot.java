/*
package org.engcia.whynot;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Singleton pattern with configurable initialization

*/
/**
 * DroolsInit class is used to create a Drools KieSession and
 * all the required assets needed to generate whynot explanations
 *//*

public class DroolsWithWhyNot {
    */
/**
     * Attribute used to ensure unique instantiation of the class
     *//*

    private static DroolsWithWhyNot singleton = null;
    */
/**
     * Reference to a singleton WhyNot object able to create explanations
     *//*

    private static WhyNot wn;
    */
/**
     * logger reference
     *//*

    private final Logger LOG = LoggerFactory.getLogger(DroolsWithWhyNot.class);
    */
/**
     * Package name including all classes used to create Drools Working Memory facts
     *//*

    private final String factsPackage;
    */
/**
     * Drools session reference
     *//*

    private KieSession session;
    */
/**
     * Reference to a KnowledgeBase object containing a representation in memory of DRL file contents,
     * and utils' methods
     *//*

    private KnowledgeBase kb;

    */
/**
     * DroolsInit constructor
     * @param factsPackage Package name including all classes used to create Drools Working Memory facts
     *//*

    private DroolsWithWhyNot(String factsPackage) {
        this.factsPackage = factsPackage;
        this.kb = new KnowledgeBase(factsPackage);
        this.session = createKieSession();
    }

    */
/**
     * Creates a singleton instance of an object containing the initialization of a Drools session and
     * the resources needed to generate whynot explanations
     * @param factsPackage package name containing all classes used to create Drools Working Memory facts
     * @return singleton instance of DroolsInit class configured with factsPackage parameter
     *
     *//*

    public synchronized static DroolsWithWhyNot init(String factsPackage) {
        if (singleton != null)
        {
            // ensure that we only ever get the same instance when we call getInstance
            throw new AssertionError("Class " +
                    DroolsWithWhyNot.class.getSimpleName() + " was already initialized.");
        }

        singleton = new DroolsWithWhyNot(factsPackage);
        wn = WhyNot.init(singleton);
        return singleton;
    }

    */
/**
     * Ask the singleton WhyNot object by a whynot explanation
     * @param expectedConclusion expected conclusion following the format Functor(args)
     * @return whynot explanation
     *//*

    public String getWhyNotExplanation(String expectedConclusion) {
        return wn.getWhyNotExplanation(expectedConclusion);
    }

    */
/**
     * Getter used to get access to the KieSession object contained in DroolsInit object
     * @return Reference to KieSession object
     *//*

    public KieSession getKieSession() {
        return session;
    }

    */
/**
     * Getter used to get access to the KnowledgeBase object contained in DroolsInit object
     * @return Reference to KnowledgeBase object
     *//*

    protected KnowledgeBase getKnowledgeBase() {
        return kb;
    }

    */
/**
     * Creates a Drools KieSession
     * @return Reference to the Drools KieSession
     *//*

    private KieSession createKieSession() {

        KieBase kieBase = kb.getKieBase();

        LOG.info("Creating kieSession");
        session = kieBase.newKieSession();
        return session;
    }
}
*/
