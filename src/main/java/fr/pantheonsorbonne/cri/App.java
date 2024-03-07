package fr.pantheonsorbonne.cri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * The main class for the application.
 */
public final class App {

    /**
     * main entrypoint for my class.
     * 
     * @param args a bunch of string from the cli
     */
    public static void main(final String[] args) {
        dataBaseConnect();
    }

    private static void dataBaseConnect() {
        String url = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
        String username = "root";
        String password = "";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
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
                + "location_3 VARCHAR(100),"
                + "interest_1 VARCHAR(100),"
                + "interest_2 VARCHAR(100),"
                + "interest_3 VARCHAR(100),"
                + "user_studdies VARCHAR(100)"
                + ")";
        statement.executeUpdate(createUserTableQuery);
    }
}
