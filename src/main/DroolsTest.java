package main;

import main.ReqMapModel.ReleaseModel;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {


    public static final void main(ReleaseModel model) {

        try {

            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();

            //two types of session in drools ,stateful session here
            KieSession kSession = kContainer.newKieSession("ksession-rule");

            //inserting obj in drools memory
            kSession.insert(model);

            //rules fired
            kSession.fireAllRules();




        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
