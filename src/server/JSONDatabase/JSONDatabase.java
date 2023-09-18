package server.JSONDatabase;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class JSONDatabase {
    //HashMap<String,String> db;
    JsonObject db;

    public JSONDatabase() {
        db = new JsonObject();
    }

    @Deprecated
    public JsonElement set(String key, String value) {
        db.addProperty(key, value);
        System.out.println(db.get("Key"));
        return db.get("Key");
    }
    public JsonObject set(JsonObject request) {
        db.addProperty(request.get("key").getAsString(), request.get("value").getAsString());
        //System.out.println(db.get("Key"));
        JsonObject response = new JsonObject();
        response.addProperty("response", "OK");
        return response;
    }
    public JsonObject get(JsonObject request) {
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
        return response;
    }
    public JsonObject delete(JsonObject request) {
        System.out.println(db.remove(request.get("key").getAsString()));
        return new JsonObject();
    }
}
