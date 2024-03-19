package fr.pantheonsorbonne.cri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class UserSignup {
    private static final String DB_URL = "jdbc:mysql://localhost:8887/study_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    private static List<User> registeredUsers = new ArrayList<>();

    public static void signup() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue dans votre application d'inscription !");
        System.out.println("----------------------------------------------");

        // Demander à l'utilisateur de saisir ses informations
        System.out.print("Prénom : ");
        String firstName = scanner.nextLine();

        System.out.print("Nom : ");
        String lastName = scanner.nextLine();

        System.out.print("Nom d'utilisateur : ");
        String userName = scanner.nextLine();

        System.out.print("Adresse e-mail : ");
        String userEmail = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String userPassword = scanner.nextLine();

        System.out.println("Veuillez saisir jusqu'à deux lieux de résidence : ");
        String location1 = scanner.nextLine();
        String location2 = scanner.nextLine();

        System.out.println("Veuillez saisir jusqu'à deux centres d'intérêt : ");
        String interest1 = scanner.nextLine();
        String interest2 = scanner.nextLine();

        System.out.print("Veuillez saisir votre filière d'études : ");
        String userStudies = scanner.nextLine();

        // Enregistrer l'utilisateur dans la base de données
        if (registerUser(firstName, lastName, userName, userEmail, userPassword, location1, location2,
                interest1, interest2, userStudies)) {
            System.out.println("Inscription réussie !");
            addRegisteredUser(new User(firstName, lastName, userName, userEmail, userPassword, location1, location2,
                    interest1,
                    interest2, userStudies));
        } else {
            System.out.println("Erreur lors de l'inscription. Veuillez réessayer.");
        }
        // il ne faut pas close le scanner sinon ca provoque un bug dans le menu
        // principal.
        // scanner.close();
    }

    // Méthode pour enregistrer un nouvel utilisateur dans la base de données
    public static boolean registerUser(String firstName, String lastName, String userName, String userEmail,
            String userPassword, String location1, String location2, String interest1,
            String interest2, String userStudies) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Vérifier si l'adresse e-mail est déjà utilisée
            // preparedStatements utilisé contre injection SQL
            try (PreparedStatement emailCheck = connection
                    .prepareStatement("SELECT COUNT(*) FROM user WHERE user_email = ?")) {
                emailCheck.setString(1, userEmail);
                try (ResultSet emailCheckResult = emailCheck.executeQuery()) {
                    if (emailCheckResult.next() && emailCheckResult.getInt(1) > 0) {
                        System.out.println("L'adresse e-mail est déjà utilisée.");
                        return false;
                    }
                }
            }

            // Vérifier si le nom d'utilisateur est déjà pris
            try (PreparedStatement usernameCheck = connection
                    .prepareStatement("SELECT COUNT(*) FROM user WHERE user_name = ?")) {
                usernameCheck.setString(1, userName);
                try (ResultSet usernameCheckResult = usernameCheck.executeQuery()) {
                    if (usernameCheckResult.next() && usernameCheckResult.getInt(1) > 0) {
                        System.out.println("Le nom d'utilisateur est déjà pris.");
                        return false;
                    }
                }
            }

            // Insérer l'utilisateur dans la base de données
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO user (first_name, last_name, user_name, user_email, user_password, location_1, location_2, interest_1, interest_2,  user_studdies) VALUES (?,?,?,?,?,?,?,?,?,?)")) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, userName);
                preparedStatement.setString(4, userEmail);
                preparedStatement.setString(5, userPassword);
                preparedStatement.setString(6, location1);
                preparedStatement.setString(7, location2);
                preparedStatement.setString(8, interest1);
                preparedStatement.setString(9, interest2);
                preparedStatement.setString(10, userStudies);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void addRegisteredUser(User user) {
        registeredUsers.add(user);
    }

    public static User getRegisteredUserByUsername(String username) {
        for (User user : registeredUsers) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }

}
