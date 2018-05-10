package Start;

import View.Home;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.naming.ldap.Control;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

// Start.Client class
public class Client {
    private static Scanner scn = new Scanner(System.in);
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;
    private static Socket s;

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {

        InetAddress ip = InetAddress.getByName("localhost");
        s = new Socket(ip, 5056);
        objectOutputStream = new ObjectOutputStream(s.getOutputStream());
        objectInputStream = new ObjectInputStream(s.getInputStream());
        Home home = new Home();

        home.showArticles();

        Controller controller = new Controller(home);


//        JsonObject jsonObject = new JsonObject();
//        JsonParser jsonParser = new JsonParser();
//        Gson gson = new Gson();
//        String a = "a@a.a";
//        String b = "b";
//        ArrayList<String> list = new ArrayList<>();
//        list.add(a);
//        list.add(b);
//        String json = gson.toJson(list);
//        System.out.println(json);
//
//        JsonArray jsonArray = (JsonArray) jsonParser.parse(json);
//        String email = String.valueOf(jsonArray.get(0));
//        String pass = String.valueOf(jsonArray.get(1) );
//        String a1 = email.replaceAll("\""," ");
//
//        System.out.println(a1 + pass);

    }


    public static int comunicateSend(Message message1) throws IOException
    {
        objectOutputStream.writeObject(message1);
        if (message1.getB().equals("Exit")) {
            exit();
            return 0;
        }
        return 1;
    }

    public static Message comunicateRead() throws IOException, ClassNotFoundException {
        Message toReturn;
        toReturn = (Message) objectInputStream.readObject();
//        System.out.println("Server-retMessage - " + toReturn.toString());
        return toReturn;
    }

    public static void exit() throws IOException {
        System.out.println("Closing this connection : " + s);
        comunicateSend(new Message("end","Exit"));

        scn.close();
        objectInputStream.close();
        objectInputStream.close();
        s.close();

        System.out.println("Connection closed");



    }
}