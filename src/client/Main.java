package client;

import com.beust.jcommander.JCommander;
import com.google.gson.JsonObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.SimpleTimeZone;

public class Main {

    public static void main(String[] args) {


        Args jct = new Args();
        JCommander.newBuilder()
                .addObject(jct)
                .build()
                .parse(args);
        JsonObject request = new JsonObject();
        request.addProperty("type", jct.operationType);
        switch (jct.operationType) {
            case "get", "delete" -> request.addProperty("key", jct.key);
            case "set" -> {
                request.addProperty("key", jct.key);
                request.addProperty("value", jct.value);
            }
        }

        //request.addProperty("type", "get");
        //request.addProperty("key", "1");
        //request.addProperty("value", "1 value");
        String address = "127.0.0.1";
        int port = 23456;
        try (
                Socket socket = new Socket(InetAddress.getByName(address), port);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        ) {
            System.out.println("Client started!");
            //Scanner sc = new Scanner(System.in);
            //String msg = sc.nextLine();
            //String msg = "12";
            System.out.println("Sent: " + request.toString());
            output.writeUTF(request.toString());

            String receivedMsg = input.readUTF();

            System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
