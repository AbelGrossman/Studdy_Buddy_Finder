package fr.pantheonsorbonne.cri.MeetingFolderTest;

import fr.pantheonsorbonne.cri.MeetingFolder.Meeting;
import fr.pantheonsorbonne.cri.MeetingFolder.MeetingMembersDatabase;
import fr.pantheonsorbonne.cri.UserFolder.User;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

//Not possible to test because of the Google Maps API key
public class MeetingMembersDatabaseTest {
        // Créer un meeting fictif
        Meeting meeting = new Meeting(null, LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(1),
                "Meeting location", 5, false, null);

        // Créer un utilisateur fictif
        User user = new User(1, "John", "Doe", "johndoe", "johndoe@example.com", "password", "Paris", "France",
                "Art", "Music", "University");
    @Test
    public void testInsertAndRemoveMeetingParticipant() {
        

        // Insérer l'utilisateur comme participant au meeting
        MeetingMembersDatabase.insertMeetingParticipantIntoDatabase(meeting, user);

        // Vérifier si l'utilisateur a été inséré comme participant
        assertTrue(meeting.getParticipants().contains(user));

        // Supprimer l'utilisateur de la liste des participants au meeting
        MeetingMembersDatabase.removeMeetingParticipantFromDatabase(meeting, user);

        // Vérifier si l'utilisateur a été correctement supprimé de la liste des participants
        assertFalse(meeting.getParticipants().contains(user));
    }

    @Test
    public void testRemoveMeetingParticipants() {

        // Créer quelques utilisateurs fictifs
        User user1 = new User(1, "John", "Doe", "johndoe", "johndoe@example.com", "password", "Paris", "France",
                "Art", "Music", "University");
        User user2 = new User(2, "Jane", "Smith", "janesmith", "janesmith@example.com", "password", "New York", "USA",
                "Science", "Technology", "College");

        // Insérer les utilisateurs comme participants au meeting
        MeetingMembersDatabase.insertMeetingParticipantIntoDatabase(meeting, user1);
        MeetingMembersDatabase.insertMeetingParticipantIntoDatabase(meeting, user2);

        // Supprimer tous les participants du meeting
        MeetingMembersDatabase.removeMeetingParticipants(meeting);

        // Vérifier si la liste des participants est vide après suppression
        assertTrue(meeting.getParticipants().isEmpty());
    }
}
