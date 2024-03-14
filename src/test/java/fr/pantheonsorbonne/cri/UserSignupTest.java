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
        System.setIn(new ByteArrayInputStream("Layal\nElzein\nlala\nlayal@gmail.com\nmashalah\nParis\n\nInformatique\nEconomie\nMIAGE\n".getBytes()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Exécuter le main de UserLogin
        UserSignup.signup();

        // Vérifier la sortie
        String expectedOutput = "Bienvenue dans votre application d'inscription !\n" +
                                 "----------------------------------------------\n" +
                                 "Prénom : Nom : Nom d'utilisateur : Adresse e-mail : Mot de passe : Veuillez saisir jusqu'à deux lieux de résidence : \nVeuillez saisir jusqu'à deux centres d'intérêt : \nVeuillez saisir votre filière d'études : Inscription réussie !\n";
        assertEquals(expectedOutput, outputStream.toString());

    }


    @Test
    @Order(2)
    public void testMainDuplicateEmail() {
        // Simuler les saisies utilisateur avec une adresse e-mail déjà utilisée
        System.setIn(new ByteArrayInputStream("Layal\nElzein\nlalatest\nlayal@gmail.com\nmashalah\nParis\n\nInformatique\nEconomie\nMathématiques\nMIAGE\n".getBytes()));

        // Capturer la sortie standard
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Exécuter le main de UserSignup
        UserSignup.signup();

        // Vérifier la sortie
        String expectedOutput = "Bienvenue dans votre application d'inscription !\n" +
                                 "----------------------------------------------\n" +
                                 "Prénom : Nom : Nom d'utilisateur : Adresse e-mail : Mot de passe : Veuillez saisir jusqu'à deux lieux de résidence : \nVeuillez saisir jusqu'à deux centres d'intérêt : \nVeuillez saisir votre filière d'études : L'adresse e-mail est déjà utilisée.\n" +
                                 "Erreur lors de l'inscription. Veuillez réessayer.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    @Order(3)
    public void testMainDuplicateUsername() {
        // Simuler les saisies utilisateur avec une adresse e-mail déjà utilisée
        System.setIn(new ByteArrayInputStream("Layal\nElzein\nlala\nlayalttest@gmail.com\nmashalah\nParis\n\nInformatique\nEconomie\nMathématiques\nMIAGE\n".getBytes()));

        // Capturer la sortie standard
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Exécuter le main de UserSignup
        UserSignup.signup();

        // Vérifier la sortie
        String expectedOutput = "Bienvenue dans votre application d'inscription !\n" +
                                 "----------------------------------------------\n" +
                                 "Prénom : Nom : Nom d'utilisateur : Adresse e-mail : Mot de passe : Veuillez saisir jusqu'à deux lieux de résidence : \nVeuillez saisir jusqu'à deux centres d'intérêt : \nVeuillez saisir votre filière d'études : Le nom d'utilisateur est déjà pris.\n" +
                                 "Erreur lors de l'inscription. Veuillez réessayer.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }
    
}
