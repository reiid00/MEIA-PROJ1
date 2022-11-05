package org.engcia.whynot;

import org.drools.compiler.lang.descr.PatternDescr;
import org.drools.compiler.lang.descr.RuleDescr;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RuleWM class is used to describe a rule using the rule description gathered from DRL file parsing
 */
public class RuleWM {
    /**
     * Rule name
     */
    private String ruleName;
    /**
     * Rule description gathered from DRL file parsing
     */
    private RuleDescr ruleDescr;
    /**
     * List containing the description of each condition from rule LHS
     */
    private List<PatternDescr> ruleConditions;
    /**
     * Rule RHS
     */
    private String ruleConsequence;

    /**
     * Constructor for class RuleWM used to describe rules with information gathered from DRL files
     * @param ruleName Rule Name
     * @param ruleDescr Rule description, including LHS and RHS parsed from DRL file
     */
    protected RuleWM(String ruleName, RuleDescr ruleDescr) {
        this.ruleName = ruleName;
        this.ruleDescr = ruleDescr;
        this.ruleConditions = getRuleConditions();
        this.ruleConsequence = getRuleConsequence();
    }

    /**
     * Get rule LHS
     * @return rule RHS
     */
    protected List<PatternDescr> getRuleConditions() {
        List<PatternDescr> lst = ruleDescr.getLhs().getAllPatternDescr();
        return lst;
    }

    /**
     * Get rule RHS
     * @return rule RHS
     */
    protected String getRuleConsequence() {
        String rhs = this.ruleDescr.getConsequence().toString();
        return rhs;
    }

    /**
     * Get list of conclusions of a given type included in RHS
     * @param functor object/fact type
     * @return list with conclusions of a given type included in RHS
     */
    protected List<String> getConclusionFromRhs(String functor) {
        return getAllMatches(this.getRuleConsequence(), functor + "\\(.*\\)");
    }

    /**
     * Get all substrings present in the RHS of a rule that matches "functor(*)"
     * @param text Rule RHS
     * @param regex Regular expression to be matched with text
     * @return list with the arg (*) of each functor included in the RHS
     */
    private List<String> getAllMatches(String text, String regex) {
        List<String> matches = new ArrayList<String>();
        Matcher m = Pattern.compile("(?=(" + regex + "))").matcher(text);
        while(m.find()) {
            matches.add(m.group(1));
        }
        return matches;
    }

}
