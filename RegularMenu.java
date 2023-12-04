import java.sql.Connection;
import java.util.Scanner;

public class RegularMenu {
    public static void displayMenu() {
        System.out.println("Regular Menu:");
        System.out.println("1. Add Comment");
        System.out.println("2. View All Laws");
        System.out.println("3. Interactive Queries");
        System.out.println("4. Analytics | Queries");

        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void mainRegularMenu(Connection connection, String username) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        AdminFunctionality thisAdminFunctionality = new AdminFunctionality();
        MenuInteractiveQueries thisMenuInteractiveQueries = new MenuInteractiveQueries();
        MenuAnalyticsQueries thisMenuAnalyticsQueries = new MenuAnalyticsQueries();
        AddCommentFunctionality thisAAddCommentFunctionality = new AddCommentFunctionality();


        do {
            displayMenu();
            choice = scanner.nextInt();


            switch (choice) {
                
                case 1:
                    // Call the method to add a comment
                    thisAAddCommentFunctionality.addComment(connection);
                    break;
                case 2:
                    // Call the method to view all laws
                    thisAdminFunctionality.viewAllLaws(connection); // Call the method to display all laws
                    break;
                case 3:
                    // Call the method to handle interactive queries
                    thisMenuInteractiveQueries.menuInteractive(connection);

                case 4:
                    thisMenuAnalyticsQueries.menuAnalytics(connection);

                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);
        
        scanner.close();
    }
    
}
