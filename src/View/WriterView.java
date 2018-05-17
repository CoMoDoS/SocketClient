package View;

import Start.Client;
import Start.Controller;
import Start.Message;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class WriterView extends JFrame
{
    private JPanel mainPanel;
    private JTable table1;
    private JTextField titleTF;
    private JTextField absTF;
    private JTextField authorTF;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextArea bodyTA;
    private JButton addRelatedButton;
    private JTextField idRelatedTF;
    private JTextField idTF;

    private JsonObject jsonObject = new JsonObject();
    private JsonParser jsonParser = new JsonParser();

    public WriterView()
    {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        table1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        int tableRow = table1.getSelectedRow();
                        TableModel model2 = table1.getModel();
                        JsonPrimitive id = (JsonPrimitive) model2.getValueAt(tableRow,0);
                        String idArticle = id.getAsString();
                        JsonPrimitive title = (JsonPrimitive) model2.getValueAt(tableRow,1);
                        String titleArticle = title.getAsString();
                        JsonPrimitive abs = (JsonPrimitive) model2.getValueAt(tableRow,2);
                        String absArticle = abs.getAsString();

                idTF.setText(idArticle);
                titleTF.setText(titleArticle);
                absTF.setText(absArticle);

                try
                {
                    Client.comunicateSend(new Message(idArticle,"get-article"));
                    Message retMsg = Client.comunicateRead();
                    System.out.println("retMesg WriterView -  " + retMsg.toString());

                    jsonObject = (JsonObject) jsonParser.parse(retMsg.getB());

                    String body = String.valueOf(jsonObject.get("body"));
                    String author = String.valueOf(jsonObject.get("author"));
                    authorTF.setText(author);
                    bodyTA.setText(body);

                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public JTextField getTitleTF() {
        return this.titleTF;
    }

    public JTextField getAbsTF() {
        return this.absTF;
    }

    public JTextField getAuthorTF() {
        return this.authorTF;
    }

    public JTextField getIdTF() {
        return this.idTF;
    }

    public JTextField getIdRelatedTF() {
        return this.idRelatedTF;
    }

    public JTextArea getBodyTA() {
        return this.bodyTA;
    }

    public JTable getTable1()
    {
        return this.table1;
    }
    public void setDeleteButton(ActionListener e)
    {
        this.deleteButton.addActionListener(e);
    }

    public void setUpdateButton(ActionListener e)
    {
        this.updateButton.addActionListener(e);
    }
    public void setCreateButton(ActionListener e)
    {
        this.createButton.addActionListener(e);
    }

    public void setAddRelatedButton(ActionListener e)
    {
        this.addRelatedButton.addActionListener(e);
    }


}
