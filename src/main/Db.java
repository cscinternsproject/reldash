package main;

import com.google.gson.Gson;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import main.model.ReleaseModel;
import main.model.Student;
import main.service.RestService;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Filters.and;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Db{


    public  static Block<Document>  printBlock = new Block<Document>() {
        @Override
        public void apply(final Document document) {
            System.out.println(document.toJson());
        }
    };



    public static ReleaseModel  getResponse( String project,String release ) {


        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("JiraIssue");

        MongoCollection<Document> collection = db.getCollection("issues");
        MongoCollection<Document> CapColl = db.getCollection("ReleaseCap");
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "John");
        //MongoCursor<Document> cursor = collection.find(Filters.eq("Status","open")).iterator();

        String pattern = release+"_"+project;
        long status_open =  collection.countDocuments(and(Filters.regex("key",pattern),Filters.eq("Status","open")));
        long status_closed =  collection.countDocuments(and(Filters.regex("key",pattern),Filters.eq("Status","closed")));
        long status_progress =  collection.countDocuments(and(Filters.regex("key",pattern),Filters.eq("Status","progress")));

        Document doc =  CapColl.find(Filters.eq("Release",release)).first();


        Date RstartDate = doc.getDate("R_startDate");
        Date RendDate =doc.getDate("R_endDate");
        Date CurrentDate = new Date();
        Document sprintDoc =  CapColl.find(and(Filters.eq("Release",release),Filters.lte("S_startDate",CurrentDate),
                Filters.gte("S_endDate",CurrentDate))).first();


        String sprint =   sprintDoc.getString("Sprint");
        Date  sStartDate = sprintDoc.getDate("S_startDate");
        Date sendDate = sprintDoc.getDate("S_endDate");


      Double ReleaseCapacity= (Double) ( CapColl.aggregate(
                Arrays.asList(
                        Aggregates.match(and(Filters.eq("project", "Release board"),
                                Filters.eq("Release", release))),
                        Aggregates.group("MemberCapacity", Accumulators.sum("count", "$MemberCapacity"))
                )
        ).first()).get("count");

        Double SprintCapacity= ((Double) ( CapColl.aggregate(
                Arrays.asList(
                        Aggregates.match(and(Filters.eq("project", "Release board"),
                                Filters.eq("Release", release),Filters.eq("Sprint", sprint)
                                )),
                        Aggregates.group("", Accumulators.sum("count", "$MemberCapacity"))
                )
        ).first()).get("count"))/8;

        Double TotalEstimate= (Double) ( collection.aggregate(
                Arrays.asList(
                        Aggregates.match(and(Filters.regex("key", pattern),
                                Filters.eq("Sprint", sprint)
                        )),
                        Aggregates.group("", Accumulators.sum("count", "$OriginalEstimates"))
                )
        ).first()).get("count");


//


        ReleaseModel model= new ReleaseModel();
        model.setData(status_open,status_progress,status_closed,SprintCapacity,TotalEstimate,RstartDate,RendDate,sprint,sStartDate,sendDate);
        model.setProject(project);
        model.setRelease(release);
        model = RestService.FireRules(model);
//         DroolsTest.main(model);
//        System.out.println(model.getColorLabel()+ " "+ model.getSprintColor());

        return model;
       // AggregationOutput output = collection.aggregate(match,group);

    //    int i = 1;
        // Getting the iterator
       // ArrayList<Student>   lst  = new ArrayList<Student>();
//        while (cursor.hasNext()) {
//     Gson gson = new Gson();
//    String lst= cursor.next().toJson();
//            System.out.println(lst);
////            lst.add(user);System.out.println(user.getName());
//            i++;
//        }


        //return lst;
    }
}