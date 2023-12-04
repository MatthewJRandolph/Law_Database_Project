import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    public static void displayMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1.  Add Law");
        System.out.println("2.  Add Category");
        System.out.println("3.  Add Tag");
        System.out.println("4.  Add LawCategory");
        System.out.println("5.  Add LawTag");
        System.out.println("6.  Add Comment");
        System.out.println("7.  View AuditLogs");
        System.out.println("8.  View All Laws");
        System.out.println("9.  Update a Law");
        System.out.println("10. Delete a Law");
        System.out.println("11. Interactive Queries");
        System.out.println("12. Analytics | Queries");

        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        System.out.println("\n--------------------------------------------------");
    }

    public static void mainMenu(Connection connection, String username) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        AdminFunctionality thisAdminFunctionality = new AdminFunctionality();
        MenuInteractiveQueries thisMenuInteractiveQueries = new MenuInteractiveQueries();
        MenuAnalyticsQueries thisMenuAnalyticsQueries = new MenuAnalyticsQueries();
        AddLawFunctionality thisAddLawFunctionality = new AddLawFunctionality();
        AddCategoryFunctionality thisAddCategoryFunctionality = new AddCategoryFunctionality();
        AddTagFunctionality thisAddTagFunctionality = new AddTagFunctionality();
        AddLawCategoryFunctionality thisAddLawCategoryFunctionality = new AddLawCategoryFunctionality();
        AddLawTagFuncitonality thisLawTagFuncitonality = new AddLawTagFuncitonality();
        AddCommentFunctionality thisAAddCommentFunctionality = new AddCommentFunctionality();
        AuditLogsFunctionality thisAuditLogsFunctionality = new AuditLogsFunctionality();
        DeleteLawFunctionality thisDeleteLawFunctionality = new DeleteLawFunctionality();
        UpdateLawDescriptionFunctionality thisUpdateLawDescriptionFunctionality = new UpdateLawDescriptionFunctionality();


        do {
            displayMenu();
            choice = scanner.nextInt();


            switch (choice) {
                case 1:
                    thisAddLawFunctionality.addLaw(connection, scanner);
                    break;
                case 2:
                    thisAddCategoryFunctionality.addCategory(connection);
                    break;
                case 3:
                    thisAddTagFunctionality.addTag(connection);
                    break;
                case 4:
                    // Call the method to add LawCategory
                    thisAddLawCategoryFunctionality.addLawCategory(connection);
                    break;
                case 5:
                    // Call the method to add LawTag
                    thisLawTagFuncitonality.addLawTag(connection);
                    break;
                case 6:
                    // Call the method to add a comment
                    thisAAddCommentFunctionality.addComment(connection);

                    break;
                case 7:
                    // Call the method to view AuditLogs
                    thisAuditLogsFunctionality.pullAuditLogsByUsername(connection, username);
                    break;
                case 8:

                    // Call the method to view all laws
                    thisAdminFunctionality.viewAllLaws(connection); // Call the method to display all laws
                    break;
                case 9:
                    thisUpdateLawDescriptionFunctionality.updateLawDescription(connection);

                    break;
                case 10:
                    thisDeleteLawFunctionality.deleteLaw(connection);


                    break;

                case 11:
                    thisMenuInteractiveQueries.menuInteractive(connection);
                    break;


                case 12:
                    thisMenuAnalyticsQueries.menuAnalytics(connection);
                    break;

                case 0:
                     try {
                            connection.close(); // Close the database connection
                            System.out.println("Database connection closed.");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        scanner.close();
                        break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);
        
        scanner.close();
    }
}
