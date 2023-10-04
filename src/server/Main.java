package server;

import server.JSONDatabase.*;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.*;

public class Main {


    public static void main(String[] args) {
        JSONDatabase jdb = new JSONDatabase();
        String address = "127.0.0.1";

        int poolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        int port = 23456;
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            while (true) {

                try {
                    executor.submit(new Session(server.accept(), jdb, server));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        } catch (Exception e) {
            executor.shutdown();
        }

    }

}
