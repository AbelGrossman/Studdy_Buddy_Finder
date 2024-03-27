package fr.pantheonsorbonne.cri.GroupFolderTest;

import fr.pantheonsorbonne.cri.GroupFolder.Group;
import fr.pantheonsorbonne.cri.GroupFolder.GroupDatabase;
import fr.pantheonsorbonne.cri.UserFolder.User;
import fr.pantheonsorbonne.cri.GroupFolder.FindGroup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.*;

//Pour mettre les tests dans l'ordre snn il ne marche pas
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

//Working 24/03/2024
public class GroupDataBaseTest {

    @Test
    @Order(1)
    public void testInsertGroupIntoDatabase() {
        // Créer un utilisateur fictif pour le test
        User admin = new User(1000, "John", "Doe", "johndoe", "johndoe@example.com", "password", "Paris", "France", "Art", "Music", "University");

        // Insérer un groupe dans la base de données
        GroupDatabase.insertGroupIntoDatabase("TestGroup", "Computer Science", "Bachelor", admin);

        // Vérifier si le groupe a été inséré correctement en vérifiant s'il existe
        Group group = FindGroup.getGroupByGroupname("TestGroup");
        assertNotNull(group);
    }

    @Test
    @Order(2)
    public void testRemoveGroupFromDatabase() {
        // On va utiliser le meme groupe fictif créer précédemment pour le test

        // Récupérer le groupe inséré
        Group group = FindGroup.getGroupByGroupname("TestGroup");

        // Supprimer le groupe de la base de données
        GroupDatabase.removeGroupFromDatabase(group);

        // Vérifier si le groupe a été supprimé correctement en vérifiant s'il n'existe plus
        group = FindGroup.getGroupByGroupname("TestGroup");
        assertNull(group);
    }
}
