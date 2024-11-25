package org.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bus {
    // Attributes
    private int busID;
    private int capacity;
    private int route;
    private String currentStatus;
    private String driver;

    // Constructors
    public Bus() {}

    public Bus(int busID, int capacity, int route, String currentStatus, String driver) {
        this.busID = busID;
        this.capacity = capacity;
        this.route = route;
        this.currentStatus = currentStatus;
        this.driver = driver;
    }

    // Getters and Setters
    public int getBusID() {
        return busID;
    }

    public void setBusID(int busID) {
        this.busID = busID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    // Methods
    public boolean updateCapacity(int newCapacity) {
        String sql = "UPDATE buses SET capacity = ? WHERE busID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newCapacity);
            pstmt.setInt(2, this.busID);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                this.capacity = newCapacity;
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error updating capacity: " + e.getMessage());
        }
        return false;
    }

    public boolean assignRoute(int newRoute) {
        String sql = "UPDATE buses SET route = ? WHERE busID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newRoute);
            pstmt.setInt(2, this.busID);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                this.route = newRoute;
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error assigning route: " + e.getMessage());
        }
        return false;
    }

    public boolean checkAvailability() {
        String sql = "SELECT currentStatus FROM buses WHERE busID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, this.busID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                this.currentStatus = rs.getString("currentStatus");
                return this.currentStatus.equalsIgnoreCase("available");
            }
        } catch (SQLException e) {
            System.err.println("Error checking availability: " + e.getMessage());
        }
        return false;
    }
}
