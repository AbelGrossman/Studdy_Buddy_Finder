package fr.pantheonsorbonne.cri.ControllerFolder;

import fr.pantheonsorbonne.cri.UserFolder.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserController {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private UserController() {
        throw new IllegalStateException("Utility class");
    }

    public static void updateUserProfile(User user, Scanner scanner) {
        boolean modifying = true;
        while (modifying) {
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
            System.out.println("10. Supprimer le profil");
            System.out.println("0. Quitter le menu de modification du profil");

            System.out.println("Choisissez l'option que vous souhaitez modifier : ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Pour consommer la nouvelle ligne restante après nextInt()
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
                    case 10:
                        System.out.print("Are you sure you want to delete your account? (true/false)");
                        boolean confirmation = scanner.nextBoolean();
                        if (confirmation) {
                            UserDelete.deleteAccount(user);
                        } else {
                            System.out.println("Account deletion cancelled.");
                        }
                        break;
                    case 0:
                        System.out.println("Leaving profil menu.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                    // Mettre à jour le profil utilisateur dans la base de données
                    String updateQuery = "UPDATE user SET first_name=?, last_name=?, user_name=?, user_email=?, user_password=?, location_1=?, location_2=?, interest_1=?, interest_2=?, user_studies=? WHERE user_id=?";
                    try (PreparedStatement emailCheck = connection
                            .prepareStatement("SELECT COUNT(*) FROM user WHERE user_email = ?")) {
                        emailCheck.setString(1, user.getUserEmail());
                        try (ResultSet emailCheckResult = emailCheck.executeQuery()) {
                            if (emailCheckResult.next() && emailCheckResult.getInt(1) > 0) {
                                System.out.println("L'adresse e-mail est déjà utilisée.");
                                continue;
                            }
                        }
                    }
                    // Vérifier si le nom d'utilisateur est déjà pris
                    try (PreparedStatement usernameCheck = connection
                            .prepareStatement("SELECT COUNT(*) FROM user WHERE user_name = ?")) {
                        usernameCheck.setString(1, user.getUserName());
                        try (ResultSet usernameCheckResult = usernameCheck.executeQuery()) {
                            if (usernameCheckResult.next() && usernameCheckResult.getInt(1) > 0) {
                                System.out.println("Le nom d'utilisateur est déjà pris.");
                                continue;
                            }
                        }
                    }
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                        preparedStatement.setString(1, user.getFirstName());
                        preparedStatement.setString(2, user.getLastName());
                        preparedStatement.setString(3, user.getUserName());
                        preparedStatement.setString(4, user.getUserEmail());
                        preparedStatement.setString(5, user.getUserPassword());
                        preparedStatement.setString(6, user.getLocation1());
                        preparedStatement.setString(7, user.getLocation2());
                        preparedStatement.setString(8, user.getInterest1());
                        preparedStatement.setString(9, user.getInterest2());
                        preparedStatement.setString(10, user.getUserStudies());
                        preparedStatement.setInt(11, user.getUserId());
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Profil mis à jour avec succès !");
                        } else {
                            System.out.println("Erreur lors de la mise à jour du profil.");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Invalid input. Please input an integer.");
                scanner.nextLine(); // Pour vider le buffer du scanner
            }
        }
    }
}
