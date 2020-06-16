package main.Service;

import main.models.IssueID;
import main.models.board;
import main.models.project;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
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

    public static List<IssueID> getIds(){
        String url = "http://localhost:8080/getJiraIds";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity request = new HttpEntity(createHeaders());
        ResponseEntity<List<IssueID>> response = restTemplate.exchange(url,HttpMethod.GET,request,new ParameterizedTypeReference<List<IssueID>>(){});

        return response.getBody();
    }

    public static void save(List<IssueID> lst) {
        System.out.println(lst.size());
        for (IssueID obj : lst) {
            System.out.println(obj.getID());
            String key = obj.getID();
            String url = "http://localhost:8080/" + key + "/saveIssue";
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity request = new HttpEntity(createHeaders());
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, request, Void.class);
        }
    }

     public static List<project> getProject(){
            String url = "http://localhost:8080/getProjectIds";
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity request = new HttpEntity(createHeaders());
            ResponseEntity<List<project>> response = restTemplate.exchange(url,HttpMethod.GET,request,new ParameterizedTypeReference<List<project>>(){});

            return response.getBody();
        }

    public static void saveReleases(List<project> PrjLst){

        for(project obj: PrjLst)
        {
            String url = "http://localhost:8080/"+obj.getKey()+"/saveReleases";
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity request = new HttpEntity(createHeaders());
            ResponseEntity<List<project>> response = restTemplate.exchange(url,HttpMethod.POST,request,new ParameterizedTypeReference<List<project>>(){});

        }

       // return response.getBody();
    }


    public static void saveSprints(List<project> ProjLst){

        for(project obj:ProjLst)
        {
            String url = "http://localhost:8080/"+obj.getKey()+"/getBoards";
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity request = new HttpEntity(createHeaders());
            ResponseEntity<List<board>> response = restTemplate.exchange(url,HttpMethod.GET,request,new ParameterizedTypeReference<List<board>>(){});

            for(board brd : response.getBody())
            {   System.out.println(brd.getId());
                String _url = "http://localhost:8080/"+obj.getKey()+"/"+brd.getId()+"/saveSprints";
                ResponseEntity<Void> sprints = restTemplate.exchange(_url,HttpMethod.POST,request,Void.class);

            }

        }

    }


}
