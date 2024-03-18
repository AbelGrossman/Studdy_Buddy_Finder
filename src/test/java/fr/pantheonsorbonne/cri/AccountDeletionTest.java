package fr.pantheonsorbonne.cri;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AccountDeletionTest {

    
    @Test
    public void testDeleteUser() {
        // Test de suppression de compte avec un nom d'utilisateur correct et un mot de passe incorrect
        assertFalse(AccountDeletion.deleteUser("lala", "wrongPassword"));

        // Test de suppression de compte avec un nom d'utilisateur incorrect et un mot de passe correct
        assertFalse(AccountDeletion.deleteUser("wrongUsername", "mashalah"));

        // Test de suppression de compte avec des identifiants incorrects
        assertFalse(AccountDeletion.deleteUser("wrongUsername", "wrongPassword"));

        // Test de suppression de compte avec des identifiants corrects
        //assertTrue(AccountDeletion.deleteUser("lala", "mashalah"));
    }

    @Test
    public void testMain() {
        // Simuler les saisies utilisateur
        System.setIn(new ByteArrayInputStream("lala\nmashalah\n".getBytes()));

        // Capturer la sortie standard
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Exécuter la méthode deleteUser
        //String firstName, String lastName, String userName, String email, String password, String location1, String location2, String interest1, String interest2, String userStudies
        AccountDeletion.deleteAccount(new User("layal", "elzein", "lala", "lala@gmail.com", "mashalah", "Paris", "Cairo", "Computer Science", "Mathematics", "Licence MIAGE"));

        // Vérifier la sortie
        String expectedOutput = "Nom d'utilisateur : Mot de passe : Compte supprimé avec succès !\n";
        assertEquals(expectedOutput, out.toString());
    }

    @Test
    public void testMainWrong() {
        // Simuler les saisies utilisateur
        ByteArrayInputStream in = new ByteArrayInputStream("wrongUsername\nwrongPassword\n".getBytes());
        System.setIn(in);

        // Capturer la sortie standard
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Exécuter la méthode deleteUser
        AccountDeletion.deleteAccount(new User("layal", "elzein", "lala", "lala@gmail.com", "mashalah", "Paris", "Cairo", "Computer Science", "Mathematics", "Licence MIAGE"));;

        // Vérifier la sortie
        String expectedOutput = "Nom d'utilisateur : Mot de passe : Erreur lors de la suppression du compte. Veuillez réessayer.\n";
        assertEquals(expectedOutput, out.toString());
    }
}

