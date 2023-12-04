import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteLawFunctionality {
    public void deleteLaw(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        
        // Display all the law titles from the database
        System.out.println("List of Laws:");
        displayLawTitles(connection);

        System.out.println("Enter the name of the law you want to delete: ");
        String lawTitle = scanner.nextLine();

        try {
            // Prepare the SQL statement to delete the law by its title
            String deleteLawSQL = "DELETE FROM Law WHERE l_title = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteLawSQL);
            preparedStatement.setString(1, lawTitle);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Law deleted successfully.");
            } else {
                System.out.println("Law not found. No law deleted.");
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
