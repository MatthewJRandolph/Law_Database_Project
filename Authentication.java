

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Authentication {
    public static boolean authenticateUser(Connection connection, String username) {
        try {
            // Check if the username is in the User table with the usertype "Admin"
            String sql = "SELECT * FROM User WHERE u_username = ? AND u_usertype = 'Admin'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            // If a row is found, the user is authenticated as an admin
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
