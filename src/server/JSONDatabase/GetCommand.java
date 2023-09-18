package server.JSONDatabase;

import com.google.gson.JsonObject;

public class GetCommand implements Command {
    private JSONDatabase db;
    private JsonObject request;

    public GetCommand(JSONDatabase db, JsonObject request) {
        this.db = db;
        this.request = request;
    }

    @Override
    public JsonObject execute() {
        return db.get(request);
    }
}
