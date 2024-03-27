package fr.pantheonsorbonne.cri.MeetingFolderTest;

import fr.pantheonsorbonne.cri.MeetingFolder.Meeting;
import fr.pantheonsorbonne.cri.MeetingFolder.MeetingMembersDatabase;
import fr.pantheonsorbonne.cri.UserFolder.User;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class MeetingMembersDatabaseTest {

    @Test
    public void testInsertAndRemoveMeetingParticipant() {
        // Créer un meeting fictif
        /*Meeting meeting = new Meeting(0, null, null, LocalDate.of(2024, 3, 24), LocalTime.of(16, 52, 49), LocalTime.of(17, 52, 49),
                "Tour Eiffel, Paris, France", 4, false, null);

        // Créer un utilisateur fictif
        User user = new User(1, "John", "Doe", "johndoe", "johndoe@example.com", "password", "Paris", "France",
                "Art", "Music", "University");

        // Insérer l'utilisateur comme participant au meeting
        MeetingMembersDatabase.insertMeetingParticipantIntoDatabase(meeting, user);

        // Vérifier si l'utilisateur a été inséré comme participant
        assertTrue(meeting.getParticipants().contains(user));

        // Supprimer l'utilisateur de la liste des participants au meeting
        MeetingMembersDatabase.removeMeetingParticipantFromDatabase(meeting, user);

        // Vérifier si l'utilisateur a été correctement supprimé de la liste des participants
        assertFalse(meeting.getParticipants().contains(user));
    }
    */
}
}