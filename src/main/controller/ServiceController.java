package main.controller;

import main.Db;
import main.DroolsTest;
import main.model.ReleaseModel;
import main.model.StudList;
import main.model.Student;
import main.model.request;
import main.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ServiceController {

@Autowired


    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public List<Student>  get(){

        return Arrays.asList(new Student("zaid","pass",3,3,"pass"),
                new Student("zaid","pass",3,3,"pass"));
    }

    @RequestMapping(value = "/ReleaseDashboard", method = RequestMethod.POST)
    public ReleaseModel fire_rules(@RequestBody request obj){

    return  Db.getResponse(obj.getProject(),obj.getVersion(),obj.getSprint());
//       RestService.get();
//        DroolsTest.main(lst.getLst());
//
       //return  new ArrayList<>();

    }

}
