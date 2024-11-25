package org.example.view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField txtUserID = new JTextField(20);
    private JPasswordField txtPassword = new JPasswordField(20);
    private JButton btnLogin = new JButton("Login");

    public LoginView() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

      
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("User ID:"));
        panel.add(txtUserID);
        panel.add(new JLabel("Password:"));
        panel.add(txtPassword);
        panel.add(new JLabel());
        panel.add(btnLogin);


        add(panel);

        setVisible(true);
    }


    public String getUserID() {
        return txtUserID.getText();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }


    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }


    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }


    public void close() {
        this.dispose();
    }
}
