package fr.pantheonsorbonne.cri;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDeletion {
    private static final String DB_URL = "jdbc:mysql://localhost:8887/study_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Demander à l'utilisateur de saisir son nom d'utilisateur
        System.out.print("Nom d'utilisateur : ");
        String userName = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String userPassword = scanner.nextLine();

        // Supprimer l'utilisateur de la base de données
        if (deleteUser(userName, userPassword)) {
            System.out.println("Compte supprimé avec succès !");
        } else {
            System.out.println("Erreur lors de la suppression du compte. Veuillez réessayer.");
        }
    }

    // Méthode pour supprimer un utilisateur de la base de données
    static boolean deleteUser(String userName, String userPassword) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM User WHERE user_name = ? AND user_password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, userName);
                preparedStatement.setString(2, userPassword);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting user from the database" + e.getMessage());
            return false;
        }
    }

}
