package main;

import com.google.gson.Gson;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import main.model.Student;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Db{

    public static ArrayList<Student> getList(  ) {

        // Creating a Mongo client

        MongoClient mongoClient = new MongoClient();
//This registry is required for your Mongo document to POJO conversion
        CodecRegistry codecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase db = mongoClient.getDatabase("student").withCodecRegistry(codecRegistry);

        MongoCollection<Document> collection = db.getCollection("students");
        MongoCursor<Document> cursor = collection.find().iterator();

        int i = 1;
        // Getting the iterator
        ArrayList<Student>   lst  = new ArrayList<Student>();
        while (cursor.hasNext()) {
            Gson gson = new Gson();
            Student user= gson.fromJson(cursor.next().toJson(), Student.class);
            lst.add(user);
           // System.out.println(user.getName());
            i++;
        }
        System.out.println(i);

        return lst;
    }
}