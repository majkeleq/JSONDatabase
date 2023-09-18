package server.JSONDatabase;

import com.google.gson.JsonObject;

public class ExitCommand implements Command{

    @Override
    public JsonObject execute() {
        JsonObject response = new JsonObject();
        response.addProperty("response", "OK");
        return response;
    }
}
