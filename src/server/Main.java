package server;

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
                    System.out.println("Recieved: " + msg);
                    String[] commands = msg.split(" ");
                    switch (commands[0]) {
                        case "get" -> command = new GetCommand(db, Integer.parseInt(commands[1]));
                        case "set" ->
                                command = new SetCommand(db, Integer.parseInt(commands[1]), Arrays.stream(commands).skip(2)
                                        .reduce("", (partialString, element) -> partialString + " " + element));
                        case "delete" -> command = new DeleteCommand(db, Integer.parseInt(commands[1]));
                        default -> command = new ExitCommand();
                    }
                    dbController.setCommand(command);

                    returnMessage = dbController.executeCommand();
                    output.writeUTF(returnMessage);
                    System.out.println("Sent: " + returnMessage);
                    if (commands[0].equals("exit")) break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
