package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import server.JSONDatabase.*;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Database db = new Database();
        JSONDatabase jdb = new JSONDatabase();
        DatabaseController dbController = new DatabaseController();
        Command command;
        String returnMessage = "";
        String address = "127.0.0.1";
        int port = 23456;
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            while (true) {
                try (
                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    String msg = input.readUTF();
                    Gson gson = new Gson();
                    JsonObject request = gson.fromJson(msg, JsonObject.class);
                    System.out.println("Recieved: " + msg);
                    //String[] commands = msg.split(" ");

                    switch (request.get("type").getAsString()) {
                        case "set" -> command = new SetCommand(jdb, request);
                        case "get" -> command = new GetCommand(jdb, request);
                            default -> command = new ExitCommand();
                    }


                    dbController.setCommand(command);
                    JsonObject returnJSON = dbController.executeCommand();

                    output.writeUTF(returnJSON.toString());
                    System.out.println("Sent: " + returnJSON);
                    if (request.get("type").getAsString().equals("exit")) break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
