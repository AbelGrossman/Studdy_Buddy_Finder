package fr.pantheonsorbonne.cri;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/*public class GroupCreationTest {

    private static final User admin = new User("John", "Doe", "johndoe", "johndoe@example.com", "password", "Paris", null, "Economie", "Informatique", "Philosophie");

    @Test
    public void testCreateGroup() {
        // Données de test
        String groupName = "Study Group";
        String studyDomain = "Computer Science";
        String studyLevel = "Bachelor";

        // Simulation de saisies utilisateur
        String simulatedInput = String.format("%s%n%s%n%s%n", groupName, studyDomain, studyLevel);
        InputStream originalInput = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Capture de la sortie standard
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Appel de la méthode createGroup
        GroupCreation.createGroup(admin);

        // Réinitialisation de System.in
        System.setIn(originalInput);

        // Vérification de la sortie
        String expectedOutput = "Nom du groupe : Domaine d'étude : Niveau d'étude : Groupe créé avec succès !\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testInsertGroupIntoDatabase() {
        // Données de test
        String groupName = "Test Group";
        String studyDomain = "Test Domain";
        //String studyLevel = "Test Level";
        String admin = "Test Admin";

        // Insertion d'un groupe dans la base de données
        assertTrue(GroupCreation.insertGroupIntoDatabase(groupName, null, studyDomain, admin));
    }
}

*/