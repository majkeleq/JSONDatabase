package server.JSONDatabase;

import com.google.gson.*;

public class RequestHandler {
    /**
     * Method goes through JSON database searching for JsonObject for key or last key from array of keys.
     * If it is array of keys it creates a tree if it is non-existent and return deepest object
     * If key param is not an array it returns db
     *
     * @param key key or keys from client
     * @return JsonObject for deepest key
     */

    public static JsonObject getTargetObject(JsonElement key, JsonObject db) {
        JsonObject dbLvl = db;
        try {
            //JsonArray jsonArray = JsonParser.parseString(key.getAsString()).getAsJsonArray();
            JsonArray jsonArray = key.getAsJsonArray();
            //System.out.println(key.getAsString());
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
            //System.out.println(e.getMessage());
        }
        return dbLvl;
    }

    public static String getDeepestKey(JsonElement key) {
        try {
            //JsonArray jsonArray = JsonParser.parseString(key.getAsString()).getAsJsonArray();
            JsonArray jsonArray = key.getAsJsonArray();
            return jsonArray.get(jsonArray.size() - 1).getAsString();
        } catch (Exception e) {
            //e.printStackTrace();
            return key.getAsString();
        }
    }

    public static void add(JsonObject obj, JsonElement key, JsonElement value) {
        try {
            System.out.println(value);
            JsonObject values = new Gson().fromJson(value, JsonObject.class);
            obj.add(getDeepestKey(key), values);
        } catch (Exception e) {
            obj.addProperty(getDeepestKey(key), value.getAsString());
        }
    }
}
