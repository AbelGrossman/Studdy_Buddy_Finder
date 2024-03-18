package fr.pantheonsorbonne.cri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FindFriend {
    private static final String DB_URL = "jdbc:mysql://localhost:8887/study_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Recherche d'ami par nom d'utilisateur !");
        System.out.println("----------------------------------------");

        // Demander à l'utilisateur de saisir le nom d'utilisateur de l'ami à rechercher
        System.out.print("Entrez le nom d'utilisateur de votre ami : ");
        String friendUsername = scanner.nextLine();

        // Rechercher l'ami dans la base de données
        findFriendByUsername(friendUsername, scanner);

        scanner.close();
    }

    static void findFriendByUsername(String username, Scanner scanner) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT first_name, last_name, interest_1, interest_2 FROM user WHERE user_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("Ami trouvé !");
                        System.out.println("Nom : " + resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                        System.out.println("Centre d'intérêt 1 : " + resultSet.getString("interest_1"));
                        System.out.println("Centre d'intérêt 2 : " + resultSet.getString("interest_2"));

                        // Demander à l'utilisateur s'il veut ajouter l'ami en tant qu'ami
                //il faut implémenter ca pour permettre d'ajouter des amis genre
                //on pourra faire une classe AddFriend qui va ajouter un ami
                        System.out.print("Voulez-vous ajouter cet utilisateur en tant qu'ami ? (oui/non) : ");
                        String choice = scanner.nextLine().toLowerCase();
                        if (choice.equals("oui")) {
                            System.out.println("Ami ajouté avec succès !");
                            // Code pour ajouter l'ami à la liste d'amis
                        } else {
                            System.out.println("Ami non ajouté.");
                        }
                    } else {
                        System.out.println("Aucun utilisateur trouvé avec le nom d'utilisateur spécifié.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

