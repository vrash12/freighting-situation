package org.example.controller;


import org.example.model.Admin;
import org.example.view.AdminView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminController {
    private Admin model;
    private AdminView view;

    public AdminController(Admin model, AdminView view) {
        this.model = model;
        this.view = view;

        // Add action listeners
        this.view.addLoginListener(new LoginListener());
        // Dashboard listeners will be added after successful login
    }

    // Inner class for login listener
    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int userID = Integer.parseInt(view.getLoginUserID());
                String password = view.getLoginPassword();

                // Check if the user ID starts with 200
                if (String.valueOf(userID).startsWith("200")) {
                    boolean isValid = model.validateLogin(userID, password);
                    if (isValid) {
                        // Successful login
                        view.showMessage("Login successful.");
                        // Switch to dashboard
                        view.showDashboard();
                        // Add action listeners for dashboard buttons
                        view.addAddUserListener(new AddUserListener());
                        view.addRemoveUserListener(new RemoveUserListener());
                        view.addAssignRoleListener(new AssignRoleListener());
                    } else {
                        view.showMessage("Invalid credentials.");
                    }
                } else {
                    view.showMessage("Invalid User ID. Must start with 200.");
                }
            } catch (NumberFormatException ex) {
                view.showMessage("Invalid User ID format.");
            }
        }
    }

    // Inner classes for dashboard action listeners
    class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int userID = Integer.parseInt(view.getUserID());
                String name = view.getUserName();
                String role = view.getUserRole();
                String credentials = view.getUserCredentials();

                // Check if userID starts with 200 and is not 2001 (reserved for admin)
                if (String.valueOf(userID).startsWith("200") && userID != 2001) {
                    Admin newAdmin = new Admin(userID, name, role, credentials);
                    boolean isAdded = model.addUser(newAdmin);
                    if (isAdded) {
                        view.showMessage("User added successfully.");
                    } else {
                        view.showMessage("Failed to add user.");
                    }
                } else {
                    view.showMessage("User ID must start with 200 and not be 2001.");
                }
            } catch (NumberFormatException ex) {
                view.showMessage("Invalid User ID.");
            }
        }
    }

    class RemoveUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int userID = Integer.parseInt(view.getUserID());
                // Prevent deletion of admin with ID 2001
                if (userID == 2001) {
                    view.showMessage("Cannot remove the main admin.");
                    return;
                }
                boolean isRemoved = model.removeUser(userID);
                if (isRemoved) {
                    view.showMessage("User removed successfully.");
                } else {
                    view.showMessage("Failed to remove user.");
                }
            } catch (NumberFormatException ex) {
                view.showMessage("Invalid User ID.");
            }
        }
    }

    class AssignRoleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int userID = Integer.parseInt(view.getUserID());
                String newRole = view.getUserRole();
                boolean isAssigned = model.assignRole(userID, newRole);
                if (isAssigned) {
                    view.showMessage("Role assigned successfully.");
                } else {
                    view.showMessage("Failed to assign role.");
                }
            } catch (NumberFormatException ex) {
                view.showMessage("Invalid User ID.");
            }
        }
    }
}
