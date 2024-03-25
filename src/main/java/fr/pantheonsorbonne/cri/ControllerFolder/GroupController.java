package fr.pantheonsorbonne.cri.ControllerFolder;

import fr.pantheonsorbonne.cri.UserFolder.*;
import fr.pantheonsorbonne.cri.GroupFolder.*;
import fr.pantheonsorbonne.cri.MeetingFolder.MeetingDatabase;
import fr.pantheonsorbonne.cri.MeetingFolder.MeetingParticipantsDatabase;

import java.util.Scanner;

public class GroupController {

    private GroupController() {
        throw new IllegalStateException("Utility class");
    }

    public static void createGroup(User admin, Scanner scanner) {
        // Demander à l'utilisateur de saisir le nom du groupe
        if (scanner.hasNextLine()) {
            scanner.nextLine();
            System.out.print("Nom du groupe : ");
            String groupName = scanner.nextLine();

            // Demander à l'utilisateur de saisir le domaine d'étude
            System.out.print("Filière d'étude : ");
            String studyDomain = scanner.nextLine();

            // Demander à l'utilisateur de saisir le niveau d'étude
            System.out.print("Niveau d'étude : ");
            String studyLevel = scanner.nextLine();

            // Créer un groupe
            GroupDatabase.insertGroupIntoDatabase(groupName, studyDomain, studyLevel, admin);

            GroupMembersDatabase.insertGroupMemberIntoDatabase(GroupDatabase.findGroupByGroupname(groupName), admin);
        } else {
            System.out.println("Erreur lors de la saisie. Veuillez réessayer.");
            return;
        }
    }

    public static void sendGroupRequest(Group group, User user) {
        AdminRequestDatabase.insertAdminRequestIntoDatabase(group.getGroupAdmin(), user, group);
    }

    public static void answerGroupRequest(Group group, User user, Scanner scanner) {
        System.out.println("Do you want to accept " + user.getFirstName() + " " + user.getLastName() + " into "
                + group.getGroupName() + " ? (true/false)");
        if (scanner.hasNextBoolean()) {
            boolean choice = scanner.nextBoolean();
            if (choice) {
                System.out.println(
                        "You accepted " + user.getFirstName() + " " + user.getLastName() + " into "
                                + group.getGroupName()
                                + "!");
                addMember(user, group);
            } else {
                System.out
                        .println("You refused " + user.getFirstName() + " " + user.getLastName() + "'s request to join "
                                + group.getGroupName() + ".");
            }
            AdminRequestDatabase.removeAdminRequestFromDatabase(group.getGroupAdmin(), user, group);
        } else {
            System.out.println("Erreur lors de la saisie. Veuillez réessayer.");
        }
        scanner.nextLine(); // Pour vider le buffer du scanner
    }

    public static void sendGroupInvitation(User user, Group group) {
        InvitationRequestDatabase.insertGroupInvitationIntoDatabase(group, user);
    }

    public static void answerGroupInvitation(Group group, User user, Scanner scanner) {
        System.out.println("Do you want to join " + group.getGroupName() + " ? (true/false)");
        if (scanner.hasNextBoolean()) {
            scanner.nextLine();
            boolean choice = scanner.nextBoolean();
            if (choice) {
                System.out.println("You joined " + group.getGroupName() + "!");
                addMember(user, group);
            } else {
                System.out.println("You refused the invitation to join " + group.getGroupName() + ".");
            }
            InvitationRequestDatabase.removeGroupInvitationFromDatabase(group, user);
        } else {
            System.out.println("Erreur lors de la saisie. Veuillez réessayer.");
        }
        scanner.nextLine(); // Pour vider le buffer du scanner
    }

    private static void addMember(User member, Group group) {
        GroupMembersDatabase.insertGroupMemberIntoDatabase(group, member);
    }

    public static void removeMember(User member, Group group) {
        GroupMembersDatabase.removeGroupMemberFromDatabase(group, member);
    }

    public static void leaveGroup(Group group, User user) {
        removeMember(user, group);
        MeetingParticipantsDatabase.removeParticipantFromAllGroupMeetings(group, user);
    }

    public static void deleteGroup(Group group) {
        GroupMembersDatabase.removeGroupMembers(group);
        GroupDatabase.removeGroupFromDatabase(group);
        MeetingParticipantsDatabase.removeAllMeetingsMembersFromGroup(group);
        MeetingDatabase.removeAllMeetingsFromGroup(group);
    }

    public static void updateGroup(Group group, Scanner scanner) {
        boolean modifying = true;
        while (modifying) {
            System.out.println("Menu de modification du groupe :");
            System.out.println("1. Modifier le nom du groupe");
            System.out.println("2. Modifier la filière d'étude");
            System.out.println("3. Modifier le niveau d'étude");
            System.out.println("0. Quitter le menu de modification du groupe");

            System.out.println("Choisissez l'option que vous souhaitez modifier : ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Pour consommer la nouvelle ligne restante après nextInt()
                switch (choice) {
                    case 1:
                        System.out.print("Nouveau nom du groupe : ");
                        String groupName = scanner.nextLine();
                        group.setGroupName(groupName);
                        break;
                    case 2:
                        System.out.print("Nouvelle filière d'étude : ");
                        String studyDomain = scanner.nextLine();
                        group.setStudyDomain(studyDomain);
                        break;
                    case 3:
                        System.out.print("Nouveau niveau d'étude : ");
                        String studyLevel = scanner.nextLine();
                        group.setStudyLevel(studyLevel);
                        break;
                    case 0:
                        modifying = false;
                        continue;
                    default:
                        System.out.println("Option invalide. Veuillez choisir une option valide.");
                        continue;
                }
                GroupDatabase.updateGroupInDatabase(group);
            } else {
                System.out.println("Option invalide. Veuillez choisir une option valide.");
                continue;
            }
        }
    }
}
