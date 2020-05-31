package com.rd.droolspoc;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class DroolsPoc {

    public void executeDrools() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rule");

        Product product = new Product();
        product.setType("gold");

        FactHandle fact1;

        fact1 = kSession.insert(product);
        kSession.fireAllRules();

        System.out.println("The discount for the jewellery product "
                + product.getType() + " is " + product.getDiscount());
    }

    public static void main(String[] args) {
        DroolsPoc droolsTest = new DroolsPoc();
        droolsTest.executeDrools();
    }
}
