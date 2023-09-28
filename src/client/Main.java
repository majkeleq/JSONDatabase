package client;

import com.beust.jcommander.JCommander;
import com.google.gson.JsonObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {


        Args jct = new Args();
        JCommander.newBuilder()
                .addObject(jct)
                .build()
                .parse(args);
        //JsonObject request = new RequestHandler(jct).getRequest();
        JsonObject request = new JsonObject();

        request.addProperty("type", "set");
        request.addProperty("key", "[\"person1\",\"person1\"]");
        request.addProperty("value", "Elon");
        //request.addProperty("value", //"{" +
                //"      \"name\":\"Elon\"," +
                //"      \"car\":{" +
                //"         \"model\":\"Tesla Roadster\"," +
                //"         \"year\":\"2018\"" +
                //"      }," +
                //"      \"rocket\":{" +
                //"         \"name\":\"Falcon 9\"," +
                //"         \"launches\":\"87\"" +
                //"      }" +
                //"}");
        String address = "127.0.0.1";
        int port = 23456;
        try (
                Socket socket = new Socket(InetAddress.getByName(address), port);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");

            System.out.println("Sent: " + request);
            output.writeUTF(request.toString());

            String receivedMsg = input.readUTF();

            System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
