import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InteractiveQueries {
    public static void query1(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the first category: ");
        String category1 = scanner.nextLine();

        System.out.println("Enter the second category: ");
        String category2 = scanner.nextLine();

        try {
            String sql = "SELECT DISTINCT u.u_username " +
                         "FROM User u " +
                         "JOIN Comment c ON u.u_userid = c.c_userid " +
                         "JOIN Law l ON c.c_lawid = l.l_lawid " +
                         "JOIN LawCategory lc ON l.l_lawid = lc.lc_lawid " +
                         "JOIN Category cat ON lc.lc_categoryid = cat.c_categoryid " +
                         "WHERE cat.c_categoryname IN (?, ?) " +
                         "GROUP BY u.u_username";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category1);
            preparedStatement.setString(2, category2);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> users = new ArrayList<>();
            while (resultSet.next()) {
                String username = resultSet.getString("u_username");
                users.add(username);
            }

            if (users.isEmpty()) {
                System.out.println("No users found for the specified categories.");
            } else {
                System.out.println("Users who commented on laws in both categories:");
                for (String user : users) {
                    System.out.println(user);
                }
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void query2(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the category: ");
        String category = scanner.nextLine();

        try {
            String sql = "SELECT DISTINCT l.l_title, GROUP_CONCAT(DISTINCT t.t_tagname) AS Tags " +
                         "FROM Law l " +
                         "LEFT JOIN LawTag lt ON l.l_lawid = lt.l_lawid " +
                         "LEFT JOIN Tag t ON lt.l_tagid = t.t_tagid " +
                         "WHERE l.l_lawid IN ( " +
                         "    SELECT lc.lc_lawid " +
                         "    FROM LawCategory lc " +
                         "    JOIN Category c ON lc.lc_categoryid = c.c_categoryid " +
                         "    WHERE c.c_categoryname <> ? " +
                         ") " +
                         "GROUP BY l.l_lawid";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> lawInfo = new ArrayList<>();
            while (resultSet.next()) {
                String lawTitle = resultSet.getString("l_title");
                String tags = resultSet.getString("Tags");
                lawInfo.add("Law Title: " + lawTitle + ", Tags: " + tags);
            }

            if (lawInfo.isEmpty()) {
                System.out.println("No laws found for the specified category: " + category );
            } else {
                System.out.println("Laws with associated tags not in the specified category: " + category);
                for (String info : lawInfo) {
                    System.out.println(info);
                }
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void query3(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your username: ");
        String username = scanner.nextLine();

        try {
            String sql = "SELECT u.u_username, GROUP_CONCAT(DISTINCT t.t_tagname) AS Tags " +
                         "FROM User u " +
                         "JOIN Comment c ON u.u_userid = c.c_userid " +
                         "JOIN Law l ON c.c_lawid = l.l_lawid " +
                         "JOIN LawTag lt ON l.l_lawid = lt.l_lawid " +
                         "JOIN Tag t ON lt.l_tagid = t.t_tagid " +
                         "WHERE u.u_username = ? " +
                         "GROUP BY u.u_username";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> userTags = new ArrayList<>();
            while (resultSet.next()) {
                String tags = resultSet.getString("Tags");
                userTags.add(tags);
            }

            if (userTags.isEmpty()) {
                System.out.println("No tags found for the specified user.");
            } else {
                System.out.println("Tags associated with your comments:");
                for (String tags : userTags) {
                    System.out.println(tags);
                }
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void query4(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a date (e.g., 2023-01-01): ");
        String inputDate = scanner.nextLine();

        try {
            String sql = "SELECT u.u_username, l.l_title, c.c_commenttext " +
                         "FROM Comment c " +
                         "JOIN Law l ON c.c_lawid = l.l_lawid " +
                         "JOIN User u ON c.c_userid = u.u_userid " +
                         "WHERE l.l_dateenacted > ? " +
                         "ORDER BY l.l_dateenacted";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, inputDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("u_username");
                String lawTitle = resultSet.getString("l_title");
                String commentText = resultSet.getString("c_commenttext");

                System.out.println("User: " + username);
                System.out.println("Law Title: " + lawTitle);
                System.out.println("Comment: " + commentText);
                System.out.println("--------------------------------------------------");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void query5(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Law Title: ");
        String lawTitle = scanner.nextLine();

        try {
            String sql = "SELECT DISTINCT u.u_username " +
                         "FROM User u " +
                         "JOIN Comment c ON u.u_userid = c.c_userid " +
                         "JOIN Law l ON c.c_lawid = l.l_lawid " +
                         "WHERE l.l_title = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, lawTitle);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Users who commented on the law '" + lawTitle + "':");
                while (resultSet.next()) {
                    String username = resultSet.getString("u_username");
                    System.out.println(username);
                }
            } else {
                System.out.println("No comments found for the specified law title.");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void query6(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of top commented laws (N): ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        try {
            String sql = "SELECT l.l_title, COUNT(c.c_commenttext) AS CommentCount " +
                         "FROM Law l " +
                         "LEFT JOIN Comment c ON l.l_lawid = c.c_lawid " +
                         "GROUP BY l.l_title " +
                         "ORDER BY CommentCount DESC " +
                         "LIMIT ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, n);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Top " + n + " most commented laws:");
                while (resultSet.next()) {
                    String lawTitle = resultSet.getString("l_title");
                    int commentCount = resultSet.getInt("CommentCount");
                    System.out.println("Law Title: " + lawTitle + ", Comment Count: " + commentCount);
                }
            } else {
                System.out.println("No laws found.");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
