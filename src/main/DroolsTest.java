package main;

import main.model.ReleaseModel;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {


    public static final void main(ReleaseModel model) {

        try {
//            System.out.println("check out");
//             System.out.println(r.getPostsPlainJSON());
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("ksession-rule");
            kSession.insert(model);
            kSession.fireAllRules();
         //   Db db = new Db();
         //   ArrayList<Student> lst = db.getList();
//            for (Student student: lst) {
//                kSession.insert(student);
//                kSession.fireAllRules();
//              //  System.out.println(" Student "
//                 //       + student.getName() + " is " + student.getEligible() + " and is " + student.getResult());
//            }
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
