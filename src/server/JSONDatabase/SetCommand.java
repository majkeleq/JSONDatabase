package server.JSONDatabase;

import com.google.gson.JsonObject;

public class SetCommand implements Command {
    private JSONDatabase db;
    private String key;
    private String value;
    private JsonObject request;

    @Deprecated
    public SetCommand(JSONDatabase db, String key, String value) {
        this.db = db;
        this.key = key;
        this.value = value;
    }

    public SetCommand(JSONDatabase db, JsonObject request) {
        this.db = db;
        this.request = request;
    }

    @Override
    public JsonObject execute() {
        return db.set(request);
    }
}
