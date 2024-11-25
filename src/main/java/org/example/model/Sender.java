package org.example.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sender {
    // Attributes
    private int customerID;
    private String name;
    private String address;
    private String contactNumber;

    // Constructors
    public Sender() {}

    public Sender(int customerID, String name, String address, String contactNumber) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    // Getters and Setters
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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
    public boolean createShipment(Shipment shipment, int branchID) {
        String sql = "INSERT INTO shipments (shipmentID, senderID, receiverID, busID, branchID, weight, dimensions, status, cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, shipment.getShipmentID());
            pstmt.setInt(2, this.customerID);
            pstmt.setInt(3, shipment.getReceiverID());
            pstmt.setInt(4, shipment.getBusID());
            pstmt.setInt(5, branchID);
            pstmt.setDouble(6, shipment.getWeight());
            pstmt.setString(7, shipment.getDimensions());
            pstmt.setString(8, shipment.getStatus());
            pstmt.setDouble(9, shipment.getCost());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error creating shipment: " + e.getMessage());
            return false;
        }
    }

    public Shipment viewShipmentStatus(int shipmentID) {
        String sql = "SELECT * FROM shipments WHERE shipmentID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, shipmentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Shipment shipment = new Shipment();
                shipment.setShipmentID(rs.getInt("shipmentID"));
                shipment.setSenderID(rs.getInt("senderID"));
                shipment.setReceiverID(rs.getInt("receiverID"));
                shipment.setBusID(rs.getInt("busID"));
                shipment.setBranchID(rs.getInt("branchID"));
                shipment.setWeight(rs.getDouble("weight"));
                shipment.setDimensions(rs.getString("dimensions"));
                shipment.setStatus(rs.getString("status"));
                shipment.setCost(rs.getDouble("cost"));
                return shipment;
            }
        } catch (SQLException e) {
            System.err.println("Error viewing shipment status: " + e.getMessage());
        }
        return null;
    }

    public ResultSet viewShipmentHistory(int customerID) {
        String sql = "SELECT * FROM shipments WHERE senderID = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerID);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error viewing shipment history: " + e.getMessage());
            return null;
        }
    }
}
