import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddTagFunctionality {
    public void addTag(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the tag: ");
        String tagName = scanner.nextLine();

        try {
            String sql = "INSERT INTO Tag (t_tagname) VALUES (?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tagName);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Tag added successfully!");
            } else {
                System.out.println("Tag could not be added.");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
