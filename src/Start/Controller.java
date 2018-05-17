package Start;

import View.AdminView;
import View.Home;
import View.WriterView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.scripts.JO;
import sun.applet.resources.MsgAppletViewer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class Controller extends Observ
{
    private Home home;
    private AdminView adminView;
    private WriterView writerView;

    Subject subject;

    private List<Observer> observers = new ArrayList<Observer>();

    private static JsonObject jsonObject = new JsonObject();
    private static JsonParser jsonParser = new JsonParser();

    public Controller(Home home, AdminView adminView, WriterView writerView, Subject subject) throws IOException, ClassNotFoundException {
        this.subject = subject;
        this.subject.attach(this);

        this.home = home;
        this.home.setVisible(true);
        this.home.setLoginButton(new ButtonLogin());

        this.adminView = adminView;
        this.adminView.setCreateButton(new ButtonCreateWriter());

        this.writerView = writerView;
        this.writerView.setCreateButton(new ButtonCreateArticle());
        this.writerView.setUpdateButton(new ButtonUpdateArticle());
        this.writerView.setDeleteButton(new ButtonDeleteArticle());
        this.writerView.setAddRelatedButton(new AddRelatedArticle());

        showArticles(home.getTable1());


    }


    @Override
    public void update() {
        JTable table1 = home.getTable1();
        JTable table2 = writerView.getTable1();
        try {
            showArticles(table1);
            showArticles(table2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private class ButtonLogin implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = home.getEmailTF().getText();
            String pass = home.getPassTf().getText();

            Gson gson = new Gson();
            ArrayList<String> list = new ArrayList<>();
            list.add(email);
            list.add(pass);
            String json = gson.toJson(list);
            System.out.println(json);

            try {
                Client.comunicateSend(new Message(json,"login-writer"));
                Message retMsg = Client.comunicateRead();
                System.out.println(retMsg.getA() + " " + retMsg.getB());
                if( retMsg.getB().compareTo("ok") == 0 && retMsg.getA().compareTo("admin") == 0 ) {
                    adminView.setVisible(true);
                    JOptionPane.showMessageDialog(null, "Login successfully");
                }
                if( retMsg.getB().compareTo("ok") == 0 && retMsg.getA().compareTo("writer") == 0 ) {
                    writerView.setVisible(true);

                    JTable table1 = writerView.getTable1();
                    showArticles(table1);
                    JOptionPane.showMessageDialog(null, "Login successfully");
                }
                if ( retMsg.getB().compareTo("no") == 0)
                    JOptionPane.showMessageDialog(null, "Wrong credentials");

            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class ButtonCreateWriter implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = adminView.getNameTF().getText();
            String email = adminView.getEmailTF().getText();
            String pass = adminView.getPassTF().getText();
            String stat = adminView.getStatusTF().getText();

            Gson gson = new Gson();
            ArrayList<String> list = new ArrayList<String>();
            list.add(name);
            list.add(email);
            list.add(pass);
            list.add(stat);

            String json = gson.toJson(list);

            try {
                Client.comunicateSend(new Message(json,"create-writer"));
                Message msg = Client.comunicateRead();
                if( msg.getB().compareTo("ok") == 0 )
                    JOptionPane.showMessageDialog(null,"Writer created successfully");
                if ( msg.getB().compareTo("error") == 0)
                    JOptionPane.showMessageDialog(null,"Error!");

            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class ButtonCreateArticle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = writerView.getTitleTF().getText();
            String abs = writerView.getAbsTF().getText();
            String aut = writerView.getAuthorTF().getText();
            String body = writerView.getBodyTA().getText();

            Gson gson = new Gson();
            ArrayList<String> list = new ArrayList<String>();
            list.add(title);
            list.add(abs);
            list.add(aut);
            list.add(body);

            String json = gson.toJson(list);

            try
            {
                Client.comunicateSend(new Message(json,"create-article"));
                Message msg = Client.comunicateRead();
                if( msg.getB().compareTo("ok") == 0 ) {
//                    writerView.showArticles();
//                    JTable table1 = writerView.getTable1();
                    update();
//                    showArticles(table1);
                    JOptionPane.showMessageDialog(null, "Article created successfully");
                }
                if ( msg.getB().compareTo("error") == 0)
                    JOptionPane.showMessageDialog(null,"Error!");

            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }

        }
    }

    private class ButtonUpdateArticle  implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = writerView.getTitleTF().getText();
            String abs = writerView.getAbsTF().getText();
            String aut = writerView.getAuthorTF().getText();
            String body = writerView.getBodyTA().getText();
            String id = writerView.getIdTF().getText();

            Gson gson = new Gson();
            ArrayList<String> list = new ArrayList<String>();
            list.add(id);
            list.add(title);
            list.add(abs);
            list.add(aut);
            list.add(body);

            String json = gson.toJson(list);
            System.out.println("update   :  " + json);
            try
            {
                Message msgTo = new Message(json,"update-article");
                System.out.println(msgTo.toString());
                Client.comunicateSend(msgTo);
                Message msg = Client.comunicateRead();
                System.out.println(msg.toString());
                if( msg.getB().compareTo("ok") == 0 ) {
//                    writerView.showArticles();
                    update();
//                    JTable table1 = writerView.getTable1();
//                    showArticles(table1);
////                    home.showArticles();
//                    JTable table2 = home.getTable1();
////                    home.showArticles();
//                    showArticles(table2);
                    JOptionPane.showMessageDialog(null, "Article updated successfully");
                }
                if ( msg.getB().compareTo("error") == 0)
                    JOptionPane.showMessageDialog(null,"Error!");
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class ButtonDeleteArticle  implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String id = writerView.getIdTF().getText();
            try{
                Client.comunicateSend(new Message(id,"delete-article"));
                Message msg = Client.comunicateRead();
                if( msg.getB().compareTo("ok") == 0 ) {
//                    writerView.showArticles();
                        update();

//                    JTable table1 = writerView.getTable1();
//                    showArticles(table1);
//                    JTable table2 = home.getTable1();
////                    home.showArticles();
//                    showArticles(table2);
                    JOptionPane.showMessageDialog(null, "Article deleted successfully");
                }
                if ( msg.getB().compareTo("error") == 0)
                    JOptionPane.showMessageDialog(null,"Error!");
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }

        }
    }

    private class AddRelatedArticle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = writerView.getIdTF().getText();
            String id2 = writerView.getIdRelatedTF().getText();

            Gson gson = new Gson();
            ArrayList<String> list = new ArrayList<String>();
            list.add(id);
            list.add(id2);

            String json = gson.toJson(list);

            try{
                Client.comunicateSend(new Message(json, "add-related"));
                Message msg = Client.comunicateRead();

                if( msg.getB().compareTo("ok") == 0 ) {
                    JOptionPane.showMessageDialog(null, "Article updated successfully");
                }
                if ( msg.getB().compareTo("error") == 0)
                    JOptionPane.showMessageDialog(null,"Error!");
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }

        }
    }

    public void showArticles(JTable table1) throws IOException, ClassNotFoundException {
        Message message = new Message("1","get-articles");
        Client.comunicateSend(message);
        Message retMsg = Client.comunicateRead();

        ArrayList<JsonObject> list = new ArrayList<JsonObject>();
        JsonArray lang = (JsonArray) jsonParser.parse(retMsg.getB());
        for ( int i=0; i<lang.size(); i++ )
        {
            jsonObject = (JsonObject) jsonParser.parse(String.valueOf(lang.get(i)));
            list.add(jsonObject);
        }

        DefaultTableModel model1 = new DefaultTableModel();
        model1.addColumn("ID");
        model1.addColumn("Title");
        model1.addColumn("Abstract");

        table1.setModel(model1);

        Object[] row = new Object[3];
        for ( int i=0; i< list.size(); i++)
        {
            row[0] = list.get(i).get("id");
            row[1] = list.get(i).get("title");
            row[2] = list.get(i).get("abs");
            model1.addRow(row);
        }

        table1.setModel(model1);
        model1.fireTableDataChanged();

    }


}
