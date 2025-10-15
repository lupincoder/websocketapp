import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Waiting for client connection...");

            try (Socket socket = serverSocket.accept()) {
                System.out.println("Accepted connection");

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String clientMessage;
                while ((clientMessage = in.readLine()) != null) {
                    System.out.println("Client said: " + clientMessage);
                    if (clientMessage.equalsIgnoreCase("stop")){
                        out.println("Connection stopped");
                        break;
                    }
                    out.println(clientMessage);
                }
                System.out.println("Connection closed");
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }
}