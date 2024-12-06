package com.three_amigas.LaundryOps;

import com.three_amigas.LaundryOps.Models.SQLquery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUD {
    private static final String DB_URL = "jdbc:mysql://database-management.crcukwsq0ap7.ap-southeast-2.rds.amazonaws.com:3306/laundry_ops?useSSL=false&serverTimezone=UTC";
    private static final String USER = "admin";
    private static final String PASSWORD = "August_172024";

    private Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Connection failed!" + e);
        }
        return connection;
    }

    public boolean create(SQLquery sql) {
        String query = "INSERT INTO users (id, name, number, email, date, done, mailed) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = this.connect(); 
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, sql.id);
            statement.setString(2, sql.name);
            statement.setString(3, sql.number);
            statement.setString(4, sql.email);
            statement.setString(5, sql.date);
            statement.setBoolean(6, false);
            statement.setBoolean(7, false);
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error during CREATE operation: " + e.getMessage());
        }
        return false;
    }

    public List<SQLquery> read() {
        String query = "SELECT * FROM users";
        List<SQLquery> users = new ArrayList<>();
        try (Connection connection = this.connect(); 
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
             
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String number = resultSet.getString("number");
                String email = resultSet.getString("email");
                String date = resultSet.getString("date");
                boolean done = resultSet.getBoolean("done");
                boolean mailed = resultSet.getBoolean("mailed");
                
                SQLquery sql = new SQLquery(id, name, number, email, date, done, mailed);
                users.add(sql);
            }
        } catch (SQLException e) {
            System.err.println("Error during READ operation: " + e.getMessage());
        }
        return users;
    }
    
    public int getLastInsertedId() {
        String query = "SELECT MAX(id) AS max_id FROM users";
        int lastId = 0;
        try (Connection connection = this.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                lastId = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            System.err.println("Error during SELECT operation: " + e.getMessage());
        }
        return lastId;
    }

    public boolean update(SQLquery sql) {
        String query = "UPDATE users SET name = ?, number = ?, email = ?, date = ?, done = ?, mailed = ? WHERE id = ?";
        try (Connection connection = this.connect(); 
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, sql.name);
            statement.setString(2, sql.number);
            statement.setString(3, sql.email);
            statement.setString(4, sql.date);
            statement.setBoolean(5, sql.done);
            statement.setBoolean(6, sql.mailed);
            statement.setInt(7, sql.id);
            
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User with ID " + sql.id + " was updated successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error during UPDATE operation: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteLastInserted() {
        String selectQuery = "SELECT id FROM users ORDER BY id DESC LIMIT 1";
        int lastInsertedId = -1;

        try (Connection connection = this.connect();
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = selectStatement.executeQuery()) {

            if (resultSet.next()) {
                lastInsertedId = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            System.err.println("Error during SELECT operation: " + e.getMessage());
            return false;
        }

        if (lastInsertedId != -1) {
            String deleteQuery = "DELETE FROM users WHERE id = ?";
            try (Connection connection = this.connect();
                 PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {

                deleteStatement.setInt(1, lastInsertedId);

                int rowsDeleted = deleteStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Last inserted record was deleted successfully.");
                    return true;
                } else {
                    System.out.println("No records found to delete.");
                }
            } catch (SQLException e) {
                System.err.println("Error during DELETE operation: " + e.getMessage());
            }
        }

        return false;
    }
    
    public boolean clear() {
        String query = "DELETE FROM users";
        try (Connection connection = this.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("All records have been cleared.");
                return true;
            } else {
                System.out.println("No records found to delete.");
            }
        } catch (SQLException e) {
            System.err.println("Error during CLEAR operation: " + e.getMessage());
        }
        return false;
    }
}
