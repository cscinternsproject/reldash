package main.service;

import main.Db;
import main.DbCollections.Capacity;
import main.DbCollections.IssueModel;
import main.DbCollections.version;
import main.ServiceModel.BoardID.BoardList;
import main.ServiceModel.SprintList;
import main.ServiceModel.BoardID.board;
import main.ServiceModel.Issues.ApiObject.sprint;
import main.ServiceModel.Issues.Issue;
import main.ServiceModel.Issues.IssueList;
import main.ServiceModel.JiraID.IDlist;
import main.ServiceModel.JiraID.IssueID;
import main.ServiceModel.ProjectId.ProjectIdList;
import main.ServiceModel.ProjectId.projectId;
import main.ReqMapModel.ReleaseModel;
import main.DbCollections.project;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class RestService {


    public static HttpHeaders createHeaders(){
        return new HttpHeaders() {{
            String auth = "zaidjunaid3@gmail.com" + ":" + "9GLO9L8tCHVXB5HcTbc60C7F" ;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }




    public static List<projectId> getProjectIds(){
       String url="https://cscinterns2020.atlassian.net/rest/api/3/project/search";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(createHeaders());
        ResponseEntity<ProjectIdList>response = restTemplate.exchange(
                url, HttpMethod.GET,request,ProjectIdList.class);
       for(projectId obj:response.getBody().getValues())
       {  project prj = new project();
       prj.setKey(obj.getKey());
       prj.setName(obj.getName());
         saveProject(prj);
       }
        return response.getBody().getValues();
    }

    public static void saveProject(project obj){
        System.out.println(obj.getName()+" "+obj.getKey());
        obj.setReleases(new ArrayList<version>());
Db.SaveObject(obj,"ListProj",project.class,"key",obj.getKey());
    }

    public static List<IssueID> getIds(){
        String url = "https://cscinterns2020.atlassian.net/rest/agile/1.0/epic/none/issue?fields=id";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(createHeaders());
        ResponseEntity<IDlist>response = restTemplate.exchange(
                url, HttpMethod.GET,
                request,IDlist.class);

        return response.getBody().getIssues();
    }


    public static void SaveJira(String id){

        String url ="https://cscinterns2020.atlassian.net/rest/agile/1.0/issue/"+id;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(createHeaders());
        ResponseEntity<Issue>response = restTemplate.exchange(
                url, HttpMethod.GET,
                request,Issue.class);

        System.out.println(id);
        Issue obj = response.getBody();
        if(obj.getFields().getFixVersions().size()==0)
            return;;
        String key = obj.getFields().getProject().getKey()+"_"+
                obj.getFields().getFixVersions().get(0).getName()+"_"+obj.getFields().getSprint().getName();

        String issue_id = obj.getJiraID();
        String issue_type = obj.getFields().getIssuetype().getName();
        String issue_summ= obj.getFields().getSummary();
        String issue_status = obj.getFields().getStatus().getName();
        String issue_assignee = obj.getFields().getAssignee().getDisplayName();
        String issue_component= obj.getFields().getComponents().get(0).getName();
        String issue_sprint = obj.getFields().getSprint().getName();
        String issue_release = obj.getFields().getFixVersions().get(0).getName();


       Capacity cap =new Capacity(obj.getFields().getProject().getKey(),issue_id,issue_assignee,issue_sprint,issue_release);
        IssueModel issue = new IssueModel(key,issue_id,issue_type,issue_summ,issue_status,issue_assignee,issue_component,issue_sprint,issue_release,obj.getFields().getProject().getKey());
        Db.SaveObject(issue,"IssueList",IssueModel.class,"JiraID",issue_id);
        Db.SaveJiraCap(cap);
    }




    public static void saveReleases(String key){
       String url = "https://cscinterns2020.atlassian.net/rest/api/3/project/"+key+"/versions";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(createHeaders());
        ResponseEntity<List<version>>response = restTemplate.exchange(
                url, HttpMethod.GET,
                request,new ParameterizedTypeReference<List<version>>(){});

      Db.SaveProjectFields(response.getBody(),"ListProj",version.class,key,"releases");
    }




    public static List<board> getBoards(String key){
        String url = "https://cscinterns2020.atlassian.net/rest/agile/1.0/board?projectKeyOrId="+key;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(createHeaders());
        ResponseEntity<BoardList>response = restTemplate.exchange(
                url, HttpMethod.GET,
                request, BoardList.class);

        return response.getBody().getValues();
    }

    public  static void saveSprints(String boardId,String ProjectId)
    {String url ="https://cscinterns2020.atlassian.net/rest/agile/1.0/board/"+boardId+"/sprint";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(createHeaders());
        ResponseEntity<SprintList>response = restTemplate.exchange(
                url, HttpMethod.GET,
                request,SprintList.class);


       System.out.println(response.getBody().getValues().getClass());
       Db.SaveProjectFields(response.getBody().getValues(),"ListProj",sprint.class,ProjectId,"Sprints");

    }





    public static ReleaseModel FireRules(ReleaseModel model){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ReleaseModel> request = new HttpEntity(model);
        ResponseEntity<ReleaseModel> response = restTemplate.exchange(
                "http://localhost:8080//Rules", HttpMethod.POST,request,ReleaseModel.class);


        return  response.getBody();

    }


}
