package fr.pantheonsorbonne.cri;

import fr.pantheonsorbonne.cri.UserFolder.UserLogin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class UserLoginTest {
    private static Scanner scanner = new Scanner(System.in);

    @Test
    public void testLogin() {
        // Test de la connexion avec des identifiants corrects
        assertTrue(UserLogin.login("Abel31", "abel123"));

        // Test de la connexion avec identifiant correct et mot de passe incorrect
        assertFalse(UserLogin.login("Abel31", "wrongpassword"));

        // Test de la connexion avec identifiant incorrect et mot de passe correct
        assertFalse(UserLogin.login("johndoe", "abel123"));

        // Test de la connexion avec des identifiants incorrects
        assertFalse(UserLogin.login("wrongusername", "wrongpassword"));
    }

    @Test
    public void testMain() {
        // Simuler les saisies utilisateur
        System.setIn(new ByteArrayInputStream("Abel31\nabel123\n".getBytes()));

        // Capturer la sortie standard
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Exécuter le main de UserLogin
        UserLogin.login(scanner);

        // Vérifier la sortie
        String expectedOutput = "Bienvenue dans votre application de connexion !\n" +
                "----------------------------------------------\n" +
                "Nom d'utilisateur : Mot de passe : Connexion réussie !\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testMainWrong() {
        // Simuler les saisies utilisateur
        System.setIn(new ByteArrayInputStream("Abel31\nwrongpassword\n".getBytes()));

        // Capturer la sortie standard
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Exécuter le main de UserLogin
        UserLogin.login(scanner);

        // Vérifier la sortie
        String expectedOutput = "Bienvenue dans votre application de connexion !\n" +
                "----------------------------------------------\n" +
                "Nom d'utilisateur : Mot de passe : Nom d'utilisateur ou mot de passe incorrect. Réessayez ou inscrivez-vous.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }
}
