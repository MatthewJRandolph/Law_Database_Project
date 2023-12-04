import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UserRegistration {
    private final Connection connection;

    public UserRegistration(Connection connection) {
        this.connection = connection;
    }

    public void registerUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Are you an Admin or Regular? (Admin | Regular): ");
        String userType = scanner.nextLine().trim();

        System.out.print("Input a username: ");
        String username = scanner.nextLine().trim();

        String adminString = "Admin";

        if (!userType.equalsIgnoreCase("Admin") && !userType.equalsIgnoreCase("Regular")) {
            System.out.println("Invalid user type.");
            return;
        }

        // Insert the new user into the database
        if (insertUser(userType, username)) {
            System.out.println("User registered successfully.");
            if (userType.equalsIgnoreCase(adminString)) {
                // Add admin username to the authentication set
                Menu thisMenu = new Menu();
                AuditLogsFunctionality thisAuditLogsFunctionality = new AuditLogsFunctionality();
                thisAuditLogsFunctionality.insertAuditLog(connection, username);
                thisMenu.mainMenu(connection, username);
            }
        } else {
            System.out.println("User registration failed.");
        }
    }

    private boolean insertUser(String userType, String username) {
        try {
            String sql = "INSERT INTO User (u_username, u_usertype) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, userType);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
