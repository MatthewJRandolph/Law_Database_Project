import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminFunctionality {
    // ...

    public static void viewAllLaws(Connection connection) {
        System.out.println("hiiiiii -----"); // Debug: Print a message to check if the method is being called

        try {
            // Create a SQL statement
            Statement statement = connection.createStatement();
            
            System.out.println("Statement created successfully"); // Debug: Print a message to check if the statement is created

            // Execute the SQL query to retrieve all laws
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Law");
            
            System.out.println("Query executed successfully"); // Debug: Print a message to check if the query is executed

            // Print the details of each law
            while (resultSet.next()) {
                int lawId = resultSet.getInt("l_lawid");
                String title = resultSet.getString("l_title");
                String description = resultSet.getString("l_description");
                
                // Print law details
                System.out.println("Law ID: " + lawId);
                System.out.println("Title: " + title);
                System.out.println("Description: " + description);
                System.out.println("--------------------------------------------------");
            }

            // Close the statement and result set
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ...
}
