package main.service;

import main.DroolsTest;
import main.model.ProjDetail;
import main.model.ProjList;
import main.model.ReleaseModel;
import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;


public class RestService {


//    public static HttpHeaders createHeaders(){
////        return new HttpHeaders() {{
////            String auth = "zaidjunaid3@gmail.com" + ":" + "9GLO9L8tCHVXB5HcTbc60C7F" ;
////            byte[] encodedAuth = Base64.encodeBase64(
////                    auth.getBytes(Charset.forName("US-ASCII")) );
////            String authHeader = "Basic " + new String( encodedAuth );
////            set( "Authorization", authHeader );
////        }};
//    }


//    public static void get(){
////        RestTemplate restTemplate = new RestTemplate();
////        HttpHeaders headers = new HttpHeaders();
////        HttpEntity request = new HttpEntity(createHeaders());
////        ResponseEntity<> response = restTemplate.exchange(
////                "https://cscinterns2020.atlassian.net/rest/api/3/search?fields=key,id,project", HttpMethod.GET,
////                request, ProjDetail.class);
////
//////       for(ProjList obj : response.getBody().getValues())
//////       System.out.println(obj.getKey()+" " +obj.getId());
//////
//////       getIssue(response.getBody().getValues());
//    }



    public static ReleaseModel FireRules(ReleaseModel model){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ReleaseModel> request = new HttpEntity(model);
        ResponseEntity<ReleaseModel> response = restTemplate.exchange(
                "http://localhost:8080//Rules", HttpMethod.POST,request,ReleaseModel.class);


        return  response.getBody();

    }


}
