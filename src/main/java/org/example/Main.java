// Main.java
package org.example;
// Main.java

import org.example.controller.AdminController;
import org.example.model.Admin;
import org.example.view.AdminView;
public class Main {
    public static void main(String[] args) {
        // Create instances of the model and view
        Admin model = new Admin();

        // Ensure the main admin exists in the database
        ensureMainAdminExists(model);

        AdminView view = new AdminView();

        // Create the controller
        new AdminController(model, view);
    }

    private static void ensureMainAdminExists(Admin model) {
        // Check if admin with ID 2001 exists
        boolean exists = model.validateLogin(2001, "admin"); // Assuming default password is "admin"
        if (!exists) {
            // Create the main admin
            Admin mainAdmin = new Admin(2001, "Main Admin", "Administrator", "admin");
            boolean isAdded = model.addUser(mainAdmin);
            if (isAdded) {
                System.out.println("Main admin created with ID 2001 and password 'admin'.");
            } else {
                System.err.println("Failed to create main admin.");
            }
        }
    }
}
