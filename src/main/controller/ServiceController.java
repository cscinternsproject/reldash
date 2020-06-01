package main.controller;

import main.DroolsTest;
import main.model.StudList;
import main.model.Student;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ServiceController {

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public List<Student>  get(){

        return Arrays.asList(new Student("zaid","pass",3,3,"pass"),
                new Student("zaid","pass",3,3,"pass"));
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public ArrayList<Student> rules(@RequestBody StudList lst){

        DroolsTest.main(lst.getLst());

        return  lst.getLst();

    }

}
