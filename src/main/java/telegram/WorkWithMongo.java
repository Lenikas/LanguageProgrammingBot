package telegram;

import all.Question;
import all.WorkWithJson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Random;


/**
 * Класс для работы с базой данных Mongo, на данный момент не используется.
 */
public class WorkWithMongo {
    private static final Question[] data_py = WorkWithJson.readJson("question.json");
    private static final Question[] data_sharp = WorkWithJson.readJson("question_sharp.json");

    public WorkWithMongo() {
        try (MongoClient mongoClient = MongoClients.create())
        {
            MongoDatabase database = mongoClient.getDatabase("telegram");
            MongoCollection py_question = database.getCollection("python");
            MongoCollection sharp_question = database.getCollection("sharp");
            createDatabase(data_py, py_question);
            createDatabase(data_sharp, sharp_question);
        }
    }

    private Document createDocument(Question question, Integer id){
        String text_question = question.getQuestion();
        String text_answer = question.getVariants().toString();
        String text_correct = question.getCorrect();
        String link = question.getLink();
        Document document = new Document();
        document.put("id", id);
        document.put("question", text_question);
        document.put("answers", text_answer);
        document.put("correct", text_correct);
        document.put("link", link);
        return document;
    }

    private void createDatabase(Question[] data, MongoCollection db){
        for (int i=0; i < data.length; i++ ) {
            Document document = createDocument(data[i], i);
            db.insertOne(document);
        }
    }

    public String getRandomQuestion(MongoCollection collection){
        Integer random = new Random().nextInt(5);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id", random);
        Object a = collection.find(searchQuery).first();
        return a.toString();
    }

    private void formatQuestion(String dataQuestion) {

    }
}
