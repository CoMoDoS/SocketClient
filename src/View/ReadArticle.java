package View;

import Start.Client;
import Start.Message;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class ReadArticle extends JFrame {
    private JTable table1;
    private JTextArea textArea1;
    private JPanel mainPanel;

    private String body;
    private JsonArray jsonArray;

    private JsonParser jsonParser = new JsonParser();
    private JsonObject jsonObject = new JsonObject();

    public ReadArticle(String body, JsonArray jsonArray) throws IOException, ClassNotFoundException {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);
        this.body = body;
        this.jsonArray = jsonArray;
        textArea1.setText(body);
        showRelated(jsonArray);


        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int aux = table1.getSelectedRow();
                TableModel model3 = table1.getModel();
                JsonPrimitive primitive = (JsonPrimitive) model3.getValueAt(aux,0);
                int id = primitive.getAsInt();
                try {
                    Client.comunicateSend(new Message(String.valueOf(id), "get-article"));
                    Message retMsg = Client.comunicateRead();
                    System.out.println("primit: " + retMsg.getB());

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
                    //System.out.println(retRelated.toString());
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    public void showRelated(JsonArray jsonArray) {

        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        ArrayList<JsonObject> list = new ArrayList<JsonObject>();
        for ( int i=0; i<jsonArray.size(); i++ )
        {
            jsonObject = (JsonObject) jsonParser.parse(String.valueOf(jsonArray.get(i)));
            list.add(jsonObject);
        }

        DefaultTableModel model2 = new DefaultTableModel();
        model2.addColumn("ID");
        model2.addColumn("Title");
        model2.addColumn("Abstract");

        table1.setModel(model2);

        Object[] row = new Object[3];
        for ( int i=0; i< list.size(); i++)
        {
            row[0] = list.get(i).get("id");
            row[1] = list.get(i).get("title");
            row[2] = list.get(i).get("abs");
            model2.addRow(row);
        }

        table1.setModel(model2);
        model2.fireTableDataChanged();

    }
}
