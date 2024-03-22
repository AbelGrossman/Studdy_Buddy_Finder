package fr.pantheonsorbonne.cri.ControllerFolder;

import fr.pantheonsorbonne.cri.MeetingFolder.Meeting;
import fr.pantheonsorbonne.cri.MeetingFolder.MeetingDatabase;
import fr.pantheonsorbonne.cri.UserFolder.User;
import fr.pantheonsorbonne.cri.GroupFolder.Group;
import fr.pantheonsorbonne.cri.MeetingFolder.MeetingMembersDatabase;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

public class MeetingController {
    private static Scanner scanner = new Scanner(System.in);

    private MeetingController() {
        throw new IllegalStateException("Utility class");
    }

    public static void createMeeting(User admin, Group group) {
        // Demander à l'utilisateur de saisir la date de la réunion
        System.out.println("Nome de la réunion : ");
        String meetingName = scanner.nextLine();

        System.out.print("Date de la réunion (aaaa-mm-jj) : ");
        String meetingDate = scanner.nextLine();
        LocalDate date = LocalDate.parse(meetingDate);

        // Demander à l'utilisateur de saisir l'heure de début de la réunion
        System.out.print("Heure de début de la réunion (hh:mm) : ");
        String meetingStartTime = scanner.nextLine();
        LocalTime startTime = LocalTime.parse(meetingStartTime);

        // Demander à l'utilisateur de saisir l'heure de fin de la réunion
        System.out.print("Heure de fin de la réunion (hh:mm) : ");
        String meetingEndTime = scanner.nextLine();
        LocalTime endTime = LocalTime.parse(meetingEndTime);

        // Demander à l'utilisateur de saisir le lieu de la réunion
        System.out.print("Lieu de la réunion : ");
        String meetingLocation = scanner.nextLine();

        // Demander à l'utilisateur de saisir le nombre de participants
        System.out.print("Nombre de participants : ");
        int amountOfParticipants = scanner.nextInt();

        // Demander à l'utilisateur de saisir si une réservation est requise
        System.out.print("Réservation requise (true/false) : ");
        boolean reservationRequired = scanner.nextBoolean();

        // Créer une réunion
        MeetingDatabase.insertMeetingIntoDatabase(meetingName, group, date, startTime, endTime, meetingLocation,
                amountOfParticipants,
                reservationRequired, admin);
        MeetingMembersDatabase.insertMeetingParticipantIntoDatabase(MeetingDatabase.getMeetingByName(meetingName),
                admin);
    }

    public static void joinMeeting(User user, Meeting meeting) {
        user.addEventToCalendar(meeting);
        MeetingMembersDatabase.insertMeetingParticipantIntoDatabase(meeting, user);

        // user.getCalendar().addEvent(meeting);
    }

    public static void leaveMeeting(User user, Meeting meeting) {
        user.removeEventFromCalendar(meeting);
        MeetingMembersDatabase.removeMeetingParticipantFromDatabase(meeting, user);
    }

    public static void removeMeeting(Meeting meeting) {
        MeetingMembersDatabase.removeMeetingParticipants(meeting);
        MeetingDatabase.removeMeetingFromDatabase(meeting);
    }
}
