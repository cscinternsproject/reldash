package main;

import com.mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import main.DbCollections.Capacity;
import main.DbCollections.project;
import main.DbCollections.version;
import main.DbCollections.ProjList;
import main.ReqMapModel.ReleaseModel;
import main.service.RestService;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

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
        MongoDatabase db = mongoClient.getDatabase("SSR");

        MongoCollection<Document> collection = db.getCollection("IssueList");
        MongoCollection<Document> CapColl = db.getCollection("CapacityList");


        Bson compo;
        if(team!=null)
            compo= Filters.eq("component",team);
        else
            compo=Filters.regex("component","");

        String pattern = project+"_"+release;
        System.out.println(pattern);
        long status_open =  collection.countDocuments(and(Filters.regex("key",pattern),compo,Filters.eq("status","To Do")));
        long status_closed =  collection.countDocuments(and(Filters.regex("key",pattern),compo,Filters.eq("status","Test completed")));
        long status_progress =  collection.countDocuments(and(Filters.regex("key",pattern),compo,Filters.eq("status","In Progress")));


       // System.out.println(status_open);
        Document doc =  CapColl.find(Filters.eq("release",release)).first();


        Date RstartDate = doc.getDate("rstartDate");
        Date RendDate =doc.getDate("rendDate");
        Date CurrentDate = new Date();
        Document sprintDoc =  CapColl.find(and(Filters.eq("release",release),Filters.lte("sstartDate",CurrentDate),
                Filters.gte("sendDate",CurrentDate))).first();


        String sprint =   sprintDoc.getString("sprint");
        Date  sStartDate = sprintDoc.getDate("sstartDate");
        Date sendDate = sprintDoc.getDate("sendDate");
System.out.print(" sprint "+ sprint);
//
//      Double ReleaseCapacity= (Double) ( CapColl.aggregate(
//                Arrays.asList(
//                        Aggregates.match(and(Filters.eq("project", "Release board"),
//                                Filters.eq("Release", release))),
//                        Aggregates.group("MemberCapacity", Accumulators.sum("count", "$MemberCapacity"))
//                )
//        ).first()).get("count");

        System.out.println(project+" "+sprint+" "+release+"  gere eg");
        AggregateIterable<Document> SprintDoc= CapColl.aggregate(
                Arrays.asList(
                        Aggregates.match(and(Filters.eq("project", project),
                                Filters.eq("release", release),Filters.eq("sprint", sprint)
                        )),
                        Aggregates.group("", Accumulators.sum("count", "$memberCapacity"))
                )
        );

        Double SprintCapacity=  (SprintDoc .first()!=null)?(Double)SprintDoc .first().get("count")/8:0.0;

        AggregateIterable<Document> estimDoc =collection.aggregate(
                Arrays.asList(
                        Aggregates.match(and(Filters.regex("key", pattern),
                                Filters.eq("sprint", sprint)
                        )),
                        Aggregates.group("", Accumulators.sum("count", "$originalEstimates"))
                )
        );

        Double TotalEstimate=  ( estimDoc.first()!=null)? (double)estimDoc.first().get("count"):0.0;

          System.out.println(status_open+" "+status_closed+" "+SprintCapacity+" "+TotalEstimate+" "+RstartDate+" "+sStartDate);
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
        MongoDatabase db = mongoClient.getDatabase("SSR");
        db = db.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<T> collection = db.getCollection(collName,classType);
        //MongoCollection<Document> collection = db.getCollection("issues");

        try {
            collection.updateOne( Filters.eq(filterKey,id),new Document("$set",obj) , (new UpdateOptions()).upsert(true));
        }
        catch (MongoException err){
            System.out.println(err);
        }

    }




    public static  void SaveJiraCap(Capacity obj){

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("SSR");
        db = db.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Capacity> collection = db.getCollection("CapacityList",Capacity.class);
        MongoCollection<Document> CapColl = db.getCollection("ListProj");

      //  System.out.println(obj.getProject());
        Document proj =  CapColl.find(Filters.eq("key",obj.getProject())).first();
        List<Document> sprints = (List<Document>) proj.get("Sprints");
        List<Document> releases = (List<Document>) proj.get("releases");

  //      System.out.println("kaaam");
   //     System.out.println(obj.getSprint());
        //MongoCollection<Document> collection = db.getCollection("issues");
        for(int i=0;i<sprints.size();i++)
        {   // System.out.println(sprints.get(i).get("name"));

            if(sprints.get(i).get("name").equals(obj.getSprint()))
            {
                obj.setSendDate(sprints.get(i).getDate("endDate"));
                obj.setSstartDate(sprints.get(i).getDate("startDate"));
              // System.out.println("diif");
               // System.out.println(obj.getSstartDate());
                break;
            }
        }

        for(int i=0;i<releases.size();i++)
        {   // System.out.println(sprints.get(i).get("name"));

            if(releases.get(i).get("name").equals(obj.getRelease()))
            {   Date sdate =releases.get(i).getDate("releaseDate");
            System.out.println("dates");
            System.out.println(sdate);
                obj.setRendDate(sdate);
                obj.setRstartDate(releases.get(i).getDate("startDate"));
                // System.out.println("diif");
                // System.out.println(obj.getSstartDate());
                break;
            }
        }

       // System.out.println("obj.getSstartDate()");
       // System.out.println(obj.getSstartDate());
//        Gson gson = new Gson();
//        Document doc= Document.parse(gson.toJson(obj));
        try {
            collection.updateOne( Filters.eq("key",obj.getKey()),new Document("$set",obj) , (new UpdateOptions()).upsert(true));
        }
        catch (MongoException err){
            System.out.println(err);
        }

    }


    public static <T> void SaveProjectFields(List<T> lst, String collName,Class<T> classType,String key,String field){

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("SSR");
        db = db.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<T> collection = db.getCollection(collName, classType);
        //MongoCollection<Document> collection = db.getCollection("issues");
        for(T obj:lst) {
            try {
                collection.updateOne(Filters.eq("key",key), new Document("$pull", new Document(field, obj)) , (new UpdateOptions()).upsert(true));
                collection.updateOne(Filters.eq("key", key), new Document("$push", new Document(field, obj)) , (new UpdateOptions()).upsert(true));
            } catch (MongoException err) {
                System.out.println(err);
            }
        }

    }


    public  static  List<ProjList> getProjectLists(){

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("SSR");
        db = db.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<project> collection = db.getCollection("ListProj", project.class);
        FindIterable<project> iterable=collection.find();
        MongoCursor<project> cursor = iterable.iterator();
        List<ProjList> lst = new ArrayList<ProjList>();
        while(cursor.hasNext())
        {
            ProjList obj = new ProjList();
            project prj =cursor.next();
            obj.setName(prj.getName());
            obj.setKey(prj.getKey());
            lst.add(obj);
        }

        return lst;


    }

    public  static  List<version> getReleases(String key){

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("SSR");
        db = db.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<project> collection = db.getCollection("ListProj", project.class);
        project releases=collection.find(Filters.eq("key",key)).first();


        return releases.getReleases();


    }


    public  static  List<String> getTeams(){

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("SSR");
        db = db.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<project> collection = db.getCollection("IssueList", project.class);
        DistinctIterable<String> components = collection.distinct("component",String.class);

        MongoCursor<String> cursor = (MongoCursor<String>) components.iterator();
        List<String> lst = new ArrayList<String>();
        while(cursor.hasNext())
        {
            ProjList obj = new ProjList();
            String comp =cursor.next();
            System.out.println("component");
            System.out.println(comp);
            if(comp.equals("not available"))
                continue;
            lst.add(comp);
        }

        return lst;




    }



}