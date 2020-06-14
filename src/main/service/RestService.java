package main.service;

import main.DbModel.IssueModel;
import main.JiraModel.ApiObject.sprint;
import main.JiraModel.Issue;
import main.JiraModel.ProjectApi.*;
import main.model.ReleaseModel;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import main.JiraModel.IssueList;
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


    public static void get(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(createHeaders());
        ResponseEntity<IssueList> response = restTemplate.exchange(
                "https://cscinterns2020.atlassian.net/rest/agile/1.0/epic/none/issue", HttpMethod.GET,
                request, IssueList.class);
System.out.println("rest servie");
      //  System.out.println(response.getBody().getIssues());
       for(Issue obj : response.getBody().getIssues())
           if(obj.getFields().getFixVersions().size()>0)
           {
               String Release = obj.getFields().getFixVersions().get(0).getName();
               String Project = obj.getFields().getProject().getName();
               String ProjectKey = obj.getFields().getProject().getKey();
               String Sprint = obj.getFields().getSprint().getName();
               String Summary = obj.getFields().getSummary();
               String IssueType = obj.getFields().getIssuetype().getName();
               int id = obj.getId();
               String Status = obj.getFields().getStatus().getName();
               String Assignee= obj.getFields().getAssignee().getDisplayName();
               String Component = obj.getFields().getComponent().getName();



               IssueModel IssueObJ = new IssueModel(ProjectKey,id,IssueType,Summary,Status,Assignee,Component,Sprint);
               System.out.println(IssueObJ.getAssignee()+" "+IssueObJ.getComponent()+" "+ IssueObJ.getSprint()+" "+IssueObJ.getStatus()+" "+IssueObJ.getSummary());
           }

//       getIssue(response.getBody().getValues());
    }


    public static List<version> getReleases(String url){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(createHeaders());
        ResponseEntity<List<version>>response = restTemplate.exchange(
                url, HttpMethod.GET,
                request,new ParameterizedTypeReference<List<version>>(){});

        return response.getBody();
    }

    public static List<sprint> getBoardSprint(String url){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(createHeaders());
        ResponseEntity<SprintList>response = restTemplate.exchange(
                url, HttpMethod.GET,
                request,SprintList.class);


        return response.getBody().getValues();
    }




    public static List<sprint> getSprints(String url){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(createHeaders());
        ResponseEntity<BoardList>response = restTemplate.exchange(
                url, HttpMethod.GET,
                request, BoardList.class);

        List<sprint> sprintList= new ArrayList<sprint>();
        for(board obj : response.getBody().getValues())
        {
            String _url ="https://cscinterns2020.atlassian.net/rest/agile/1.0/board/"+obj.getId()+"/sprint";
            List<sprint> lst = getBoardSprint(_url);
           sprintList.addAll(lst);
        }

        return sprintList;
    }



    public static void UpdateProject(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(createHeaders());
        ResponseEntity<ProjectList> response = restTemplate.exchange(
                "https://cscinterns2020.atlassian.net/rest/api/3/project/search", HttpMethod.GET,
                request, ProjectList.class);
        System.out.println("rest servie");
        //  System.out.println(response.getBody().getIssues());
        for(project obj : response.getBody().getValues())
        {
            String url = "https://cscinterns2020.atlassian.net/rest/api/3/project/"+obj.getKey()+"/versions";
            List<version> releases = getReleases(url);
            obj.setReleases(releases);

            String _url = "https://cscinterns2020.atlassian.net/rest/agile/1.0/board?projectKeyOrId="+obj.getKey();
            List<sprint> sprints = getSprints(_url);
            obj.setSprints(sprints);
            
        }

//       getIssue(response.getBody().getValues());
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
