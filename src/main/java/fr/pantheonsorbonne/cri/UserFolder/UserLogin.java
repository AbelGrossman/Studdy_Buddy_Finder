package fr.pantheonsorbonne.cri.UserFolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import fr.pantheonsorbonne.cri.StuddyBuddyFolder.FindUser;

public class UserLogin {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/study_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    private UserLogin() {
        throw new IllegalStateException("Utility class");
    }

    public static User login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenue dans votre application de connexion !");
        System.out.println("----------------------------------------------");

        // Demander à l'utilisateur de saisir son nom d'utilisateur et son mot de passe
        // dans le terminal
        System.out.print("Nom d'utilisateur : ");
        String userName = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String userPassword = scanner.nextLine();

        // Vérifier les informations d'identification
        if (login(userName, userPassword)) {
            System.out.println("Connexion réussie !");
            return FindUser.getUserByUsername(userName);
        } else {
            System.out.println("Nom d'utilisateur ou mot de passe incorrect. Réessayez ou inscrivez-vous.");
        }
        return null;
        // il ne faut pas close le scanner sinon ca provoque un bug dans le menu
        // principal.
        // scanner.close();
    }

    // Méthode pour vérifier les informations d'identification de l'utilisateur dans
    // la base de données
    public static boolean login(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement preparedStatement = connection
                        .prepareStatement("SELECT * FROM user WHERE user_name = ? AND user_password = ?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
