import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateLawDescriptionFunctionality {
    public void updateLawDescription(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        // Display all the law titles from the database 
        System.out.println("List of Laws:");
        displayLawTitles(connection);

        System.out.println("Enter the name of the law you want to update the description for: ");
        String lawTitle = scanner.nextLine();

        System.out.println("Enter the new description for the law: ");
        String newDescription = scanner.nextLine();

        try {
            // Prepare the SQL statement to update the law's description by its title
            String updateLawDescriptionSQL = "UPDATE Law SET l_description = ? WHERE l_title = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateLawDescriptionSQL);
            preparedStatement.setString(1, newDescription);
            preparedStatement.setString(2, lawTitle);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Law description updated successfully.");
            } else {
                System.out.println("Law not found. No description updated.");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayLawTitles(Connection connection) {
        try {
            String query = "SELECT l_title FROM Law";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String lawTitle = resultSet.getString("l_title");
                System.out.println(lawTitle);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
