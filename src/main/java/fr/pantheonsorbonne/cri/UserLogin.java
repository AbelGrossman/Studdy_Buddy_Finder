package fr.pantheonsorbonne.cri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserLogin {
    private static final String URL = "jdbc:mysql://localhost:8887/study_buddy_finder";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenue dans votre application de connexion !");
        System.out.println("----------------------------------------------");

        // Demander à l'utilisateur de saisir son nom d'utilisateur et son mot de passe
        System.out.print("Nom d'utilisateur : ");
        String username = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        // Vérifier les informations d'identification
        if (login(username, password)) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("Nom d'utilisateur ou mot de passe incorrect !");
        }

        scanner.close();
    }

    // Méthode pour vérifier les informations d'identification de l'utilisateur dans la base de données
    public static boolean login(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Établir la connexion à la base de données
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Préparer la requête SQL
            String sql = "SELECT * FROM user WHERE user_name = ? AND user_password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Exécuter la requête
            resultSet = preparedStatement.executeQuery();

            // Vérifier si un utilisateur correspondant a été trouvé
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Fermer les ressources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
