package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import server.JSONDatabase.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Session implements Runnable {
    private final ServerSocket server;
    private final Socket socket;
    private final JSONDatabase jdb;

    /**
     * @param socket accepter connection
     * @param jdb pointer to database
     * @param server pointer to ServerSocket in case of 'exit' request to terminate ServerSocket
     */
    public Session(Socket socket, JSONDatabase jdb, ServerSocket server) {
        this.socket = socket;
        this.jdb = jdb;
        this.server = server;
    }
@Override
    public void run() {
        Command command;
        DatabaseController dbController = new DatabaseController();
        try (
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            String msg = input.readUTF();
            Gson gson = new Gson();
            JsonObject request = gson.fromJson(msg, JsonObject.class);
            System.out.println("Received: " + msg);
            //String[] commands = msg.split(" ");

            switch (request.get("type").getAsString()) {
                case "set" -> command = new SetCommand(jdb, request);
                case "get" -> command = new GetCommand(jdb, request);
                case "delete" -> command = new DeleteCommand(jdb, request);
                default -> command = new ExitCommand();
            }


            dbController.setCommand(command);
            JsonObject returnJSON = dbController.executeCommand();

            output.writeUTF(returnJSON.toString());
            System.out.println("Sent: " + returnJSON);
            socket.close();
            if (request.get("type").getAsString().equals("exit")) {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}