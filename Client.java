import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverip = "127.0.0.1";
        int serverport = 5000;

        try (Socket socket = new Socket(serverip, serverport)) {
            System.out.println("Connected to server on port " + serverport);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            String userInput;
            while (true) {
                System.out.print("You: ");
                userInput = scanner.nextLine();
                out.println(userInput);

                if (userInput.equalsIgnoreCase("stop")) {
                    break;
                }

                String response = in.readLine();
                System.out.println("Server Response: " + response);
            }
            System.out.println("Goodbye!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
