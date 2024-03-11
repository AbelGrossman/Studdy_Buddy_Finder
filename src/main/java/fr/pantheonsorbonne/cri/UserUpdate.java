package fr.pantheonsorbonne.cri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class UserUpdate {
    private static final String DB_URL = "jdbc:mysql://localhost:8887/study_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    public static void updateUserProfile(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Menu de modification du profil :");
        System.out.println("1. Modifier le prénom");
        System.out.println("2. Modifier le nom");
        System.out.println("3. Modifier le nom d'utilisateur");
        System.out.println("4. Modifier le mot de passe");
        System.out.println("5. Modifier le lieu de résidence (1)");
        System.out.println("6. Modifier le lieu de résidence (2)");
        System.out.println("7. Modifier le centre d'intérêt (1)");
        System.out.println("8. Modifier le centre d'intérêt (2)");
        System.out.println("9. Modifier la filière d'études");
        System.out.println("0. Quitter");

        System.out.print("Choisissez l'option que vous souhaitez modifier : ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Pour vider le buffer du scanner

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            switch (choice) {
                case 1:
                    System.out.print("Nouveau prénom : ");
                    String firstName = scanner.nextLine();
                    user.setFirstName(firstName);
                    break;
                case 2:
                    System.out.print("Nouveau nom : ");
                    String lastName = scanner.nextLine();
                    user.setLastName(lastName);
                    break;
                case 3:
                    System.out.print("Nouveau nom d'utilisateur : ");
                    String userName = scanner.nextLine();
                    user.setUserName(userName);
                    break;
                case 4:
                    System.out.print("Nouveau mot de passe : ");
                    String userPassword = scanner.nextLine();
                    user.setUserPassword(userPassword);
                    break;
                case 5:
                    System.out.print("Nouveau lieu de résidence (1) : ");
                    String location1 = scanner.nextLine();
                    user.setLocation1(location1);
                    break;
                case 6:
                    System.out.print("Nouveau lieu de résidence (2) : ");
                    String location2 = scanner.nextLine();
                    user.setLocation2(location2);
                    break;
                case 7:
                    System.out.print("Nouveau centre d'intérêt (1) : ");
                    String interest1 = scanner.nextLine();
                    user.setInterest1(interest1);
                    break;
                case 8:
                    System.out.print("Nouveau centre d'intérêt (2) : ");
                    String interest2 = scanner.nextLine();
                    user.setInterest2(interest2);
                    break;
                case 9:
                    System.out.print("Nouvelle filière d'études : ");
                    String userStudies = scanner.nextLine();
                    user.setUserStudies(userStudies);
                    break;
                case 0:
                    System.out.println("Modification du profil annulée.");
                    return;
                default:
                    System.out.println("Option invalide.");
                    return;
            }

            // Mettre à jour le profil utilisateur dans la base de données
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE user SET first_name=?, last_name=?, user_name=?, user_email=?, user_password=?, location_1=?, location_2=?, interest_1=?, interest_2=?, user_studies=? WHERE user_id=?")) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getUserName());
                preparedStatement.setString(4, user.getUserPassword());
                preparedStatement.setString(5, user.getLocation1());
                preparedStatement.setString(6, user.getLocation2());
                preparedStatement.setString(7, user.getInterest1());
                preparedStatement.setString(8, user.getInterest2());
                preparedStatement.setString(9, user.getUserStudies());
                preparedStatement.setInt(10, user.getUserId());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Profil mis à jour avec succès !");
                } else {
                    System.out.println("Erreur lors de la mise à jour du profil.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
