package main;

import main.Service.RestService;
import main.models.IssueID;
import main.models.project;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Spring {

    public static void main(String[] args) {
        SpringApplication.run(Spring.class, args);

    }

    @Scheduled(fixedDelay = 10000L)
    void someJob() throws InterruptedException{
        System.out.println("Restart");
        List<project> PrjLst= RestService.getProject();
        RestService.saveReleases(PrjLst);
        RestService.saveSprints(PrjLst);
      List<IssueID> lst = RestService.getIds();
      RestService.save(lst);

    }

}
@Configuration
@EnableScheduling
class SchedulingConfiguration {

}