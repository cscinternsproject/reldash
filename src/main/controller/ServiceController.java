package main.controller;

import main.Db;
import main.DroolsTest;
import main.model.ReleaseModel;
import main.model.StudList;
import main.model.Student;
import main.model.request;
import main.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ServiceController {




    @RequestMapping(value = "/Rules", method = RequestMethod.POST)
    public ReleaseModel  FireRules( @RequestBody ReleaseModel obj){

        DroolsTest.main(obj);
        return obj;
    }

    @RequestMapping(value = "/ReleaseDashboard", method = RequestMethod.POST)
    @CrossOrigin(origins = "*")
    public ReleaseModel getRelease(@RequestBody request obj){
    return  Db.getResponse(obj.getProject(),obj.getVersion());
//       RestService.get();
//        DroolsTest.main(lst.getLst());
//
       //return  new ArrayList<>();

    }

}
