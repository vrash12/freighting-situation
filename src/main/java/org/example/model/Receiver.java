package org.example.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Receiver {
    // Attributes
    private int receiverID;
    private String name;
    private String address;
    private String contactNumber;

    // Constructors
    public Receiver() {}

    public Receiver(int receiverID, String name, String address, String contactNumber) {
        this.receiverID = receiverID;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    // Getters and Setters
    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    // Methods
    /**
     * Retrieves the status of all shipments for this receiver.
     * @return A ResultSet containing shipment statuses.
     */
    public ResultSet shipmentStatus() {
        String sql = "SELECT shipmentID, status FROM shipments WHERE receiverID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, this.receiverID);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error retrieving shipment status: " + e.getMessage());
            return null;
        }
    }

    /**
     * Adds a new receiver to the database.
     * @param receiver The Receiver object to add.
     * @return true if the receiver is added successfully, false otherwise.
     */
    public boolean addReceiver(Receiver receiver) {
        String sql = "INSERT INTO receivers (receiverID, name, address, contactNumber) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, receiver.getReceiverID());
            pstmt.setString(2, receiver.getName());
            pstmt.setString(3, receiver.getAddress());
            pstmt.setString(4, receiver.getContactNumber());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error adding receiver: " + e.getMessage());
            return false;
        }
    }

    /**
     * Removes a receiver from the database.
     * @param receiverID The ID of the receiver to remove.
     * @return true if the receiver is removed successfully, false otherwise.
     */
    public boolean removeReceiver(int receiverID) {
        String sql = "DELETE FROM receivers WHERE receiverID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, receiverID);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Error removing receiver: " + e.getMessage());
            return false;
        }
    }
}
