package org.example.view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminDashboardView extends JFrame {
    private JButton btnManageUsers = new JButton("Manage Users");
    private JButton btnManageBuses = new JButton("Manage Buses");
    private JButton btnManageShipments = new JButton("Manage Shipments");
    private JButton btnLogout = new JButton("Logout");

    public AdminDashboardView() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(btnManageUsers);
        panel.add(btnManageBuses);
        panel.add(btnManageShipments);
        panel.add(btnLogout);


        add(panel);


        setVisible(true);
    }

    // Methods to add action listeners
    public void addManageUsersListener(ActionListener listener) {
        btnManageUsers.addActionListener(listener);
    }

    public void addManageBusesListener(ActionListener listener) {
        btnManageBuses.addActionListener(listener);
    }

    public void addManageShipmentsListener(ActionListener listener) {
        btnManageShipments.addActionListener(listener);
    }

    public void addLogoutListener(ActionListener listener) {
        btnLogout.addActionListener(listener);
    }

    // Close the dashboard
    public void close() {
        this.dispose();
    }
}
