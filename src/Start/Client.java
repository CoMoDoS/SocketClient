package Start;

import View.AdminView;
import View.Home;
import View.WriterView;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import java.util.Scanner;

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
        AdminView adminView = new AdminView();
        WriterView writerView = new WriterView();
        Controller controller = new Controller(home, adminView, writerView);
    }


    public static void comunicateSend(Message message1) throws IOException
    {
        objectOutputStream.writeObject(message1);
        if (message1.getB().equals("Exit"))
            exit();

    }

    public static Message comunicateRead() throws IOException, ClassNotFoundException {
        Message toReturn;
        toReturn = (Message) objectInputStream.readObject();
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