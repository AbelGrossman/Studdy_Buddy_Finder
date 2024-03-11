package fr.pantheonsorbonne.cri;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenue dans l'application !");
        System.out.println("--------------------------------");

        while (true) {
            // Afficher le menu
            System.out.println("Menu :");
            System.out.println("1. S'inscrire");
            System.out.println("2. Se connecter");
            System.out.println("0. Quitter");

            // Demander à l'utilisateur de choisir une option
            System.out.print("Veuillez choisir une option : ");
            int choice = scanner.nextInt();

            // Effectuer une action en fonction du choix de l'utilisateur
            switch (choice) {
                case 1:
                    // Code pour l'inscription
                    break;
                case 2:
                    // Appeler le main de UserLogin pour la connexion
                    UserLogin.main(null);
                    break;
                case 0:
                    System.out.println("Merci d'avoir utilisé l'application. Au revoir !");
                    scanner.close();
                    return;
                default:
                    System.out.println("Option invalide. Veuillez choisir une option valide.");
            }
        }
    }
}
