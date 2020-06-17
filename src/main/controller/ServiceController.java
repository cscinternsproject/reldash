package main.controller;

import main.Db;
import main.DroolsTest;
import main.ServiceModel.JiraID.IssueID;
import main.JiraModel.board;
import main.DbModel.project;
import main.DbModel.version;
import main.JiraModel.ProjectId.projectId;
import main.ReqMapModel.ReleaseModel;
import main.ReqMapModel.request;
import main.service.RestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ServiceController {




    @RequestMapping(value = "/Rules", method = RequestMethod.POST)
    public ReleaseModel  FireRules( @RequestBody ReleaseModel obj){

        DroolsTest.main(obj);
        return obj;
    }



    @RequestMapping(value = "/getReleases/{id}", method = RequestMethod.GET)
    public List<version> getReleases(@PathVariable("id") String id){
        return Db.getReleases(id);
    }

    @RequestMapping(value = "/getTeams", method = RequestMethod.GET)
    public List<String> getTeams(){
        return Db.getTeams();
    }



    @RequestMapping(value = "/getJiraIds", method = RequestMethod.GET)
    public List<IssueID> getJiraIds(){
        return RestService.getIds();
    }

    @RequestMapping(value = "/{id}/saveIssue", method = RequestMethod.POST)
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

    @RequestMapping(value = "/{projId}/{id}/saveSprints", method = RequestMethod.POST)
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
