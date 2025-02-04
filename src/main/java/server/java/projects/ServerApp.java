package server.java.projects;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

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

                    OutputStream os = localSocket.getOutputStream();
                    OutputStreamWriter osr = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osr);

                    if (!command.equals("GET")) {
                        String httpHeader = """
                                HTTP/1.1 405 Method Not Allowed
                                Date: %s
                                Server: Simple Java Server
                                
                                """.formatted(LocalDateTime.now());

                        bw.write(httpHeader);
                        bw.flush();

                        String httpBody = """
                                <!DOCTYPE html>
                                <html lang="en">
                                <head>
                                    <meta charset="UTF-8">
                                    <title>405</title>
                                </head>
                                <body>
                                    <h1>405</h1>
                                    <h2>HTTP Method Not Allowed</h2>
                                </body>
                                </html>
                                """;
                        bw.write(httpBody);
                        bw.flush();
                    } else if (host == null) {
                        String httpHeader = """
                                HTTP/1.1 400 Bad Request
                                Date: %s
                                Server: Simple Java Server
                                """.formatted(LocalDateTime.now());
                        bw.write(httpHeader);
                        bw.flush();
                        String httpBody = """
                                <!DOCTYPE html>
                            <html lang="en">
                            <head>
                                <meta charset="UTF-8">
                                <title>Bad Request</title>
                            </head>
                            <body>
                                <h1>400</h1>
                                <h2>Bad Request</h2>
                            </body>
                            </html>
                            """;
                        bw.write(httpBody);
                        bw.flush();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }).start();
        }
    }
}
