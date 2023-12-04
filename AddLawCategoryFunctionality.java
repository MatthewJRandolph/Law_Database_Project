import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddLawCategoryFunctionality {
    public void addLawCategory(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Law ID: ");
        int lawId = scanner.nextInt();

        System.out.println("Enter the Category ID: ");
        int categoryId = scanner.nextInt();

        try {
            String sql = "INSERT INTO LawCategory (lc_lawid, lc_categoryid) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, lawId);
            preparedStatement.setInt(2, categoryId);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("LawCategory added successfully!");
            } else {
                System.out.println("LawCategory could not be added.");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
