package View;

import Start.Client;
import Start.Message;
import com.google.gson.*;
import jdk.nashorn.internal.runtime.JSONListAdapter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class Home extends JFrame {
    private JButton loginButton;
    private JTextField emailTF;
    private JTextField passTf;
    private JTable table1;
    private JPanel mainPanel;

    JsonObject jsonObject = new JsonObject();
    JsonParser jsonParser = new JsonParser();

    public Home()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int aux = table1.getSelectedRow();
                TableModel model2 = table1.getModel();
                JsonPrimitive primitive = (JsonPrimitive) model2.getValueAt(aux,0);
                int id = primitive.getAsInt();
                try {
                    Client.comunicateSend(new Message(String.valueOf(id), "get-article"));
                    Message retMsg = Client.comunicateRead();
                    System.out.println(retMsg.toString());
                    jsonObject = (JsonObject) jsonParser.parse(retMsg.getB());

                    String body = String.valueOf(jsonObject.get("body"));

                    System.out.println(body);
                    Client.comunicateSend(new Message(String.valueOf(id),"get-related"));
                    Message retRelated = Client.comunicateRead();
                    JsonArray jsonArray = new JsonArray();

                    jsonArray = (JsonArray) jsonParser.parse(retRelated.getB());

                    for( int i=0; i<jsonArray.size(); i++)
                        System.out.println(jsonArray.get(i).toString());

                    new ReadArticle(body,jsonArray);

                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Closed");
                try {
                    Client.exit();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void setLoginButton(ActionListener e)
    {
        this.loginButton.addActionListener(e);
    }

    public JTextField getEmailTF() {
        return emailTF;
    }

    public JTextField getPassTf() {
        return passTf;
    }

    public JTable getTable1() {
        return table1;
    }

}


