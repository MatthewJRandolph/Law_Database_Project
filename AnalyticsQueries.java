import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnalyticsQueries {

    public static void query1(Connection connection) {
        try {
            String sql = "SELECT l.l_title, GROUP_CONCAT(DISTINCT t.t_tagname) AS Tags, u.u_username AS CommentedBy " +
                         "FROM Law l " +
                         "LEFT JOIN ( " +
                         "  SELECT l.l_lawid, t.t_tagname " +
                         "  FROM Law l " +
                         "  LEFT JOIN LawTag lt ON l.l_lawid = lt.l_lawid " +
                         "  LEFT JOIN Tag t ON lt.l_tagid = t.t_tagid " +
                         ") t ON l.l_lawid = t.l_lawid " +
                         "LEFT JOIN Comment c ON l.l_lawid = c.c_lawid " +
                         "LEFT JOIN User u ON c.c_userid = u.u_userid " +
                         "GROUP BY l.l_title, u.u_username " +
                         "ORDER BY l.l_title";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Titles of laws along with associated tags and usernames of users who commented on them:");
                while (resultSet.next()) {
                    String lawTitle = resultSet.getString("l_title");
                    String tags = resultSet.getString("Tags");
                    String commentedBy = resultSet.getString("CommentedBy");
                    System.out.println("Law Title: " + lawTitle);
                    System.out.println("Tags: " + tags);
                    System.out.println("Commented By: " + commentedBy);
                    System.out.println("--------------------------------------------------");
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

    public static void query2(Connection connection) {
        try {
            String sql = "SELECT l.l_title " +
                         "FROM Law l " +
                         "LEFT JOIN Comment c ON l.l_lawid = c.c_lawid " +
                         "LEFT JOIN LawCategory lc ON l.l_lawid = lc.lc_lawid " +
                         "WHERE c.c_commentid IS NULL AND lc.lc_categoryid IS NULL";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Laws with no associated comments and not assigned to any category:");
                while (resultSet.next()) {
                    String lawTitle = resultSet.getString("l_title");
                    System.out.println("Law Title: " + lawTitle);
                    System.out.println("--------------------------------------------------");
                }
            } else {
                System.out.println("No such laws found.");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void query3(Connection connection) {
        try {
            String sql = "SELECT cat.c_categoryname, COUNT(DISTINCT l.l_lawid) AS NumberOfLaws, " +
                         "AVG(CommentCount) AS AverageCommentsPerLaw " +
                         "FROM Category cat " +
                         "JOIN LawCategory lc ON cat.c_categoryid = lc.lc_categoryid " +
                         "JOIN Law l ON lc.lc_lawid = l.l_lawid " +
                         "LEFT JOIN ( " +
                         "    SELECT c.c_lawid, COUNT(c.c_commenttext) AS CommentCount " +
                         "    FROM Comment c " +
                         "    GROUP BY c.c_lawid " +
                         ") AS CommentData ON l.l_lawid = CommentData.c_lawid " +
                         "GROUP BY cat.c_categoryname";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Category-wise statistics:");
                while (resultSet.next()) {
                    String categoryName = resultSet.getString("c_categoryname");
                    int numberOfLaws = resultSet.getInt("NumberOfLaws");
                    double averageCommentsPerLaw = resultSet.getDouble("AverageCommentsPerLaw");

                    System.out.println("Category: " + categoryName);
                    System.out.println("Number of Laws: " + numberOfLaws);
                    System.out.println("Average Comments per Law: " + averageCommentsPerLaw);
                    System.out.println("--------------------------------------------------");
                }
            } else {
                System.out.println("No data found for categories.");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void query4(Connection connection) {
        try {
            String sql = "SELECT cat.c_categoryname, COUNT(c.c_commentid) AS CommentCount " +
                         "FROM Category cat " +
                         "JOIN LawCategory lc ON cat.c_categoryid = lc.lc_categoryid " +
                         "JOIN Law l ON lc.lc_lawid = l.l_lawid " +
                         "JOIN Comment c ON l.l_lawid = c.c_lawid " +
                         "GROUP BY cat.c_categoryname " +
                         "ORDER BY CommentCount DESC " +
                         "LIMIT 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    String categoryName = resultSet.getString("c_categoryname");
                    int commentCount = resultSet.getInt("CommentCount");

                    System.out.println("Category with Most Comments:");
                    System.out.println("Category: " + categoryName);
                    System.out.println("Total Comments: " + commentCount);
                }
            } else {
                System.out.println("No data found for categories.");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void query5(Connection connection) {
        try {
            String sql = "SELECT l.l_title, COUNT(c.c_commenttext) AS CommentCount " +
                         "FROM Law l " +
                         "LEFT JOIN Comment c ON l.l_lawid = c.c_lawid " +
                         "GROUP BY l.l_title " +
                         "ORDER BY CommentCount DESC " +
                         "LIMIT 3";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    String lawTitle = resultSet.getString("l_title");
                    int commentCount = resultSet.getInt("CommentCount");

                    System.out.println("Laws with Most Comments:");
                    System.out.println("Law Title: " + lawTitle);
                    System.out.println("Total Comments: " + commentCount);
                }
            } else {
                System.out.println("No data found for laws with comments.");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void query6(Connection connection) {
        try {
            String sql = "SELECT u.u_usertype, AVG(LENGTH(c.c_commenttext)) AS AverageCommentLength " +
                         "FROM User u " +
                         "JOIN Comment c ON u.u_userid = c.c_userid " +
                         "GROUP BY u.u_usertype";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    String userType = resultSet.getString("u_usertype");
                    double averageCommentLength = resultSet.getDouble("AverageCommentLength");

                    System.out.println("User Type: " + userType);
                    System.out.println("Average Comment Length: " + averageCommentLength);
                }
            } else {
                System.out.println("No data found for average comment length by user type.");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void query7(Connection connection) {
        try {
            String sql = "SELECT u.u_username, GROUP_CONCAT(DISTINCT cat.c_categoryname) AS CommentedCategories " +
                         "FROM User u " +
                         "JOIN Comment c ON u.u_userid = c.c_userid " +
                         "JOIN Law l ON c.c_lawid = l.l_lawid " +
                         "JOIN LawCategory lc ON l.l_lawid = lc.lc_lawid " +
                         "JOIN Category cat ON lc.lc_categoryid = cat.c_categoryid " +
                         "GROUP BY u.u_userid " +
                         "HAVING COUNT(DISTINCT cat.c_categoryname) > 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("u_username");
                    String commentedCategories = resultSet.getString("CommentedCategories");

                    System.out.println("Username: " + username);
                    System.out.println("Commented Categories: " + commentedCategories);
                }
            } else {
                System.out.println("No users found who commented on multiple categories.");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void query8(Connection connection) {
        try {
            String sql = "SELECT u.u_username, GROUP_CONCAT(DISTINCT l.l_title) AS LawTitles, COUNT(c.c_commenttext) AS TotalComments " +
                         "FROM User u " +
                         "JOIN Comment c ON u.u_userid = c.c_userid " +
                         "JOIN Law l ON c.c_lawid = l.l_lawid " +
                         "GROUP BY u.u_username " +
                         "HAVING COUNT(DISTINCT l.l_lawid) > 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("u_username");
                    String lawTitles = resultSet.getString("LawTitles");
                    int totalComments = resultSet.getInt("TotalComments");

                    System.out.println("Username: " + username);
                    System.out.println("Law Titles: " + lawTitles);
                    System.out.println("Total Comments: " + totalComments);
                }
            } else {
                System.out.println("No users found who commented on multiple laws.");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void query9(Connection connection) {
        try {
            String sql = "SELECT l.l_title " +
                         "FROM Law l " +
                         "LEFT JOIN LawCategory lc ON l.l_lawid = lc.lc_lawid " +
                         "LEFT JOIN LawTag lt ON l.l_lawid = lt.l_lawid " +
                         "WHERE l.l_lawid NOT IN (SELECT c_lawid FROM Comment) " +
                         "AND lc.lc_categoryid IS NULL " +
                         "AND lt.l_tagid IS NULL";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Laws with no associated comments and not assigned to any category or tag:");
                while (resultSet.next()) {
                    String lawTitle = resultSet.getString("l_title");
                    System.out.println(lawTitle);
                }
            } else {
                System.out.println("No laws found with the specified criteria.");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
