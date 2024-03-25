package fr.pantheonsorbonne.cri.CasesFolder;

import fr.pantheonsorbonne.cri.UserFolder.*;
import fr.pantheonsorbonne.cri.ControllerFolder.GroupController;
import fr.pantheonsorbonne.cri.ControllerFolder.UserController;

import java.util.Scanner;

public class LoggedInCases {
    private static int returnValue = 0;

    private LoggedInCases() {
        throw new IllegalStateException("Utility class");
    }

    public static void loggedInCases(User currentUser, boolean running, Scanner scanner) {
        boolean runningLogin = true;
        while (runningLogin) {
            System.out.println("Menu: ");
            System.out.println("1. Modify Profile");
            System.out.println("2. Create Group");
            System.out.println("3. Search User");
            System.out.println("4. Select Group");
            System.out.println("5. Logout");
            System.out.println("0. Leave App");

            // Demander à l'utilisateur de choisir une option
            System.out.println("Please choose an option:");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        // Code pour modifier le profil
                        System.out.println("You chose to modifiy your profile.");
                        // Appeler la méthode pour modifier le profil
                        UserController.updateUserProfile(currentUser, scanner);
                        break;
                    case 2:
                        // Code pour créer un groupe
                        System.out.println("You chose to create a group.");
                        // Appeler la méthode pour créer un groupe
                        GroupController.createGroup(currentUser, scanner);
                        break;
                    case 3:
                        // Code pour rechercher un utilisateur
                        System.out.println("You chose to search a user.");
                        StuddyBuddyCases.userSearchCases(currentUser, scanner);
                        break;
                    case 4:
                        // Code pour sélectionner un groupe
                        System.out.println("You chose to select a group.");
                        GroupCases.groupSelectionCases(currentUser, scanner);
                        break;
                    case 5:
                        System.out.println("You chose to log out");
                        currentUser = null;
                        runningLogin = false;
                        returnValue = 5;
                        break;
                    case 0:
                        runningLogin = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a valid option.");
                        continue;
                }
            } else {
                System.out.println("Invalid input. Please input an integer.");
                scanner.nextLine(); // Pour vider le buffer du scanner
            }
        }
    }

    public static int getReturnValue() {
        return returnValue;
    }
}
