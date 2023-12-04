import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AddLawFunctionality {
    public static void addLaw(Connection connection, Scanner scanner) {
        // Prompt the user for the name of the law
       // Prompt the user for the name of the law
    System.out.print("Enter the name of the law: ");
    String lawName = scanner.next(); // Use next() here

    // Consume the newline character left in the input stream
    scanner.nextLine();

    // Prompt the user for the description of the law
    System.out.print("Enter the description of the law: ");
    String lawDescription = scanner.nextLine();

        // Prompt the user for the date enacted in yyyy-MM-dd format
        Date dateEnacted = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        while (dateEnacted == null) {
            try {
                System.out.print("Enter the date enacted (yyyy-MM-dd): ");
                String dateEnactedStr = scanner.nextLine();
                dateEnacted = dateFormat.parse(dateEnactedStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }
        }

        try {
            // Create a SQL statement to insert the new law
            String insertLawSQL = "INSERT INTO Law (l_title, l_description, l_dateenacted) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertLawSQL);
            preparedStatement.setString(1, lawName);
            preparedStatement.setString(2, lawDescription);
            preparedStatement.setDate(3, new java.sql.Date(dateEnacted.getTime()));

            // Execute the SQL statement to insert the law
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Law added successfully!");
            } else {
                System.out.println("Failed to add the law.");
            }

            // Close the prepared statement (no need to close the scanner)
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("ERRRRORRRRR");
            e.printStackTrace();
        }
    }

    
}
