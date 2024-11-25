package org.example.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class Admin {
    // Attributes
    private int userID;
    private String name;
    private String role;
    private String credentials;

    // Constructors
    public Admin() {}

    public Admin(int userID, String name, String role, String credentials) {
        this.userID = userID;
        this.name = name;
        this.role = role;
        this.credentials = credentials;
    }

    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    // Methods
    public boolean addUser(Admin admin) {
        String sql = "INSERT INTO admins (userID, name, role, credentials) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, admin.getUserID());
            pstmt.setString(2, admin.getName());
            pstmt.setString(3, admin.getRole());
            pstmt.setString(4, admin.getCredentials());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    public boolean removeUser(int userID) {
        String sql = "DELETE FROM admins WHERE userID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userID);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error removing user: " + e.getMessage());
            return false;
        }
    }

    public boolean assignRole(int userID, String newRole) {
        String sql = "UPDATE admins SET role = ? WHERE userID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newRole);
            pstmt.setInt(2, userID);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error assigning role: " + e.getMessage());
            return false;
        }
    }

    public boolean validateLogin(int userID, String credentials) {
        String sql = "SELECT * FROM admins WHERE userID = ? AND credentials = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userID);
            pstmt.setString(2, credentials);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a matching record is found
        } catch (SQLException e) {
            System.err.println("Error validating login: " + e.getMessage());
            return false;
        }
    }
}
