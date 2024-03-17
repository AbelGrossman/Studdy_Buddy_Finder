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
        assertTrue(AccountDeletion.deleteUser("lala", "mashalah"));
    }

    @Test
    public void testMain() {
        // Simuler les saisies utilisateur
        ByteArrayInputStream in = new ByteArrayInputStream("noamblzs\nnoam123456\n".getBytes());
        System.setIn(in);

        // Capturer la sortie standard
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Exécuter la méthode deleteUser
        AccountDeletion.main(null);

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
        AccountDeletion.main(null);

        // Vérifier la sortie
        String expectedOutput = "Nom d'utilisateur : Mot de passe : Erreur lors de la suppression du compte. Veuillez réessayer.\n";
        assertEquals(expectedOutput, out.toString());
    }
}

