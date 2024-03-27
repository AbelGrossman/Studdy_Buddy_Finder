package fr.pantheonsorbonne.cri.UserFolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDelete {
    // private static final String DB_URL =
    // "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/study_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    private UserDelete() {
        throw new IllegalStateException("Utility class");
    }

    public static void deleteAccount(User user) {
        // Supprimer l'utilisateur de la base de données
        if (deleteUser(user.getUserName(), user.getUserPassword())) {
            System.out.println("Account deleted successfully!");
        } else {
            System.out.println("Error deleting account. Please try again.");
        }
    }

    // Méthode pour supprimer un utilisateur de la base de données
    public static boolean deleteUser(String userName, String userPassword) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Vérifier si l'utilisateur existe et le mot de passe correspond
            String checkUserQuery = "SELECT COUNT(*) FROM User WHERE user_name = ? AND user_password = ?";
            try (PreparedStatement checkUserStatement = connection.prepareStatement(checkUserQuery)) {
                checkUserStatement.setString(1, userName);
                checkUserStatement.setString(2, userPassword);
                try (ResultSet resultSet = checkUserStatement.executeQuery()) {
                    if (!resultSet.next() || resultSet.getInt(1) == 0) {
                        // Aucun utilisateur avec ce nom d'utilisateur et ce mot de passe
                        return false;
                    }
                }
            }

            // Supprimer l'utilisateur de la base de données
            String deleteQuery = "DELETE FROM User WHERE user_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, userName);
                int rowsAffected = preparedStatement.executeUpdate();
                // Vérifier si aucune ligne n'a été affectée (aucun utilisateur supprimé)
                if (rowsAffected == 0) {
                    return false;
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
            return false;
        }
    }

}
