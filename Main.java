
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
       // Initialize database connection
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:californiaStateLaws.db");

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Display welcome message
        System.out.println("Welcome to your command-line application!");
        System.out.println("Please enter your username:        | Or press 1 to create a new user");
      
        Menu thisMenu = new Menu();
        AuditLogsFunctionality thisAuditLogsFunctionality = new AuditLogsFunctionality();



        //Collect user input
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();

        String check = "1";
        if(username.equals(check)){
            System.out.println("it is equal 1");
            UserRegistration thisUserRegistration = new UserRegistration(connection);
            thisUserRegistration.registerUser();

        }

        // // Authenticate the user
        boolean isAdmin = Authentication.authenticateUser(connection, username);
    


        // Based on user type, call appropriate functionality
        if (isAdmin) {
            thisAuditLogsFunctionality.insertAuditLog(connection, username);
            thisMenu.mainMenu(connection, username); // displays the  numbered options and lets user type in choice

        }
        if (!isAdmin && username!= check){
            RegularMenu thisRegularMenu = new RegularMenu();
            thisRegularMenu.mainRegularMenu(connection, username);

        }

    }
}


