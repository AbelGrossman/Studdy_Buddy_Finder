package fr.pantheonsorbonne.cri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class GroupCreation {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studdy_buddy_finder";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private static List<Group> createdGroups = new ArrayList<>();

    public static void createGroup(User admin) {
        Scanner scanner = new Scanner(System.in);
        // Demander à l'utilisateur de saisir le nom du groupe
        System.out.print("Nom du groupe : ");
        String groupName = scanner.nextLine();

        // Demander à l'utilisateur de saisir le domaine d'étude
        System.out.print("Domaine d'étude : ");
        String studyDomain = scanner.nextLine();

        // Demander à l'utilisateur de saisir le niveau d'étude
        System.out.print("Niveau d'étude : ");
        String studyLevel = scanner.nextLine();

        // Créer un groupe
        if (insertGroupIntoDatabase(groupName, admin, studyDomain, studyLevel)) {
            addCreatedGroup(new Group(groupName, admin, studyDomain, studyLevel));
            System.out.println("Groupe créé avec succès !");
        } else {
            System.out.println("Erreur lors de la création du groupe. Veuillez réessayer.");
        }
    }

    private static boolean insertGroupIntoDatabase(String groupName, User admin, String studyDomain,
            String studyLevel) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO Group (group_name, study_domain, admin_id) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, groupName);
                preparedStatement.setString(2, studyDomain);
                preparedStatement.setInt(3, admin.getUserId());
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error inserting group into the database" + e.getMessage());
            return false;
        }
    }

    public static void addCreatedGroup(Group group) {
        createdGroups.add(group);
    }

    public static Group getCreatedGroupById(int groupId) {
        for (Group group : createdGroups) {
            if (group.getGroupId() == groupId) {
                return group;
            }
        }
        return null;
    }
}
