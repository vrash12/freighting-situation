package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminView extends JFrame {

    private JPanel loginPanel;
    private JTextField txtLoginUserID = new JTextField(20);
    private JPasswordField txtLoginPassword = new JPasswordField(20);
    private JButton btnLogin = new JButton("Login");


    private JPanel dashboardPanel;
    private JTextField txtUserID = new JTextField(20);
    private JTextField txtName = new JTextField(20);
    private JTextField txtRole = new JTextField(20);
    private JTextField txtCredentials = new JTextField(20);
    private JButton btnAddUser = new JButton("Add User");
    private JButton btnRemoveUser = new JButton("Remove User");
    private JButton btnAssignRole = new JButton("Assign Role");


    public AdminView() {

        setTitle("Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);


        initLoginPanel();


        initDashboardPanel();


        add(loginPanel);


        setVisible(true);
    }


    private void initLoginPanel() {
        loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        loginPanel.add(new JLabel("User ID:"));
        loginPanel.add(txtLoginUserID);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(txtLoginPassword);
        loginPanel.add(new JLabel()); // Empty cell
        loginPanel.add(btnLogin);
    }


    private void initDashboardPanel() {
        dashboardPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        dashboardPanel.add(new JLabel("User ID:"));
        dashboardPanel.add(txtUserID);
        dashboardPanel.add(new JLabel("Name:"));
        dashboardPanel.add(txtName);
        dashboardPanel.add(new JLabel("Role:"));
        dashboardPanel.add(txtRole);
        dashboardPanel.add(new JLabel("Credentials:"));
        dashboardPanel.add(txtCredentials);

        dashboardPanel.add(btnAddUser);
        dashboardPanel.add(btnRemoveUser);
        dashboardPanel.add(btnAssignRole);
    }


    public void showDashboard() {

        remove(loginPanel);

        add(dashboardPanel);

        revalidate();
        repaint();
        setTitle("Admin Dashboard");
    }


    public String getLoginUserID() {
        return txtLoginUserID.getText();
    }

    public String getLoginPassword() {
        return new String(txtLoginPassword.getPassword());
    }


    public String getUserID() {
        return txtUserID.getText();
    }

    public String getUserName() {
        return txtName.getText();
    }

    public String getUserRole() {
        return txtRole.getText();
    }

    public String getUserCredentials() {
        return txtCredentials.getText();
    }

    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    public void addAddUserListener(ActionListener listener) {
        btnAddUser.addActionListener(listener);
    }

    public void addRemoveUserListener(ActionListener listener) {
        btnRemoveUser.addActionListener(listener);
    }

    public void addAssignRoleListener(ActionListener listener) {
        btnAssignRole.addActionListener(listener);
    }


    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
