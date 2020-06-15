package main;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import main.JiraModel.IssueApi.ApiObject.sprint;
import main.JiraModel.IssueApi.Issue;
import main.model.ReleaseModel;
import main.service.RestService;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.springframework.core.ParameterizedTypeReference;

import java.util.*;

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



    public static ReleaseModel  getResponse( String project,String release ,String team) {
//        System.out.println("tesm");
//System.out.println(team);

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("JiraIssue");

        MongoCollection<Document> collection = db.getCollection("issues");
        MongoCollection<Document> CapColl = db.getCollection("ReleaseCap");
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "John");
        //MongoCursor<Document> cursor = collection.find(Filters.eq("Status","open")).iterator();
        Bson compo;
        if(team!=null)
         compo= Filters.eq("Component",team);
        else
            compo=Filters.regex("Component","");

        String pattern = release+"_"+project;
        long status_open =  collection.countDocuments(and(Filters.regex("key",pattern),compo,Filters.eq("Status","open")));
        long status_closed =  collection.countDocuments(and(Filters.regex("key",pattern),compo,Filters.eq("Status","closed")));
        long status_progress =  collection.countDocuments(and(Filters.regex("key",pattern),compo,Filters.eq("Status","progress")));
        System.out.println("open");
        System.out.println(status_open);
        Document doc =  CapColl.find(Filters.eq("Release",release)).first();


        Date RstartDate = doc.getDate("R_startDate");
        Date RendDate =doc.getDate("R_endDate");
        Date CurrentDate = new Date();
        Document sprintDoc =  CapColl.find(and(Filters.eq("Release",release),Filters.lte("S_startDate",CurrentDate),
                Filters.gte("S_endDate",CurrentDate))).first();


        String sprint =   sprintDoc.getString("Sprint");
        Date  sStartDate = sprintDoc.getDate("S_startDate");
        Date sendDate = sprintDoc.getDate("S_endDate");

//
//      Double ReleaseCapacity= (Double) ( CapColl.aggregate(
//                Arrays.asList(
//                        Aggregates.match(and(Filters.eq("project", "Release board"),
//                                Filters.eq("Release", release))),
//                        Aggregates.group("MemberCapacity", Accumulators.sum("count", "$MemberCapacity"))
//                )
//        ).first()).get("count");

        AggregateIterable<Document> SprintDoc= CapColl.aggregate(
                Arrays.asList(
                        Aggregates.match(and(Filters.eq("project", "Release board"),
                                Filters.eq("Release", release),Filters.eq("Component",team),Filters.eq("Sprint", sprint)
                        )),
                        Aggregates.group("", Accumulators.sum("count", "$MemberCapacity"))
                )
        );
        Double SprintCapacity=  (SprintDoc .first()!=null)?(Double)SprintDoc .first().get("count")/8:0.0;

        AggregateIterable<Document> estimDoc =collection.aggregate(
                Arrays.asList(
                        Aggregates.match(and(Filters.regex("key", pattern),Filters.eq("Component",team),
                                Filters.eq("Sprint", sprint)
                        )),
                        Aggregates.group("", Accumulators.sum("count", "$OriginalEstimates"))
                )
        );

        Double TotalEstimate=  ( estimDoc.first()!=null)? (Double)estimDoc.first().get("count"):0.0;


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


    public static <T> void SaveObject(T obj,String collName,Class<T> classType,String filterKey,String id){

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("test");
        db = db.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<T> collection = db.getCollection(collName,classType);
        //MongoCollection<Document> collection = db.getCollection("issues");
        Gson gson = new Gson();
       Document doc= Document.parse(gson.toJson(obj));
        try {
            collection.updateOne( Filters.eq(filterKey,id),new Document("$set",doc) , (new UpdateOptions()).upsert(true));
        }
        catch (MongoException err){
            System.out.println(err);
        }

    }


    public static <T> void SaveProjectFields(List<T> lst, String collName,Class<T> classType,String key,String field){

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("test");
        db = db.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<T> collection = db.getCollection(collName, classType);
        //MongoCollection<Document> collection = db.getCollection("issues");
        for(T obj:lst) {
            try {
                collection.updateOne(Filters.eq("key", key), new Document("$push", new Document(field, obj)));
            } catch (MongoException err) {
                System.out.println(err);
            }
        }

    }

}