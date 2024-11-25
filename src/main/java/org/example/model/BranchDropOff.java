package org.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BranchDropOff {
    // Attributes
    private int branchID;
    private String location;

    // Constructors
    public BranchDropOff() {}

    public BranchDropOff(int branchID, String location) {
        this.branchID = branchID;
        this.location = location;
    }

    // Getters and Setters
    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean verifyIdentity(int rcvrID) {
        String sql = "SELECT * FROM receivers WHERE receiverID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, rcvrID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a record exists
        } catch (SQLException e) {
            System.err.println("Error verifying receiver identity: " + e.getMessage());
            return false;
        }
    }


    public boolean addBranch(BranchDropOff branch) {
        String sql = "INSERT INTO branch_drop_off (branchID, location) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, branch.getBranchID());
            pstmt.setString(2, branch.getLocation());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error adding branch: " + e.getMessage());
            return false;
        }
    }

    /**
     * Removes a branch drop-off location from the database.
     * @param branchID The ID of the branch to remove.
     * @return true if the branch is removed successfully, false otherwise.
     */
    public boolean removeBranch(int branchID) {
        String sql = "DELETE FROM branch_drop_off WHERE branchID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, branchID);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error removing branch: " + e.getMessage());
            return false;
        }
    }
}
