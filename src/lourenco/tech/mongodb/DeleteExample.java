package lourenco.tech.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DeleteExample {
    public static void main(String[] args) {

        //Connect to MongoDB
        MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase database = mongoClient.getDatabase("dbname");
        MongoCollection<Document> collection = database.getCollection("collectionname");

        collection.deleteMany(new Document());

        //Create some example data
        Document exampleDoc = new Document();
        exampleDoc.put("name","Lourenco");
        exampleDoc.put("country","USA");

        Document exampleDoc2 = new Document();
        exampleDoc2.put("name","Lourenco");
        exampleDoc2.put("country","USA");

        Document exampleDoc3 = new Document();
        exampleDoc3.put("name","Lourenco");
        exampleDoc3.put("country","USA");

        Document exampleDoc4 = new Document();
        exampleDoc4.put("name","John");
        exampleDoc4.put("country","USA");

        List<Document> multipleDocs = new ArrayList<>();
        multipleDocs.add(exampleDoc);
        multipleDocs.add(exampleDoc2);
        multipleDocs.add(exampleDoc3);
        multipleDocs.add(exampleDoc4);

        //Insert example data in collection
        collection.insertMany(multipleDocs);

        //Find all data in colllection
        FindIterable<Document> result =  collection.find();

        for(Document docFound : result){
            System.out.println("Name: " + docFound.get("name") + " - Country: " + docFound.get("country"));
        }

        //Create Document to filter data
        Document filter = new Document("name","Lourenco");

        //Delete first entry matching with Document
        DeleteResult res = collection.deleteOne(filter);
        System.out.println(res.getDeletedCount() + " users deleted");

        //Check results
        for(Document docFound : result){
            System.out.println("Name: " + docFound.get("name") + " - Country: " + docFound.get("country"));
        }

        //Delete all entries matching with Document
        DeleteResult res2 = collection.deleteMany(filter);
        System.out.println(res2.getDeletedCount() + " users deleted");

        //Check results
        for(Document docFound : result){
            System.out.println("Name: " + docFound.get("name") + " - Country: " + docFound.get("country"));
        }


    }
}
