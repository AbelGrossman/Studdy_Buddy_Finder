package fr.pantheonsorbonne.cri;

import fr.pantheonsorbonne.cri.ModelFolder.DatabaseConnection;
import fr.pantheonsorbonne.cri.UserFolder.*;
import fr.pantheonsorbonne.cri.CasesFolder.*;

import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean running = true;
    private static User currentUser = null;

    public static void main(String[] args) {
        DatabaseConnection.dataBaseConnect();

        System.out.println("Welcome to Studdy Buddy Finder !");
        System.out.println("--------------------------------");

        while (running) {
            // Afficher le menu
            System.out.println("Login Menu:");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("0. Leave App");

            // Demander à l'utilisateur de choisir une option
            System.out.println("Please choose an option:");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                // Effectuer une action en fonction du choix de l'utilisateur
                switch (choice) {
                    case 1:
                        // Code pour l'inscription
                        System.out.println("You chose to Sign Up.");
                        UserSignup.signup();
                        break;
                    case 2:
                        // Appeler le main de UserLogin pour la connexion
                        System.out.println("You chose to Login.");
                        currentUser = UserLogin.login();
                        if (currentUser != null) {
                            System.out.println(currentUser.getUserName() + " is connected.");
                        }
                        break;
                    case 0:
                        System.out.println("Thank you for using the app! See you soon!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a valid option.");
                        continue;
                }
                if (currentUser != null) {
                    LoggedInCases.loggedInCases(currentUser, running);
                    if (LoggedInCases.getReturnValue() == 5) {
                        currentUser = null;
                    } else {
                        System.out.println("Thank you for using the app! See you soon!");
                        running = false;
                    }
                }
            } else {
                System.out.println("Invalid input. Please input an integer.");
                scanner.nextLine(); // Pour vider le buffer du scanner
            }
        }
    }
}
