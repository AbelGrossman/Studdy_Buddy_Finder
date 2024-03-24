package fr.pantheonsorbonne.cri.MeetingFolderTest;

import fr.pantheonsorbonne.cri.GroupFolder.Group;
import fr.pantheonsorbonne.cri.MeetingFolder.Meeting;
import fr.pantheonsorbonne.cri.UserFolder.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

//Pas possible de tester car pas de clé d'API Google Maps
public class MeetingTest {
    User admin = new User(1, "John", "Doe", "johndoe", "johndoe@example.com", "password", "Paris", "France", "Art", "Music", "University");
    Group group = new Group(1, "TestGroupMeeting", admin, "Computer Science", "Bachelor");

    @Test
    @Order(1)
    public void testMeetingCreation() {
        //User admin = new User(1, "John", "Doe", "johndoe", "johndoe@example.com", "password", "Paris", "France", "Art", "Music", "University");
        //Group group = new Group(1, "TestGroupMeeting", admin, "Computer Science", "Bachelor");

        LocalDate meetingDate = LocalDate.of(2024, 3, 23);
        LocalTime meetingStartTime = LocalTime.of(14, 0);
        LocalTime meetingEndTime = LocalTime.of(16, 30);
        String meetingLocation = "Tour Eiffel, Paris, France";

        Meeting meeting = new Meeting(group, meetingDate, meetingStartTime, meetingEndTime, meetingLocation, 10, true, admin);

        assertNotNull(meeting);
        assertEquals(group, meeting.getMeetingGroup());
        assertEquals(meetingDate, meeting.getMeetingDate());
        assertEquals(meetingStartTime, meeting.getMeetingStartTime());
        assertEquals(meetingEndTime, meeting.getMeetingEndTime());
        assertEquals(meetingLocation, meeting.getMeetingLocation());
        assertEquals(admin, meeting.getMeetingAdmin());
        assertEquals(1, meeting.getParticipants().size()); // Admin est automatiquement ajouté comme participant
        assertTrue(meeting.isReservationRequired());

        // Vérifier si le lien Google Maps a été généré
        assertNotNull(meeting.getGoogleMapsLink());
    }

    @Test
    @Order(2)
    public void testMeetingRemoval() {

        LocalDate meetingDate = LocalDate.of(2024, 3, 23);
        LocalTime meetingStartTime = LocalTime.of(14, 0);
        LocalTime meetingEndTime = LocalTime.of(16, 0);
        String meetingLocation = "Paris, France";

        Meeting meeting = new Meeting(group, meetingDate, meetingStartTime, meetingEndTime, meetingLocation, 10, true, admin);

        assertNotNull(meeting);

        // Supprimer la réunion de la base de données
        meeting.removeMeetingFromDatabase();

        // Vérifier si la réunion a été supprimée correctement
        assertEquals(0, meeting.getMeetingId());
    }
}
