package fr.pantheonsorbonne.cri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDDNoam {

    // Informations de connexion à la base de données
    private static final String URL = "jdbc:mysql://localhost:8887/study_buddy_finder";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Chargement du driver JDBC (automatique pour Java 8+)
            // Class.forName("com.mysql.cj.jdbc.Driver");

            // Établissement de la connexion
            System.out.println("Connexion à la base de données...");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Vérification de la connexion
            if (connection != null) {
                System.out.println("Connexion établie avec succès !");
                // Exécutez vos requêtes SQL ici
            } else {
                System.out.println("Échec de la connexion !");
            }
        } catch (SQLException e) {
            // Gestion des exceptions SQL
            System.out.println("Erreur de connexion : " + e.getMessage());
        } finally {
            // Fermeture de la connexion
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
                }
            }
        }
    }
}

