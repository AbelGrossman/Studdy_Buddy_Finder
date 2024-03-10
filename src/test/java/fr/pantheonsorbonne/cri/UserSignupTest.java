package fr.pantheonsorbonne.cri;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

//Pour mettre les tests dans l'ordre snn il ne marche pas
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserSignupTest {
    
    @Test
    @Order(1)
    public void testMain(){
        System.setIn(new ByteArrayInputStream("Layal\nElzein\nlala\nlayal@gmail.com\nmashalah\n".getBytes()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Exécuter le main de UserLogin
        UserSignup.main(null);

        // Vérifier la sortie
        String expectedOutput = "Bienvenue dans votre application d'inscription !\n" +
                                 "----------------------------------------------\n" +
                                 "Prénom : Nom : Nom d'utilisateur : Adresse e-mail : Mot de passe : Inscription réussie !\n";
        assertEquals(expectedOutput, outputStream.toString());

    }


    @Test
    @Order(2)
    public void testMainDuplicateEmail() {
        // Simuler les saisies utilisateur avec une adresse e-mail déjà utilisée
        System.setIn(new ByteArrayInputStream("Layal\nElzein\nlayaltest\nlayal@gmail.com\nmashalah\n".getBytes()));

        // Capturer la sortie standard
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Exécuter le main de UserSignup
        UserSignup.main(null);

        // Vérifier la sortie
        String expectedOutput = "Bienvenue dans votre application d'inscription !\n" +
                                 "----------------------------------------------\n" +
                                 "Prénom : Nom : Nom d'utilisateur : Adresse e-mail : Mot de passe : L'adresse e-mail est déjà utilisée.\n" +
                                 "Erreur lors de l'inscription !\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    @Order(3)
    public void testMainDuplicateUsername() {
        // Simuler les saisies utilisateur avec une adresse e-mail déjà utilisée
        System.setIn(new ByteArrayInputStream("Layal\nElzein\nlala\nlayaltest@gmail.com\nmashalah\n".getBytes()));

        // Capturer la sortie standard
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Exécuter le main de UserSignup
        UserSignup.main(null);

        // Vérifier la sortie
        String expectedOutput = "Bienvenue dans votre application d'inscription !\n" +
                                 "----------------------------------------------\n" +
                                 "Prénom : Nom : Nom d'utilisateur : Adresse e-mail : Mot de passe : Le nom d'utilisateur est déjà pris.\n" +
                                 "Erreur lors de l'inscription !\n";
        assertEquals(expectedOutput, outputStream.toString());
    }
    
}
