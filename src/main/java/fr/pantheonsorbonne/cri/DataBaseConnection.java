package fr.pantheonsorbonne.cri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class DataBaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:8887/study_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public static void dataBaseConnect() {

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement statement = connection.createStatement();
            createDatabase(statement);
            ResultSet resultSet = statement.executeQuery("select * from User");
            while (resultSet.next()) {
                System.out.println("index: " + resultSet.getInt(1) + "\n" + "first name: " +
                        resultSet.getString(2) + "\n" + "last name: " + resultSet.getString(3) + "\n" + "user name: " +
                        resultSet.getString(4) + "\n" + "email adress: " + resultSet.getString(5) + "\n" + "password: "
                        + resultSet.getString(6));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void createDatabase(Statement statement) throws Exception {
        String createUserTableQuery = "CREATE TABLE IF NOT EXISTS User ("
                + "user_id INT PRIMARY KEY AUTO_INCREMENT,"
                + "first_name VARCHAR(100),"
                + "last_name VARCHAR(100),"
                + "user_name VARCHAR(50) UNIQUE,"
                + "user_email VARCHAR(100) UNIQUE,"
                + "user_password VARCHAR(50),"
                + "location_1 VARCHAR(100),"
                + "location_2 VARCHAR(100),"
                + "interest_1 VARCHAR(100),"
                + "interest_2 VARCHAR(100),"
                + "user_studdies VARCHAR(100)"
                + ")";
        String createGroupTableQuery = "CREATE TABLE IF NOT EXISTS Group ("
                + "group_id INT PRIMARY KEY AUTO_INCREMENT,"
                + "group_name VARCHAR(100),"
                + "study_domain VARCHAR(100),"
                + "admin_id INT,"
                + "FOREIGN KEY (admin_id) REFERENCES User(user_id)"
                + ")";
        String createGroupMembersTableQuery = "CREATE TABLE IF NOT EXISTS GroupMembers ("
                + "group_id INT,"
                + "user_id INT,"
                + "FOREIGN KEY (group_id) REFERENCES `Group`(group_id),"
                + "FOREIGN KEY (user_id) REFERENCES User(user_id),"
                + "PRIMARY KEY (group_id, user_id)"
                + ")";
        String createStuddyBuddiesTableQuery = "CREATE TABLE IF NOT EXISTS StuddyBuddies ("
                + "user_id INT,"
                + "studdy_buddy_id INT,"
                + "FOREIGN KEY (user_id) REFERENCES User(user_id),"
                + "FOREIGN KEY (studdy_buddy_id) REFERENCES User(user_id),"
                + "PRIMARY KEY (user_id, studdy_buddy_id)"
                + ")";
        statement.executeUpdate(createUserTableQuery);
        statement.executeUpdate(createGroupTableQuery);
        statement.executeUpdate(createGroupMembersTableQuery);
        statement.executeUpdate(createStuddyBuddiesTableQuery);
    }
}
