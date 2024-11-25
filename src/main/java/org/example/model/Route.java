package org.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Route {
    // Attributes
    private int routeID;
    private String startLocation;
    private String endLocation;
    private double distance; // in kilometers or miles, as per your preference

    // Constructors
    public Route() {}

    public Route(int routeID, String startLocation, String endLocation, double distance) {
        this.routeID = routeID;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
    }

    // Getters and Setters
    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    // Methods
    /**
     * Calculates the Estimated Time of Arrival (ETA) based on distance and average speed.
     * @param averageSpeed The average speed (e.g., in km/h or mph).
     * @return The ETA in hours.
     */
    public double calculateETA(double averageSpeed) {
        if (averageSpeed <= 0) {
            throw new IllegalArgumentException("Average speed must be greater than zero.");
        }
        return this.distance / averageSpeed;
    }

    /**
     * Calibrates the route by adjusting the distance based on new data.
     * @param newDistance The new distance to set.
     * @return true if the distance is updated successfully, false otherwise.
     */
    public boolean calibrateRoute(double newDistance) {
        String sql = "UPDATE routes SET distance = ? WHERE routeID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newDistance);
            pstmt.setInt(2, this.routeID);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                this.distance = newDistance;
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error calibrating route: " + e.getMessage());
        }
        return false;
    }

    /**
     * Adds a new route to the database.
     * @param route The Route object to add.
     * @return true if the route is added successfully, false otherwise.
     */
    public boolean addRoute(Route route) {
        String sql = "INSERT INTO routes (routeID, startLocation, endLocation, distance) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, route.getRouteID());
            pstmt.setString(2, route.getStartLocation());
            pstmt.setString(3, route.getEndLocation());
            pstmt.setDouble(4, route.getDistance());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error adding route: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves route details based on route ID.
     * @param routeID The route ID to search for.
     * @return A Route object if found, null otherwise.
     */
    public static Route getRouteByID(int routeID) {
        String sql = "SELECT * FROM routes WHERE routeID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, routeID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Route route = new Route();
                route.setRouteID(rs.getInt("routeID"));
                route.setStartLocation(rs.getString("startLocation"));
                route.setEndLocation(rs.getString("endLocation"));
                route.setDistance(rs.getDouble("distance"));
                return route;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving route: " + e.getMessage());
        }
        return null;
    }
}
