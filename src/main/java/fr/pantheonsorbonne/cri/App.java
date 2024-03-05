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
        String url = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
        String username = "root";
        String password = "";
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from User");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + "" +
                        resultSet.getString(2) + "" + resultSet.getString(3) + ""
                        + resultSet.getString(4) + "" + resultSet.getString(5) + "" + resultSet.getString(6));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
