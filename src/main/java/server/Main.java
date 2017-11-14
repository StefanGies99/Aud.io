package server;

import aud.io.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.client.*;
import javafx.application.Application;
import javafx.stage.Stage;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.mongojack.JacksonDBCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Main extends Application {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private CodecRegistry pojoCodecRegistry;
    private DBCollection collection;
    private List<Song> songList;
    private String mongoIp = "37.139.5.90";
    private int port = 27017;

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public void start(Stage primaryStage) throws Exception {
        pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        songList = new ArrayList<>();
        //initDatabase();
        getSongs();
    }

    private void initDatabase() {
        try {
            mongoClient = new MongoClient("37.139.5.90", 27017);
            database = mongoClient.getDatabase("music").withCodecRegistry(pojoCodecRegistry);
            insertDatabase();
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    private void insertDatabase() {
        try {
            Song song = new Song(1211, "Hallo", "locatie", "rap", "album1", "boef");
            MongoCollection<Song> mongoCollection = database.getCollection("audio", Song.class);
            mongoCollection.insertOne(song);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }

    }

    private List<Song> getSongs() {
        MongoClient mongoClient = null;
        JacksonDBCollection<Song, String> items = null;
        try {
            mongoClient = new MongoClient(new ServerAddress(mongoIp, port));
            MongoDatabase db = mongoClient.getDatabase("music").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Song> mongoCollection = db.getCollection("audio", Song.class);

            items = JacksonDBCollection.wrap((DBCollection) mongoCollection, Song.class, String.class);
            LOGGER.log(Level.INFO, items.toString());
            return (List<Song>) items;
        } finally {
            if(mongoClient != null) mongoClient.close();
        }
    }
}
