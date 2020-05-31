package main;

import main.model.Student;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;



import java.util.ArrayList;

public class DroolsTest {

    public static final void main(String[] args) {
        try {
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("ksession-rule");
            Db db = new Db();
            ArrayList<Student> lst = db.getList();
            for (Student student: lst) {
                kSession.insert(student);
                kSession.fireAllRules();
                System.out.println(" Student "
                        + student.getName() + " is " + student.getEligible() + " and is " + student.getResult());
            }
//            Student student = new Student();
//            student.setScore(90);
//            student.setAttendance(90);
//
//            student.setName("Hardik");
//
////            FactHandle fact1;
////
////            fact1 = kSession.insert(student);
//            kSession.fireAllRules();



        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
