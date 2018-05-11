package View;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AdminView extends JFrame {
    private JPanel mainPanel;
    private JTextField nameTF;
    private JTextField emailTF;
    private JTextField passTF;
    private JTextField statusTF;
    private JButton createButton;

    public AdminView()
    {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

    }

    public JTextField getNameTF() {
        return nameTF;
    }

    public JTextField getEmailTF() {
        return emailTF;
    }

    public JTextField getPassTF() {
        return passTF;
    }

    public JTextField getStatusTF() {
        return statusTF;
    }

    public void setCreateButton(ActionListener e)
    {
        this.createButton.addActionListener(e);
    }
}
