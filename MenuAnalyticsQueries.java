import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuAnalyticsQueries {
    public static void displayAnalyticsQueriesMenu() {
        System.out.println("Analytics Options:");
        System.out.println("1. Lists the titles of laws along with their associated tags and the username of the user who commented on them");
        System.out.println("2. Find laws that have no associated comments and are not assigned to any category. ");
        System.out.println("3. Get the count of laws in each category along with the average number of comments per law in that category:.");
        System.out.println("4. finds the category with the most comments tied to the laws in that category");
        System.out.println("5. Retrieve laws with the most comments, ordered by the count of comments:");
        System.out.println("6. Calculate the average length of comments made by each user type (Admin and Regular).");
        System.out.println("7. Find Users Who Have Commented on Multiple Categories:");
        System.out.println("8. List users and the titles of laws they have commented on, along with the count of their comments, for users who have commented on more than one law");
        System.out.println("9. Laws that have not been commented on and also have not been associated with any categories or tags, ");
        System.out.print("Enter your choice: ");
        System.out.println("\n--------------------------------------------------");
    }

    public static void menuAnalytics(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayAnalyticsQueriesMenu();
            choice = scanner.nextInt();
            AnalyticsQueries thisAnalyticsQueries = new AnalyticsQueries();

            switch (choice) {
                case 1:
                    thisAnalyticsQueries.query1(connection);
                    break;
                case 2:
                    thisAnalyticsQueries.query2(connection);
                    break;
                case 3:
                    thisAnalyticsQueries.query3(connection);
                    break;
                case 4:
                    thisAnalyticsQueries.query4(connection);
                    break;
                case 5:
                    thisAnalyticsQueries.query5(connection);
                    break;
    
                case 6:
                    thisAnalyticsQueries.query6(connection);
                    break;

                case 7:
                    thisAnalyticsQueries.query7(connection);
                    break;

                case 8:
                    thisAnalyticsQueries.query8(connection);
                    break;

                case 9:
                    thisAnalyticsQueries.query9(connection);
                    break;
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

    

