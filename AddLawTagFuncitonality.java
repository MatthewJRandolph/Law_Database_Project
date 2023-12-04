import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddLawTagFuncitonality {
    public void addLawTag(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Law ID: ");
        int lawId = scanner.nextInt();

        System.out.println("Enter the Tag ID: ");
        int tagId = scanner.nextInt();

        try {
            String sql = "INSERT INTO LawTag (l_lawid, l_tagid) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, lawId);
            preparedStatement.setInt(2, tagId);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("LawTag added successfully!");
            } else {
                System.out.println("LawTag could not be added.");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
