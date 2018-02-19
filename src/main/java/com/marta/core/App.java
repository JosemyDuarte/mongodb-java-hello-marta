package com.marta.core;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Date;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;

/**
 * Java + MongoDB Hello world Example
 */
public class App {
    public static void main(String[] args) {

        try {

            /**** Connect to MongoDB ****/
            // Since 2.10.0, uses MongoClient
            MongoClient mongo = new MongoClient("localhost", 27017);

            /**** Get database ****/
            // if database doesn't exists, MongoDB will create it for you
            MongoDatabase db = mongo.getDatabase("testdb");

            /**** Get collection / table from 'testdb' ****/
            // if collection doesn't exists, MongoDB will create it for you
            MongoCollection table = db.getCollection("user");

            /**** Insert ****/
            // create a document to store key and value
            Document document = new Document("name", "marta")
                    .append("age", 30)
                    .append("createdDate", new Date());

            table.insertOne(document);


            /**** Find and display ****/

            table.find(eq("name", "marta"))
                    .forEach((Block<Document>) it -> System.out.println(it.toJson()));

            /**** Update ****/
            // search document where name="marta" and update it with new values
            table.updateOne(eq("name", "marta"), new Document("$set", new Document("name", "marta-updated")));

            /**** Find and display ****/
            table.find(eq("name", "marta-updated"))
                    .forEach((Block<Document>) it -> System.out.println(it.toJson()));

            /**** Done ****/
            System.out.println("Done");

        } catch (MongoException e) {
            e.printStackTrace();
        }

    }
}
