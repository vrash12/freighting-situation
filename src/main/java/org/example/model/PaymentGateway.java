package org.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentGateway {
    // Attributes
    private int paymentID;
    private int shipmentID;
    private double amount;
    private String paymentMethod;
    private String status;

    // Constructors
    public PaymentGateway() {}

    public PaymentGateway(int paymentID, int shipmentID, double amount, String paymentMethod, String status) {
        this.paymentID = paymentID;
        this.shipmentID = shipmentID;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    // Getters and Setters
    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getShipmentID() {
        return shipmentID;
    }

    public void setShipmentID(int shipmentID) {
        this.shipmentID = shipmentID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Methods
    /**
     * Processes a payment by updating its status to 'Processed'.
     * @return true if the payment is processed successfully, false otherwise.
     */
    public boolean processPayment() {
        String sql = "UPDATE payment_gateways SET status = 'Processed' WHERE paymentID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, this.paymentID);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                this.status = "Processed";
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error processing payment: " + e.getMessage());
        }
        return false;
    }

    /**
     * Generates a receipt for the payment.
     * @return A string representing the receipt details.
     */
    public String generateReceipt() {
        // Simple receipt generation logic
        String receipt = "----- Payment Receipt -----\n"
                + "Payment ID: " + this.paymentID + "\n"
                + "Shipment ID: " + this.shipmentID + "\n"
                + "Amount: $" + this.amount + "\n"
                + "Payment Method: " + this.paymentMethod + "\n"
                + "Status: " + this.status + "\n"
                + "Thank you for your payment!\n"
                + "----------------------------";
        return receipt;
    }

    /**
     * Adds a new payment record to the database.
     * @param payment The PaymentGateway object to add.
     * @return true if the payment is added successfully, false otherwise.
     */
    public boolean addPayment(PaymentGateway payment) {
        String sql = "INSERT INTO payment_gateways (paymentID, shipmentID, amount, paymentMethod, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, payment.getPaymentID());
            pstmt.setInt(2, payment.getShipmentID());
            pstmt.setDouble(3, payment.getAmount());
            pstmt.setString(4, payment.getPaymentMethod());
            pstmt.setString(5, payment.getStatus());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error adding payment: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves payment details based on payment ID.
     * @param paymentID The payment ID to search for.
     * @return A PaymentGateway object if found, null otherwise.
     */
    public static PaymentGateway getPaymentByID(int paymentID) {
        String sql = "SELECT * FROM payment_gateways WHERE paymentID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, paymentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PaymentGateway payment = new PaymentGateway();
                payment.setPaymentID(rs.getInt("paymentID"));
                payment.setShipmentID(rs.getInt("shipmentID"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentMethod(rs.getString("paymentMethod"));
                payment.setStatus(rs.getString("status"));
                return payment;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving payment: " + e.getMessage());
        }
        return null;
    }
}
