import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class AuditLogsFunctionality {
    public List<String> pullAuditLogsByUsername(Connection connection, String username) {
    List<String> auditLogs = new ArrayList<>();
    try {
        // First, get the user's ID based on their username
        String getUserIdSQL = "SELECT u.u_userid FROM User u WHERE u.u_username = ?";
        PreparedStatement getUserIdStmt = connection.prepareStatement(getUserIdSQL);
        getUserIdStmt.setString(1, username);
        ResultSet userIdResultSet = getUserIdStmt.executeQuery();

        int userId = -1;  // Initialize userId to an invalid value
        if (userIdResultSet.next()) {
            userId = userIdResultSet.getInt("u_userid");
            System.out.println("User ID found: " + userId); // Print the retrieved user ID
        } else {
            System.out.println("User not found."); // Handle the case where the user doesn't exist
            return auditLogs;
        }

        // Fetch audit logs for the user with the retrieved user ID
        String getAuditLogsSQL = "SELECT a.a_timestamp FROM AuditLog a WHERE a.a_adminid = ?";
        PreparedStatement getAuditLogsStmt = connection.prepareStatement(getAuditLogsSQL);
        getAuditLogsStmt.setInt(1, userId);
        ResultSet auditLogsResultSet = getAuditLogsStmt.executeQuery();

        // Store the timestamps in the list
        while (auditLogsResultSet.next()) {
            long timestampInMillis = auditLogsResultSet.getLong("a_timestamp");
            
            // Convert timestamp from milliseconds to a Date object
            Date timestampDate = new Date(timestampInMillis);
            
            // Define a date format for the output
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            // Format the date and add it to the list
            String formattedTimestamp = dateFormat.format(timestampDate);
            System.out.println("Timestamp found: " + formattedTimestamp); // Print each retrieved timestamp
            auditLogs.add(formattedTimestamp);
        }

        // Close the result sets and statements
        userIdResultSet.close();
        getUserIdStmt.close();
        auditLogsResultSet.close();
        getAuditLogsStmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return auditLogs;
}
    

    public void insertAuditLog(Connection connection, String username) {
        try {
            // First, get the user's ID based on their username
            String getUserIdSQL = "SELECT u.u_userid FROM User u WHERE u.u_username = ?";
            PreparedStatement getUserIdStmt = connection.prepareStatement(getUserIdSQL);
            getUserIdStmt.setString(1, username);
            int userId = -1;  // Initialize userId to an invalid value

            // Execute the query to get the user's ID
            if (getUserIdStmt.execute()) {
                var userIdResultSet = getUserIdStmt.getResultSet();
                if (userIdResultSet.next()) {
                    userId = userIdResultSet.getInt("u_userid");
                } else {
                    System.out.println("User not found."); // Handle the case where the user doesn't exist
                    return;
                }
            }

            // Get the current timestamp
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(new Date().getTime());

            // Insert the audit log entry
            String insertAuditLogSQL = "INSERT INTO AuditLog (a_adminid, a_timestamp) VALUES (?, ?)";
            PreparedStatement insertAuditLogStmt = connection.prepareStatement(insertAuditLogSQL);
            insertAuditLogStmt.setInt(1, userId);
            insertAuditLogStmt.setTimestamp(2, currentTimestamp);
            insertAuditLogStmt.executeUpdate();

            // Close the statements
            getUserIdStmt.close();
            insertAuditLogStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
