package org.jplugginable;

import org.junit.Test;

public class Tests {

    @Test
    public void loadingNewRule_shouldImplementRuleInterfaceDynamically() {
        RuleInterface ruleImpl = new RuleLoader().loadFromClass(TestRule.class);
        ruleImpl.run();
    }

    @Test
    public void loadingNewRule_noRunAnnotation_shouldThrowRunMethodNotFound() {
        RuleInterface ruleImpl = new RuleLoader().loadFromClass(TestRule.class);
        ruleImpl.run();
    }
}
