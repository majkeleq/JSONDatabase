package client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;

public class RequestHandler {
    Args jct;
    private String FILE_PATH = ".\\src\\client\\data\\";

    public RequestHandler(Args jct) {
        this.jct = jct;
    }

    /**
     * Function gets starting parameters for client
     * When '-in' parameter isn't present it builds 'request' from another arguments
     * When '-in' parameter is present it gets filename and get request from file
     * @return request
     */
    public JsonObject getRequest() {
        JsonObject request = new JsonObject();
        if (jct.FILENAME == null) {
            request.addProperty("type", jct.operationType);
            switch (jct.operationType) {
                case "get", "delete" -> request.addProperty("key", jct.key);
                case "set" -> {
                    request.addProperty("key", jct.key);
                    request.addProperty("value", jct.value);
                }
            }
        } else {
            Gson gson = new Gson();
            try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH + jct.FILENAME))) {
                request = gson.fromJson(reader, JsonObject.class);
            } catch (Exception e) {
                request = new JsonObject();
            }
        }
        return request;
    }
}
