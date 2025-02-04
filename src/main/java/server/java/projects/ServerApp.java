package server.java.projects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        System.out.println("Server started on port 80");

        while (true) {
            Socket localSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + localSocket.getRemoteSocketAddress());

            new Thread(() -> {
                try {
                    InputStream is = localSocket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);

                    String commandLine = br.readLine();
                    String command = commandLine.split(" ")[0].strip();
                    String resourcePath = commandLine.split(" ")[1].strip();
                    System.out.println("Command: " + command);
                    System.out.println("ResourcePath: " + resourcePath);

                    String host = null;
                    String line;
                    while ((line = br.readLine()) != null && !line.isBlank()) {
                        String header = line.split(":")[0].strip();
                        String value = line.substring(line.indexOf(":") + 1);
                        if (header.equalsIgnoreCase("host")) {
                            host = value.strip();
                        }
                    }

                    System.out.println("Host: " + host);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }).start();
        }
    }
}
