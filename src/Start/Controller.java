package Start;

import View.Home;
import com.google.gson.Gson;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class Controller
{
    private Home home;

    public Controller(Home home)
    {
        this.home = home;
        this.home.setVisible(true);
        this.home.setLoginButton(new ButtonLogin());
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
                if( retMsg.getB().compareTo("ok") == 0 )
                    JOptionPane.showMessageDialog(null,"Login successfully");
                if ( retMsg.getB().compareTo("no") == 0)
                    JOptionPane.showMessageDialog(null, "Wrong credentials");
                if ( retMsg.getB().compareTo("error") == 0)
                    JOptionPane.showMessageDialog(null,"Error!");
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }
}
