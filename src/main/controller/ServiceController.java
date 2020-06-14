package main.controller;

import main.Db;
import main.DroolsTest;
import main.model.ReleaseModel;
import main.model.request;
import main.service.RestService;
import org.springframework.web.bind.annotation.*;

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
        RestService.UpdateProject();
    return  Db.getResponse(obj.getProject(),obj.getVersion(),obj.getTeam());
//       RestService.get();
//        DroolsTest.main(lst.getLst());
//
       //return  new ArrayList<>();

    }

}
