import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverIp = "127.0.0.1";
        int serverPort = 5000;

        try (Socket socket = new Socket(serverIp, serverPort)) {
            System.out.println("Connected to server at " + serverIp + ":" + serverPort);

            // Output thread (sending messages)
            Thread sendThread = new Thread(() -> {
                try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     Scanner scanner = new Scanner(System.in)) {

                    while (true) {
                        System.out.print("You: ");
                        String message = scanner.nextLine();
                        out.println(message);

                        if (message.equalsIgnoreCase("stop")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error sending: " + e.getMessage());
                }
            });

            // Input thread (receiving messages)
            Thread receiveThread = new Thread(() -> {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println("\nServer: " + response);
                        System.out.print("You: ");
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });

            sendThread.start();
            receiveThread.start();

            sendThread.join();
            socket.close();
            System.out.println("Disconnected from server.");

        } catch (IOException | InterruptedException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
