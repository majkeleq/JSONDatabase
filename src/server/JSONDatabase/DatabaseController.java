package server.JSONDatabase;

import com.google.gson.JsonObject;

public class DatabaseController {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }
    public JsonObject executeCommand() {
        return command.execute();
    }
}
