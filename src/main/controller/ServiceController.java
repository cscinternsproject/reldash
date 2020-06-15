package main.controller;

import main.Db;
import main.DroolsTest;
import main.JiraModel.JiraID.IssueID;
import main.JiraModel.ProjectApi.board;
import main.JiraModel.ProjectApi.project;
import main.JiraModel.ProjectId.projectId;
import main.model.ReleaseModel;
import main.model.request;
import main.service.RestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServiceController {




    @RequestMapping(value = "/Rules", method = RequestMethod.POST)
    public ReleaseModel  FireRules( @RequestBody ReleaseModel obj){

        DroolsTest.main(obj);
        return obj;
    }


    @RequestMapping(value = "/getJiraIds", method = RequestMethod.GET)
    public List<IssueID> getJiraIds(){
        return RestService.getIds();
    }

    @RequestMapping(value = "/{id}/save", method = RequestMethod.GET)
    public void SaveJira(@PathVariable("id") String id){
         RestService.SaveJira(id);
    }

    @RequestMapping(value = "/getProjectIds", method = RequestMethod.GET)
    public List<projectId> getProjectIds(){
        return RestService.getProjectIds();
    }

    @RequestMapping(value = "/saveProject", method = RequestMethod.POST)
    public void saveProject(@RequestBody project obj){
        RestService.saveProject(obj);
    }

    @RequestMapping(value = "/{id}/getBoards", method = RequestMethod.GET)
    public List<board> getBoards(@PathVariable("id") String id){
        System.out.println("idr");
        return RestService.getBoards(id);
    }

    @RequestMapping(value = "/{projId}/{id}/saveSprints", method = RequestMethod.GET)
    public void saveSprints(@PathVariable("id") String id,@PathVariable("projId") String key){
        System.out.println("idr");
         RestService.saveSprints(id,key);
    }

    @RequestMapping(value = "/{projId}/saveReleases", method = RequestMethod.POST)
    public void saveReleases(@PathVariable("projId") String key){
        System.out.println("idr");
        RestService.saveReleases(key);
    }

    @RequestMapping(value = "/ReleaseDashboard", method = RequestMethod.POST)
    @CrossOrigin(origins = "*")
    public ReleaseModel getRelease(@RequestBody request obj){
       // RestService.UpdateProject();
    return  Db.getResponse(obj.getProject(),obj.getVersion(),obj.getTeam());
//       RestService.get();
//        DroolsTest.main(lst.getLst());
//
       //return  new ArrayList<>();

    }

}
