/*
package org.engcia.whynot;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

*/
/**
 * Class CheckWorkingMemory is used to verify why a conclusion is false,
 * searching for the causes that that prevented a conclusion to be obtained
 *//*

public class CheckWorkingMemory {
    */
/**
     * Singleton object reference
     *//*

    private static CheckWorkingMemory singleton = null;

    */
/**
     * logger reference
     *//*

    private final Logger LOG = LoggerFactory.getLogger(CheckWorkingMemory.class);
    */
/**
     * Drools session
     *//*

    private final KieSession session;
    */
/**
     * Map containing queries created dynamically used to validate rule conditions;
     * key: drl condition; value: query name
     *//*

    private final Map<String,String> dynamicQueries;
    */
/**
     * Reference to a KnowledgeBase object containing a representation in memory of DRL file contents,
     * and utils' methods
     *//*

    private final KnowledgeBase kb;

    */
/**
     * Constructor for class CheckWorkingMemory responsible for defining the mechanisms used to validate rule conditions
     * @param session Drools session
     * @param kb KnowledgeBase object containing a representation of the Knowledge Base
     *//*

    private CheckWorkingMemory(KieSession session, KnowledgeBase kb) {
        this.session = session;
        this.kb = kb;
        this.dynamicQueries = kb.getDynamicQueries();
    }

    */
/**
     * Returns the singleton object
     * @return CheckWorkingMemory instance
     *//*

    public static CheckWorkingMemory getInstance() {
        if(singleton == null) {
            throw new AssertionError("You have to call init first");
        }

        return singleton;
    }

    */
/**
     * Method that ensures the creation of a unique instance of CheckWorkingMemory class
     * @param session Drools session
     * @param kb KnowledgeBase object containing a representation of the Knowledge BAse
     * @return CheckWorkingMemory singleton object
     *//*

    public synchronized static CheckWorkingMemory init(KieSession session, KnowledgeBase kb) {
        if (singleton != null)
        {
            // ensure that we only ever get the same instance when we call getInstance
            throw new AssertionError("You already initialized me");
        }

        singleton = new CheckWorkingMemory(session, kb);
        return singleton;
    }

    */
/**
     * If rule 'ruleName' was been triggered, a conclusion matching 'DRLConclusion' pattern would be achieved
     * @param ruleName Rule name of a rule that could get DRLConclusion
     * @param functor Object/fact type of the expected conclusion
     * @param DRLConclusion String with expected conclusion in drl format
     * @return Return true if a conclusion from rule's RHS would match 'DRLConclusion' pattern (condition): this
     * means that if rule ruleName had been fired, the conclusion DRLConclusion would have been achieved.
     * @throws Exception If a query to evaluate a condition is not defined
     *//*

    protected boolean conclusionFromRuleDoesNotExist(String ruleName, String functor, String DRLConclusion) throws Exception {

        // get consequence of rule 'ruleName'
        RuleWM rule = kb.getRuleByName(ruleName);
        String rhs = rule.getRuleConsequence();
        // get conclusion(s) with functor 'functor' from rhs of rule 'ruleName'
        List<String> ruleConclusions = rule.getConclusionFromRhs(functor);
        if (ruleConclusions.size() == 0) { // None of the rule conclusions has 'functor' as functor
            return false;
        }
        boolean exists = false;
        for (String conclusion: ruleConclusions) {
            // Create object from ruleConclusion
            Object fact = createObject(functor, conclusion);

            // Insert object in WM
            FactHandle fh = this.session.insert(fact);

            QueryResults q = null;
            try {
                q = session.getQueryResults(dynamicQueries.get(DRLConclusion)); // DRLConclusion
            } catch (RuntimeException e) {
                throw new Exception("Undefined query: " + DRLConclusion);
            }

            // Remove fact from WM after query check
            this.session.delete(fh);

            if (q.size() > 0) {
                exists = true;
                break;
            }
        }

        return exists;
    }

    */
/**
     * Method used to create in runtime an object representing a fact
     * @param functor Fact functor
     * @param conclusion Conclusion in DRL format (constructor format), containing fact's arguments
     * @return Created object in memory
     * @throws Exception
     *//*

    private Object createObject(String functor, String conclusion) throws Exception {
        String[] conclusionArgs = conclusion.substring(conclusion.indexOf('(')+1, conclusion.indexOf(')')).
                replaceAll("\\s+","").split(",");

        final String FACTS_PACKAGE = kb.getFactsPackage();

        Class type = Class.forName(FACTS_PACKAGE + "." + functor); // fact classes must be all in the same package

        Constructor[] constructors = type.getDeclaredConstructors();
        if (constructors.length == 0) {
            throw new Exception("Constructor not found: " + FACTS_PACKAGE + "." + functor);
        }
        Object[] arguments = new Object[constructors[0].getParameterCount()];
        Class<?>[] pTypes = constructors[0].getParameterTypes();
        Method method = null;
        try {
            for (int i = 0; i < arguments.length; i++) {
                if (pTypes[i].equals(String.class)) { // String
                    arguments[i] = conclusionArgs[i].replaceAll("\"", "");
                } else if (pTypes[i].equals(int.class) || pTypes[i].equals(float.class) || pTypes[i].equals(double.class)) {
                    method = pTypes[i].getDeclaredMethod("valueOf");
                    arguments[i] = method.invoke(conclusionArgs[i]);
                // } else if (pTypes[i].equals(Values.class)) { // Enum types
                } else if (pTypes[i].isEnum() ) { // Enum types
                    method = pTypes[i].getMethod("valueOf", String.class);
                    arguments[i] = method.invoke(null, conclusionArgs[i].substring(conclusionArgs[i].lastIndexOf('.') + 1));
                }
            }
        } catch (IllegalAccessException e) {
            throw new Exception("Forbidden method call: " + method.getName(), e);
        } catch (InvocationTargetException e) {
            throw new Exception("Invoking method by reflection: " + method.getName(), e);
        } catch (NoSuchMethodException e) {
            throw new Exception("Unknown method: " + method.getName(), e);
        }

        Object object = null;
        try {
            object = constructors[0].newInstance(arguments);
        } catch (InstantiationException e) {
            throw new Exception("Cannot instantiate class: " + constructors[0].getName(), e);
        } catch (IllegalAccessException e) {
            throw new Exception("Forbidden access to class: " + constructors[0].getName(), e);
        } catch (InvocationTargetException e) {
            throw new Exception("Invoking constructor by reflection: " + constructors[0].getName(), e);
        }

        return object;
    }

    */
/**
     * Check if a rule condition is false calling a query
     * @param drlCondition DRL condition to evaluate
     * @return True if condition is false
     *//*

    protected boolean conditionIsFalse(String drlCondition) {
        QueryResults q = session.getQueryResults( dynamicQueries.get(drlCondition) );
        return q.size() == 0;
    }

}
*/
