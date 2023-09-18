package server.JSONDatabase;

import com.google.gson.JsonObject;

public class DeleteCommand implements Command{
    private JSONDatabase db;
    private JsonObject request;

    public DeleteCommand(JSONDatabase db, JsonObject request) {
        this.db = db;
        this.request = request;
    }

    @Override
    public JsonObject execute() {
        return db.delete(request);
    }
}
