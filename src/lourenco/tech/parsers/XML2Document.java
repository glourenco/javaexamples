package lourenco.tech.parsers;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XML2Document {
    List<Document> docList = new ArrayList<>();

    public static void main(String[] args) {
        XML2Document xml2Doc = new XML2Document();
        List<Document> xMLDocList = xml2Doc.objectify("C:\\tmp\\example.xml");

        MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase database = mongoClient.getDatabase("dbname");
        MongoCollection<Document> collection = database.getCollection("xmlcollection");

        collection.insertMany(xMLDocList);
        for(Document found : collection.find()){
            System.out.println(found.toJson());
        }

    }
    private List<Document> objectify(String xmlFile) {

        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document document = docBuilder.parse(new File(xmlFile));
            getChildren(document.getDocumentElement(), null);
        } catch ( IOException | ParserConfigurationException ex) {
            Logger.getLogger(XML2Document.class.getName()).log(Level.SEVERE, null, ex);
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
        return docList;
    }

    public void getChildren(Node node, String parent) {
        String newParent = node.getNodeName();
        Document nodeDoc = new Document();
        nodeDoc.put("parent", parent);
        nodeDoc.put("tagname", newParent);
        NamedNodeMap nodeAttrs = node.getAttributes();
        Document docAttrs = new Document();
        for (int j = 0; j < nodeAttrs.getLength(); j++) {
            Node attr = nodeAttrs.item(j);
            docAttrs.put(attr.getNodeName(), attr.getNodeValue());
        }
        nodeDoc.put("attributes", docAttrs);
        if (node.hasChildNodes()) {
            nodeDoc.put("value",node.getFirstChild().getNodeValue());
        }
        nodeDoc.put("date",new Date());
        docList.add(nodeDoc);
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                getChildren(currentNode, newParent);
            }
        }

    }
}
