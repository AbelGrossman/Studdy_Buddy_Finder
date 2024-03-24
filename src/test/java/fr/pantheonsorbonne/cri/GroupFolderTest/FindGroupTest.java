package fr.pantheonsorbonne.cri.GroupFolderTest;

import fr.pantheonsorbonne.cri.GroupFolder.FindGroup;
import fr.pantheonsorbonne.cri.GroupFolder.Group;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//Working 24/03/2024
public class FindGroupTest {

    @Test
    public void testGetGroupByGroupname() {
        // Test de recherche de groupe par nom de groupe existant
        String existingGroupName = "KKJ";
        Group group = FindGroup.getGroupByGroupname(existingGroupName);
        assertNotNull(group);
        System.out.println("Group found: " + group.toString());
    }
}

