package server.JSONDatabase;

import com.google.gson.JsonObject;

public interface Command {
   JsonObject execute();
}
