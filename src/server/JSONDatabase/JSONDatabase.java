package server.JSONDatabase;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JSONDatabase {
    //HashMap<String,String> db;
    JsonObject db;
    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock readLock = lock.readLock();
    Lock writeLock = lock.writeLock();
    //private final String FILE_PATH = ".\\src\\server\\data\\db.json"; //FOR STAGE CHECK
    private final String FILE_PATH = ".\\JSON Database (Java)\\task\\src\\server\\data\\db.json"; //FOR RUNNING

    /**
     * While starting server Database is downloaded from saved file - if the file doesn't exist it create new json object database
     */
    public JSONDatabase() {
        //db = new JsonObject();
        writeLock.lock();
        Gson gson = new Gson();
        try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
            db = gson.fromJson(reader, JsonObject.class);
        } catch (Exception e) {
            db = new JsonObject();
        }
        writeLock.unlock();
    }

    @Deprecated
    public JsonElement set(String key, String value) {
        db.addProperty(key, value);
        System.out.println(db.get("Key"));
        return db.get("Key");
    }

    /**
     * Method add some or override some 'key' and its 'value' and saves it to file
     *
     * @param request request from client
     * @return response from server
     */
    public JsonObject set(JsonObject request) {
        writeLock.lock();

        //db.add(request.get("key").getAsString(), gson.fromJson(request.get("value").getAsString(), JsonObject.class));
        //System.out.println(db.get("Key"));
        try {
            getTargetObject(request.get("key")).addProperty(getDeepestKey(request.get("key")), request.get("value").getAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObject response = new JsonObject();
        response.addProperty("response", "OK");
        writeFile();
        writeLock.unlock();
        return response;
    }

    private String getDeepestKey(JsonElement key) {
        try {
            JsonArray jsonArray = JsonParser.parseString(key.getAsString()).getAsJsonArray();
            return jsonArray.get(jsonArray.size() - 1).getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return key.toString();
        }
    }

    /**
     * Method goes through JSON database searching for JsonObject for key or last key from array of keys.
     * If it is array of keys it creates a tree if it is non-existent and return deepest object
     * If key param is not an array it return db
     * @param key key or keys from client
     * @return JsonObject for deepest key
     */
    private JsonObject getTargetObject(JsonElement key) {
        JsonObject dbLvl = db;
        try {
            JsonArray jsonArray = JsonParser.parseString(key.getAsString()).getAsJsonArray();
            //JsonArray jsonArray = key.getAsJsonArray();
            int i = 0;
            while (i != jsonArray.size() - 1) {

                if (dbLvl.getAsJsonObject(jsonArray.get(i).getAsString()) == null) {
                    dbLvl.add(jsonArray.get(i).getAsString(), new JsonObject());
                }

                dbLvl = dbLvl.getAsJsonObject(jsonArray.get(i).getAsString());
                i++;
            }
            //dbLvl.addProperty(jsonArray.get(i).toString(), request.get("value").getAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbLvl;
    }

    /**
     * Method gets key from JSONObject database. Does not read it from db.json
     *
     * @param request request from client
     * @return response from server
     */
    public JsonObject get(JsonObject request) {
        readLock.lock();
        JsonObject response = new JsonObject();
        JsonElement element = db.get(request.get("key").getAsString());
        if (element == null) {
            response.addProperty("response", "ERROR");
            response.addProperty("reason", "No such key");
        } else {
            String record = element.getAsString();
            response.addProperty("response", "OK");
            response.addProperty("value", record);
        }
        readLock.unlock();
        return response;
    }

    /**
     * Method deletes key and its value from JSONObject database and rewrite a file
     *
     * @param request request from client
     * @return response from server
     */
    public JsonObject delete(JsonObject request) {
        writeLock.lock();
        JsonObject response = new JsonObject();
        JsonElement element = db.remove(request.get("key").getAsString());
        if (element == null) {
            response.addProperty("response", "ERROR");
            response.addProperty("reason", "No such key");
        } else {
            response.addProperty("response", "OK");
        }
        writeFile();
        writeLock.unlock();
        return response;
    }

    private void writeFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(db, writer);
            //new Gson().toJson(db, writer);
        } catch (Exception e) {
            System.out.println("Error trying to save file");
            System.out.println(e.getMessage());
        }
    }
}
