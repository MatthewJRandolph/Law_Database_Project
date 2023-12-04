import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuInteractiveQueries {
    public static void displayInteractiveMenu() {
        System.out.println("Interactive Options:");
        System.out.println("1. Insert two categories to display all users who commented  on a law that falls under those two categories");
        System.out.println("2. Based on the Category you provide, it display Law/Laws with at least one tag that do not belong to the category you provided. Along with its' associated tags ");
        System.out.println("3. Input your username, will display the tags are associated with the comments the user has done.");
        System.out.println("4. Insert a date (for example 2023-01-01) to displays users and laws and comments they enacted on.");
        System.out.println("5. Insert a Law name, to display the username/usernames who commented on that Law");
        System.out.println("6. Input a number, to return the top number of commented Laws");
        System.out.println("************");

    }

    public static void menuInteractive(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayInteractiveMenu();
            choice = scanner.nextInt();
            InteractiveQueries thisInteractiveQueries = new InteractiveQueries();

            switch (choice) {
                case 1:
                    thisInteractiveQueries.query1(connection);
                    break;
                case 2:
                    thisInteractiveQueries.query2(connection);
                case 3:
                    thisInteractiveQueries.query3(connection);

                    break;
                case 4:
                    thisInteractiveQueries.query4(connection);
                case 5:
                    thisInteractiveQueries.query5(connection);

                case 6:
                    thisInteractiveQueries.query6(connection);
                case 0:
                    try {
                        connection.close(); // Close the database connection
                        System.out.println("Database connection closed.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        } while (choice != 0);
        
        scanner.close();
    }
}
    

