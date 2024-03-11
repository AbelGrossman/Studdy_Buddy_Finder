package fr.pantheonsorbonne.cri;

import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean running = true;
    private static User currentUser = null;

    public static void main(String[] args) {

        System.out.println("Bienvenue dans l'application !");
        System.out.println("--------------------------------");

        while (running) {
            // Afficher le menu
            System.out.println("Menu :");
            System.out.println("1. S'inscrire");
            System.out.println("2. Se connecter");
            System.out.println("3. Modifier le profil");
            System.out.println("4. Créer un groupe");
            System.out.println("5. Rejoindre un groupe");
            System.out.println("6. Sélectionner un groupe");
            System.out.println("7. Rechercher un utilisateur");
            System.out.println("0. Quitter");

            // Demander à l'utilisateur de choisir une option
            System.out.print("Veuillez choisir une option : ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();

                // Effectuer une action en fonction du choix de l'utilisateur
                switch (choice) {
                    case 1:
                        // Code pour l'inscription
                        UserSignup.signup();
                        break;
                    case 2:
                        // Appeler le main de UserLogin pour la connexion
                        currentUser = UserLogin.login();
                        scanner = new Scanner(System.in);
                        break;
                    case 3:
                        // Code pour modifier le profil
                        System.out.println("Vous avez choisi de modifier le profil.");
                        // Appeler la méthode pour modifier le profil
                        if (currentUser != null) {
                            UserUpdate.updateUserProfile(currentUser);
                        }
                        break;
                    case 4:
                        // Code pour créer un groupe
                        System.out.println("Vous avez choisi de créer un groupe.");
                        // Appeler la méthode pour créer un groupe
                        if (currentUser != null) {
                            GroupCreation.createGroup(currentUser);
                        }
                        break;
                    case 5:
                        // Code pour rejoindre un groupe
                        System.out.println("Vous avez choisi de rejoindre un groupe.");
                        // Appeler la méthode pour rejoindre un groupe
                        break;
                    case 6:
                        // Code pour sélectionner un groupe
                        System.out.println("Vous avez choisi de sélectionner un groupe.");
                        groupSelectionCases();
                        break;
                    case 7:
                        // Code pour rechercher un utilisateur
                        System.out.println("Vous avez choisi de rechercher un utilisateur.");
                        userSearchCases();
                        break;
                    case 0:
                        System.out.println("Merci d'avoir utilisé l'application. Au revoir !");
                        running = false; // Quitter la boucle
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez choisir une option valide.");
                }
            } else

            {
                System.out.println("Veuillez saisir un nombre correspondant à une option valide.");
                scanner.nextLine(); // Pour vider le buffer du scanner
            }
            scanner.close();
        }
    }

    public static void groupSelectionCases() {
        int groupId = scanner.nextInt();
        Group currentGroup = GroupCreation.getCreatedGroupById(groupId);
        if (currentGroup != null) {
            boolean groupRunning = true;

            if (currentUser != currentGroup.getAdmin()) {
                while (groupRunning) {
                    System.out.println("Group Menu :");
                    System.out.println("1. Send Group Request");
                    System.out.println("2. Answer Group Invitation");
                    System.out.println("3. Leave Group");
                    System.out.println("0. Quit Group Menu");

                    System.out.print("Veuillez choisir une option : ");
                    if (scanner.hasNextInt()) {
                        int groupOption = scanner.nextInt();
                        switch (groupOption) {
                            case 1:

                                System.out.println(
                                        "Vous avez choisi d'envoyer une demande d'accès au groupe.");

                                break;
                            case 2:
                                System.out.println(
                                        "Vous avez choisi de répondre à une invitation à rejoindre le groupe.");
                                break;
                            case 3:
                                System.out.println("Vous avez choisi de quitter le groupe.");
                                break;
                            case 0:
                                groupRunning = false;
                                break;
                            default:
                                System.out.println(
                                        "Option invalide. Veuillez choisir une option valide.");
                                break;
                        }
                    }
                }
            } else {
                while (groupRunning) {
                    System.out.println("Group Menu :");
                    System.out.println("1. Answer Group Request");
                    System.out.println("2. Send Group Invitation");
                    System.out.println("3. Remove Group Member");
                    System.out.println("4. Delete Group");
                    System.out.println("0. Quit Group Menu");

                    System.out.print("Veuillez choisir une option : ");
                    if (scanner.hasNextInt()) {
                        int groupOption = scanner.nextInt();
                        switch (groupOption) {
                            case 1:
                                System.out.println(
                                        "Vous avez choisi de répondre à une demande d'accès au groupe.");
                                break;
                            case 2:
                                System.out.println(
                                        "Vous avez choisi d'envoyer une invitation à rejoindre le groupe.");
                                break;
                            case 3:
                                System.out
                                        .println("Vous avez choisi de supprimer un membre du groupe.");
                                break;
                            case 4:
                                System.out.println("Vous avez choisi de supprimer le groupe.");
                                break;
                            case 0:
                                groupRunning = false;
                                break;
                            default:
                                System.out.println(
                                        "Option invalide. Veuillez choisir une option valide.");
                                break;
                        }
                    }
                }
            }
        }
    }

    public static void userSearchCases() {
        String username = scanner.nextLine();
        User searchedUser = UserSignup.getRegisteredUserByUsername(username);
        if (searchedUser != null) {
            boolean userRunning = true;
            while (userRunning) {
                System.out.println("Group Menu :");
                System.out.println("1. Send Studdy Buddy Request");
                System.out.println("2. Answer Studdy Buddy Request");
                System.out.println("3. Remove Studdy Buddy");
                System.out.println("0. Quit Group Menu");

                System.out.print("Veuillez choisir une option : ");
                if (scanner.hasNextInt()) {
                    int groupOption = scanner.nextInt();

                    switch (groupOption) {
                        case 1:
                            System.out
                                    .println("Vous avez choisi d'envoyer une demande de Studdy Buddy.");
                            break;
                        case 2:
                            System.out.println(
                                    "Vous avez choisi de répondre à une demande de Studdy Buddy.");
                            break;
                        case 3:
                            System.out.println("Vous avez choisi de supprimer un Studdy Buddy.");
                            break;
                        case 0:
                            userRunning = false;
                            break;
                        default:
                            System.out.println("Option invalide. Veuillez choisir une option valide.");
                            break;
                    }
                }
            } // Appeler la méthode pour rechercher un utilisateur
        }
    }
}
