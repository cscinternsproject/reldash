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

        // Basic authentication

        return new HttpHeaders() {{
            String auth = "zaidjunaid3@gmail.com" + ":" + "9GLO9L8tCHVXB5HcTbc60C7F" ;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }




    public static List<projectId> getProjectIds(){

        //Requesting list of projects
       String url="https://cscinterns2020.atlassian.net/rest/api/3/project/search";
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity request = new HttpEntity(createHeaders());

        //requesting resource
        ResponseEntity<ProjectIdList>response = restTemplate.exchange(
                url, HttpMethod.GET,request,ProjectIdList.class);

        List<projectId> lst = new ArrayList<>();


        //paginated response handling
        if(response.getBody().getTotal()<=response.getBody().getMaxResults())
          lst=response.getBody().getValues();
        else
        {
            int StartAt = response.getBody().getStartAt();
            int total= response.getBody().getTotal();
            int max = response.getBody().getMaxResults();
            StartAt+=max;

            // adding further page data to the list
            while(StartAt<total)
            {
            //adding query parameter page start in paginated view
                String queryUrl = url + "?startAt="+StartAt;
              response = restTemplate.exchange(
                        queryUrl, HttpMethod.GET,request,ProjectIdList.class);
              StartAt+=max;
              lst.addAll(response.getBody().getValues());

            }
        }

        //Saving each project detail
       for(projectId obj:lst)
       {  project prj = new project();
       prj.setKey(obj.getKey());
       prj.setName(obj.getName());
         saveProject(prj);
       }
        return lst;
    }


    public static void saveProject(project obj){

        //saving project details
        obj.setReleases(new ArrayList<version>());
Db.SaveObject(obj,"ListProj",project.class,"key",obj.getKey());
    }

    public static List<IssueID> getIds(){
        String url = "https://cscinterns2020.atlassian.net/rest/agile/1.0/epic/none/issue?fields=id";
        RestTemplate restTemplate = new RestTemplate();


        //requesting resource
        HttpEntity request = new HttpEntity(createHeaders());
        ResponseEntity<IDlist>response = restTemplate.exchange(
                url, HttpMethod.GET,
                request,IDlist.class);

        //data list
        List<IssueID> lst = new ArrayList<>();

        //paginated response data handling
        if(response.getBody().getTotal()<=response.getBody().getMaxResults())
            lst=response.getBody().getIssues();
        else
        {
            int StartAt = response.getBody().getStartAt();
            int total= response.getBody().getTotal();
            int max = response.getBody().getMaxResults();

            StartAt+=max;
            while(StartAt<total)
            {   //adding query parameter startAt
                String queryUrl = url + "&startAt="+StartAt;
                response = restTemplate.exchange(
                        queryUrl, HttpMethod.GET,request,IDlist.class);
                StartAt+=max;

                //adding each page response to list
                lst.addAll(response.getBody().getIssues());

            }

        }

        return lst;
    }


    public static void SaveJira(String id){

        String url ="https://cscinterns2020.atlassian.net/rest/agile/1.0/issue/"+id;
        RestTemplate restTemplate = new RestTemplate();


        //gettong issue details
        HttpEntity request = new HttpEntity(createHeaders());
        ResponseEntity<Issue>response = restTemplate.exchange(
                url, HttpMethod.GET,
                request,Issue.class);


        Issue obj = response.getBody();

        //if issue not attached to release ignore
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

        //initializing collection object to be used by Db class
       Capacity cap =new Capacity(obj.getFields().getProject().getKey(),issue_id,issue_assignee,issue_sprint,issue_release);

       //initializing issue object to be used by Db class
        IssueModel issue = new IssueModel(key,issue_id,issue_type,issue_summ,issue_status,issue_assignee,issue_component,issue_sprint,issue_release,obj.getFields().getProject().getKey());
        Db.SaveObject(issue,"IssueList",IssueModel.class,"JiraID",issue_id);
        Db.SaveJiraCap(cap);
    }




    public static void saveReleases(String key){
       String url = "https://cscinterns2020.atlassian.net/rest/api/3/project/"+key+"/versions";
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity request = new HttpEntity(createHeaders());

        //no paginated response here
        ResponseEntity<List<version>>response = restTemplate.exchange(
                url, HttpMethod.GET,
                request,new ParameterizedTypeReference<List<version>>(){});

        Db.SaveProjectFields(response.getBody(),"ListProj",version.class,key,"releases");
    }




    public static List<board> getBoards(String key){
        String url = "https://cscinterns2020.atlassian.net/rest/agile/1.0/board?projectKeyOrId="+key;
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity request = new HttpEntity(createHeaders());

        //requesting resource
        ResponseEntity<BoardList>response = restTemplate.exchange(
                url, HttpMethod.GET,
                request, BoardList.class);

       //list of response
        List<board> lst = new ArrayList<>();


        //paginated response handling
        if(response.getBody().getTotal()<=response.getBody().getMaxResults())
            lst=response.getBody().getValues();
        else
        {
            int StartAt = response.getBody().getStartAt();
            int total= response.getBody().getTotal();
            int max = response.getBody().getMaxResults();
            StartAt+=max;
            while(StartAt<total)
            {

                String queryUrl = url + "?startAt="+StartAt;
                response = restTemplate.exchange(
                        queryUrl, HttpMethod.GET,request,BoardList.class);
                StartAt+=max;
                //adding each paginated response to list
                lst.addAll(response.getBody().getValues());

            }

        }

        return lst;
    }

    public  static void saveSprints(String boardId,String ProjectId)
    {String url ="https://cscinterns2020.atlassian.net/rest/agile/1.0/board/"+boardId+"/sprint";
        RestTemplate restTemplate = new RestTemplate();


        HttpEntity request = new HttpEntity(createHeaders());
        //request resource
        ResponseEntity<SprintList>response = restTemplate.exchange(
                url, HttpMethod.GET,
                request,SprintList.class);

        //list of objects
        List<sprint> lst = new ArrayList<>();

        //handling paginated response
        if(response.getBody().getTotal()<=response.getBody().getMaxResults())
            lst=response.getBody().getValues();
        else
        {
            int StartAt = response.getBody().getStartAt();
            int total= response.getBody().getTotal();
            int max = response.getBody().getMaxResults();
            StartAt+=max;
            while(StartAt<total)
            {

                String queryUrl = url + "?startAt="+StartAt;
                response = restTemplate.exchange(
                        queryUrl, HttpMethod.GET,request,SprintList.class);
                StartAt+=max;
                lst.addAll(response.getBody().getValues());

            }

        }


       Db.SaveProjectFields(response.getBody().getValues(),"ListProj",sprint.class,ProjectId,"Sprints");

    }





    public static ReleaseModel FireRules(ReleaseModel model){

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ReleaseModel> request = new HttpEntity(model);

        // used to post Data object to rule engine after Db extraction
        ResponseEntity<ReleaseModel> response = restTemplate.exchange(
                "http://localhost:8080//Rules", HttpMethod.POST,request,ReleaseModel.class);


        return  response.getBody();

    }


}
