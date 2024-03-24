package fr.pantheonsorbonne.cri.StuddyBuddyFolderTest;
import fr.pantheonsorbonne.cri.StuddyBuddyFolder.FindFriend;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

public class FindFriendTest {


    @Test
    public void testMain(){
        System.setIn(new ByteArrayInputStream("Abel31\noui\n".getBytes()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Exécuter le main de UserLogin
        FindFriend.findFriend();

        String expectedOutput = "Recherche d'ami par nom d'utilisateur !\n" +
                                "----------------------------------------\n" +
                                "Entrez le nom d'utilisateur de votre ami : Ami trouvé !\n" +
                                "Nom : Abel Grossman\n" +
                                "Centre d'intérêt 1 : Communisme\n" +
                                "Centre d'intérêt 2 : Politique\n" +
                                "Voulez-vous ajouter cet utilisateur en tant qu'ami ? (oui/non) : Ami ajouté avec succès !\n";

        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testFindFriend() {
        // Capture la sortie standard
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Simule les saisies utilisateur
        System.setIn(new ByteArrayInputStream("oui\n".getBytes()));

        // Teste la recherche d'un ami existant
        FindFriend.findFriendByUsername("lala", new Scanner(System.in));

        // Vérifie la sortie
        String expectedOutput = "Ami trouvé !\n" +
                                "Nom : Layal Elzein\n" +
                                "Centre d'intérêt 1 : Informatique\n" +
                                "Centre d'intérêt 2 : Economie\n" +
                                "Voulez-vous ajouter cet utilisateur en tant qu'ami ? (oui/non) : Ami ajouté avec succès !\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testFindNonExistentFriend() {
        // Capture la sortie standard
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Simule les saisies utilisateur
        System.setIn(new ByteArrayInputStream("oui\n".getBytes()));

        // Teste la recherche d'un ami inexistant
        FindFriend.findFriendByUsername("wrongusername", new Scanner(System.in));

        // Vérifie la sortie
        String expectedOutput = "Aucun utilisateur trouvé avec le nom d'utilisateur spécifié.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }
}

