/**
 * {@link org.dei.whynot} implements a Why Not explanation module.
 * <p>
 * The classes implementing the module reside in package {@link org.dei.whynot}, which include five classes:
 * <ul>
 *     <li>{@link org.dei.whynot.DroolsWithWhyNot}</li>
 *     <li>{@link org.dei.whynot.CheckWorkingMemory}</li>
 *     <li>{@link org.dei.whynot.KnowledgeBase}</li>
 *     <li>{@link org.dei.whynot.RuleWM}</li>
 *     <li>{@link org.dei.whynot.WhyNot}</li>
 * </ul>
 * <p>
 * Class {@link org.dei.whynot.DroolsWithWhyNot} is a wrapper that creates a KieSession (which can be accessed
 * using the method {@link org.dei.whynot.DroolsWithWhyNot#getKieSession()} and implements the infrastructure
 * needed to build WhyNot explanations.
 * <p>
 * Requirements:
 * <ul>
 *      <li>DRL files must be placed in a subfolder of ProjectBaseDir/src/main/resources,
 *      complying with a Drools Maven project structure</li>
 *      <li>Classes used to define Working Memory facts must all be placed in the same package;</li>
 *      <li>The name of this package must be passed to the initialization method of {@link org.dei.whynot.DroolsWithWhyNot} class</li>
 * </ul>
 * <p>
 * Usage:
 * <ul>
 *     <li>Call static method {@link org.dei.whynot.DroolsWithWhyNot#init(String factsPackage)}:
 *     <p>
 *     this method creates a {@link org.kie.api.runtime.KieSession}, and builds the knowledge base with rules
 *     from all drl files, and auxiliary queries generated dynamically</li>
 *     <li>This static method returns a {@link org.dei.whynot.DroolsWithWhyNot} object that is used to get WhyNot explanations</li>
 *     <li>After obtaining a conclusion, an WhyNot explanation can be asked by calling the following method on the {@link org.dei.whynot.DroolsWithWhyNot} object:</li>
 *     <ul>
 *             <li>
 *                 {@link org.dei.whynot.DroolsWithWhyNot#getWhyNotExplanation(String expectedConclusion)}
 *             </li>
 *             <ul>
 *                 <li>Where expectedConclusion is a String containing the expected conclusion
 *                 ("why not expected conclusion is true?") following the format Functor(functor parameters ...),
 *                 such as "Fruit(Values.WATERMELON)"</li>
 *             </ul>
 *     </ul>
 * </ul>
 *
 * @author <a href="mailto:lef@isep.ipp.pt">Luiz Faria</a>
 */
package org.engcia.whynot;