package fr.pantheonsorbonne.cri;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

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
            System.out.println("6. Rechercher un utilisateur");
            System.out.println("0. Quitter");

            // Demander à l'utilisateur de choisir une option
            System.out.print("Veuillez choisir une option : ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();

                // Effectuer une action en fonction du choix de l'utilisateur
                switch (choice) {
                    case 1:
                        // Code pour l'inscription
                        UserSignup.main(null);
                        break;
                    case 2:
                        // Appeler le main de UserLogin pour la connexion
                        UserLogin.main(null);
                        scanner = new Scanner(System.in);
                        break;
                    case 3:
                        // Code pour créer un groupe
                        System.out.println("Vous avez choisi de créer un groupe.");
                        // Appeler la méthode pour créer un groupe
                        break;
                    case 4:
                        // Code pour rejoindre un groupe
                        System.out.println("Vous avez choisi de rejoindre un groupe.");
                        // Appeler la méthode pour rejoindre un groupe
                        break;
                    case 5:
                        // Code pour rechercher un utilisateur
                        System.out.println("Vous avez choisi de rechercher un utilisateur.");
                        // Appeler la méthode pour rechercher un utilisateur
                        break;
                    case 0:
                        System.out.println("Merci d'avoir utilisé l'application. Au revoir !");
                        running = false; // Quitter la boucle
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez choisir une option valide.");
                }
            } else {
                System.out.println("Veuillez saisir un nombre correspondant à une option valide.");
                scanner.nextLine(); // Pour vider le buffer du scanner
            }
        }
        scanner.close();
    }
}
