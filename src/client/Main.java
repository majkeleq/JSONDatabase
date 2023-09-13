package client;

import com.beust.jcommander.JCommander;

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
        String message;
        switch (jct.operationType) {
            case "get", "delete" -> message = String.format("%s %d", jct.operationType, jct.index);
            case "set" -> message = String.format(("%s %d %s"), jct.operationType, jct.index, jct.message);
            case "exit" -> message = String.format("%s", jct.operationType);
            default -> message = "exit";
        }
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
            System.out.println("Sent: " + message);
            output.writeUTF(message);

            String receivedMsg = input.readUTF();

            System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
