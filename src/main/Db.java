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


        MongoClient mongoClient = new MongoClient();
        //connect to DB
        MongoDatabase db = mongoClient.getDatabase("SSR");


        //getting collection
        MongoCollection<Document> collection = db.getCollection("IssueList");
        MongoCollection<Document> CapColl = db.getCollection("CapacityList");


        Bson compo;

        //component filter
        if(team!=null)
            compo= Filters.eq("component",team);
        else
            compo=Filters.regex("component","");

        String pattern = project+"_"+release;

        //gettong open/close issue status count
        long status_open =  collection.countDocuments(and(Filters.regex("key",pattern),compo,Filters.eq("status","To Do")));
        long status_closed =  collection.countDocuments(and(Filters.regex("key",pattern),compo,Filters.eq("status","Test completed")));
        long status_progress =  collection.countDocuments(and(Filters.regex("key",pattern),compo,Filters.eq("status","In Progress")));



        Document doc =  CapColl.find(Filters.eq("release",release)).first();


        Date RstartDate = doc.getDate("rstartDate");
        Date RendDate =doc.getDate("rendDate");


        Date CurrentDate = new Date();

        //getting current active sprint by present Date
        Document sprintDoc =  CapColl.find(and(Filters.eq("release",release),Filters.lte("sstartDate",CurrentDate),
                Filters.gte("sendDate",CurrentDate))).first();

        //handling not done for cases when sprintDoc collection is null, i.e. no active sprint
        String sprint =   sprintDoc.getString("sprint");
        Date  sStartDate = sprintDoc.getDate("sstartDate");
        Date sendDate = sprintDoc.getDate("sendDate");


        //summing member capacity column
        AggregateIterable<Document> SprintDoc= CapColl.aggregate(
                Arrays.asList(
                        Aggregates.match(and(Filters.eq("project", project),
                                Filters.eq("release", release),Filters.eq("sprint", sprint)
                        )),
                        Aggregates.group("", Accumulators.sum("count", "$memberCapacity"))
                )
        );

        //dividing by default 8(work hour) ,assuming member capacity is already manually input
        Double SprintCapacity=  (SprintDoc .first()!=null)?(Double)SprintDoc .first().get("count")/8:0.0;


        //summing original estime column
        AggregateIterable<Document> estimDoc =collection.aggregate(
                Arrays.asList(
                        Aggregates.match(and(Filters.regex("key", pattern),
                                Filters.eq("sprint", sprint)
                        )),
                        Aggregates.group("", Accumulators.sum("count", "$originalEstimates"))
                )
        );

        Double TotalEstimate=  ( estimDoc.first()!=null)? (double)estimDoc.first().get("count"):0.0;


//


        ReleaseModel model= new ReleaseModel();
        //updatin model object with data required by rules
        model.setData(status_open,status_progress,status_closed,SprintCapacity,TotalEstimate,RstartDate,RendDate,sprint,sStartDate,sendDate);
        model.setProject(project);
        model.setRelease(release);

        //passing object to drools service
        model = RestService.FireRules(model);


        return model;

    }


    public static <T> void SaveObject(T obj,String collName,Class<T> classType,String filterKey,String id){

        //enables pojo json mapping ,no need to use GSON
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("SSR");
        db = db.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<T> collection = db.getCollection(collName,classType);

        try {
            //updating object, with upsert operation
            collection.updateOne( Filters.eq(filterKey,id),new Document("$set",obj) , (new UpdateOptions()).upsert(true));
        }
        catch (MongoException err){
            System.out.println(err);
        }

    }




    public static  void SaveJiraCap(Capacity obj){

        //enables pojo json mapping ,no need to use GSON
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("SSR");
        db = db.withCodecRegistry(pojoCodecRegistry);

        //getting collections
        MongoCollection<Capacity> collection = db.getCollection("CapacityList",Capacity.class);
        MongoCollection<Document> CapColl = db.getCollection("ListProj");

          //finding project document
        Document proj =  CapColl.find(Filters.eq("key",obj.getProject())).first();
        List<Document> sprints = (List<Document>) proj.get("Sprints");
        List<Document> releases = (List<Document>) proj.get("releases");


        // in project document search for current sprint dates
        for(int i=0;i<sprints.size();i++)
        {

            if(sprints.get(i).get("name").equals(obj.getSprint()))
            {
                obj.setSendDate(sprints.get(i).getDate("endDate"));
                obj.setSstartDate(sprints.get(i).getDate("startDate"));

                break;
            }
        }

        // in project document search for selected release dates
        for(int i=0;i<releases.size();i++)
        {

            if(releases.get(i).get("name").equals(obj.getRelease()))
            {   Date sdate =releases.get(i).getDate("releaseDate");
            System.out.println("dates");
            System.out.println(sdate);
                obj.setRendDate(sdate);
                obj.setRstartDate(releases.get(i).getDate("startDate"));

                break;
            }
        }


        try {
            collection.updateOne( Filters.eq("key",obj.getKey()),new Document("$set",obj) , (new UpdateOptions()).upsert(true));
        }
        catch (MongoException err){
            System.out.println(err);
        }

    }


    public static <T> void SaveProjectFields(List<T> lst, String collName,Class<T> classType,String key,String field){
        //enables pojo json mapping ,no need to use GSON
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("SSR");
        db = db.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<T> collection = db.getCollection(collName, classType);


        //for each document
        for(T obj:lst) {
            try {
                //pulling out release, sprint array from obj ,pushing it again to enable updation
                collection.updateOne(Filters.eq("key",key), new Document("$pull", new Document(field, obj)) , (new UpdateOptions()).upsert(true));
                collection.updateOne(Filters.eq("key", key), new Document("$push", new Document(field, obj)) , (new UpdateOptions()).upsert(true));
            } catch (MongoException err) {
                System.out.println(err);
            }
        }

    }


    public  static  List<ProjList> getProjectLists(){
        //enables pojo json mapping ,no need to use GSON
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("SSR");
        db = db.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<project> collection = db.getCollection("ListProj", project.class);
        FindIterable<project> iterable=collection.find();
        MongoCursor<project> cursor = iterable.iterator();
        List<ProjList> lst = new ArrayList<ProjList>();

        //get list of all projects
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
        //enables pojo json mapping ,no need to use GSON
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("SSR");
        db = db.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<project> collection = db.getCollection("ListProj", project.class);

        //get project document with specified key
        project releases=collection.find(Filters.eq("key",key)).first();


        return releases.getReleases();


    }


    public  static  List<String> getTeams(){

        //enables pojo json mapping ,no need to use GSON
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("SSR");
        db = db.withCodecRegistry(pojoCodecRegistry);
        MongoCollection<project> collection = db.getCollection("IssueList", project.class);
        //get all distinct component list from issue collection
        DistinctIterable<String> components = collection.distinct("component",String.class);

        MongoCursor<String> cursor = (MongoCursor<String>) components.iterator();
        List<String> lst = new ArrayList<String>();

        //select all components name apart from "not available"
        while(cursor.hasNext())
        {
            ProjList obj = new ProjList();
            String comp =cursor.next();

            if(comp.equals("not available"))
                continue;
            lst.add(comp);
        }

        return lst;




    }



}