package fr.pantheonsorbonne.cri;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

public class MeetingManager {
    private static Scanner scanner = new Scanner(System.in);

    public static List<Meeting> createdMeetings = new ArrayList<>();

    public static void createMeeting(User admin, Group group) {
        // Demander à l'utilisateur de saisir la date de la réunion
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
        Meeting meeting = new Meeting(group, date, startTime, endTime, meetingLocation, amountOfParticipants,
                reservationRequired, admin);
        addCreatedMeeting(meeting);
    }

    private static void addCreatedMeeting(Meeting meeting) {
        createdMeetings.add(meeting);
    }

    public static void joinMeeting(User user, Meeting meeting) {
        meeting.getParticipants().add(user);
        MeetingMembersDatabase.insertMeetingParticipantIntoDatabase(meeting, user);
    }

    public static void leaveMeeting(User user, Meeting meeting) {
        meeting.getParticipants().remove(user);
        MeetingMembersDatabase.removeMeetingParticipantFromDatabase(meeting, user);
    }

    public static void removeMeeting(Meeting meeting) {
        MeetingMembersDatabase.removeMeetingParticipants(meeting);
        createdMeetings.remove(meeting);
        meeting.removeMeetingFromDatabase();
    }

    public static Meeting getCreatedMeetingById(int meetingId) {
        for (Meeting meeting : createdMeetings) {
            if (meeting.getMeetingId() == meetingId) {
                return meeting;
            }
        }
        return null;
    }

    public static List<Meeting> getCreatedMeetings() {
        return createdMeetings;
    }
}
