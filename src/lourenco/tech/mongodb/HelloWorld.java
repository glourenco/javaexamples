package lourenco.tech.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class HelloWorld {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase database = mongoClient.getDatabase("dbname");
        MongoCollection<Document> collection = database.getCollection("collectionname");

        Document exampleDoc = new Document();
        exampleDoc.put("name","Lourenco");
        exampleDoc.put("country","USA");

        Document exampleDoc2 = new Document();
        exampleDoc2.put("name","John");
        exampleDoc2.put("country","USA");

        List<Document> multipleDocs = new ArrayList<>();
        multipleDocs.add(exampleDoc);
        multipleDocs.add(exampleDoc2);

        collection.insertMany(multipleDocs);

        FindIterable<Document> result =  collection.find();

        for(Document docFound : result){
            System.out.println(docFound.toJson());
        }

        Document filter = new Document("name","Lourenco");

        FindIterable<Document> result2 =  collection.find(filter);

        for(Document docFound : result2){
            System.out.println(docFound.toJson());
        }
    }
}
