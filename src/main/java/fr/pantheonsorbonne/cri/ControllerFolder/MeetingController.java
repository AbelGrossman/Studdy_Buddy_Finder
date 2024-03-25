package fr.pantheonsorbonne.cri.ControllerFolder;

import fr.pantheonsorbonne.cri.MeetingFolder.Meeting;
import fr.pantheonsorbonne.cri.MeetingFolder.MeetingDatabase;
import fr.pantheonsorbonne.cri.UserFolder.User;
import fr.pantheonsorbonne.cri.GroupFolder.Group;
import fr.pantheonsorbonne.cri.MeetingFolder.MeetingParticipantsDatabase;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

public class MeetingController {

    private MeetingController() {
        throw new IllegalStateException("Utility class");
    }

    public static void createMeeting(User admin, Group group, Scanner scanner) {
        // Demander à l'utilisateur de saisir la date de la réunion
        scanner.nextLine();
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

        // Demander à l'utilisateur de saisir si une réservation est requise
        System.out.print("Réservation requise (true/false) : ");
        boolean reservationRequired = scanner.nextBoolean();

        // Créer une réunion
        MeetingDatabase.insertMeetingIntoDatabase(meetingName, group, date, startTime, endTime, meetingLocation,
                reservationRequired, admin);
        MeetingParticipantsDatabase.insertMeetingParticipantIntoDatabase(MeetingDatabase.getMeetingByName(meetingName),
                admin);
        //admin.addEventToCalendar(MeetingDatabase.getMeetingByName(meetingName));
    }

    public static void joinMeeting(User user, Meeting meeting) {
        //user.addEventToCalendar(meeting);
        MeetingParticipantsDatabase.insertMeetingParticipantIntoDatabase(meeting, user);
        // user.getCalendar().addEvent(meeting);
    }

    public static void leaveMeeting(User user, Meeting meeting) {
        //user.removeEventFromCalendar(meeting);
        MeetingParticipantsDatabase.removeMeetingParticipantFromDatabase(meeting, user);
    }

    public static void removeMeeting(Meeting meeting) {
        MeetingParticipantsDatabase.removeMeetingParticipants(meeting);
        MeetingDatabase.removeMeetingFromDatabase(meeting);
    }

    public static void updateMeeting(Meeting meeting, Scanner scanner) {
        boolean modifying = true;
        while (modifying) {
            System.out.println("Meeting Update Menu :");
            System.out.println("1. Update Meeting Name");
            System.out.println("2. Update Meeting Date");
            System.out.println("3. Update Meeting Start Time");
            System.out.println("4. Update Meeting End Time");
            System.out.println("5. Update Meeting Location");
            System.out.println("6. Update Reservation Requirement");
            System.out.println("0. Back to the Meeting Menu");
            System.out.print("Please choose an option : ");
            if (scanner.hasNextInt()) {
                int meetingResult = scanner.nextInt();
                switch (meetingResult) {
                    case 1:
                        System.out.println("Enter the new meeting name : ");
                        scanner.nextLine();
                        String newMeetingName = scanner.nextLine();
                        meeting.setMeetingName(newMeetingName);
                        break;
                    case 2:
                        System.out.println("Enter the new meeting date (aaaa-mm-jj) : ");
                        scanner.nextLine();
                        String newMeetingDate = scanner.nextLine();
                        LocalDate date = LocalDate.parse(newMeetingDate);
                        meeting.setMeetingDate(date);
                        break;
                    case 3:
                        System.out.println("Enter the new meeting start time (hh:mm) : ");
                        scanner.nextLine();
                        String newMeetingStartTime = scanner.nextLine();
                        LocalTime startTime = LocalTime.parse(newMeetingStartTime);
                        meeting.setMeetingStartTime(startTime);
                        break;
                    case 4:
                        System.out.println("Enter the new meeting end time (hh:mm) : ");
                        scanner.nextLine();
                        String newMeetingEndTime = scanner.nextLine();
                        LocalTime endTime = LocalTime.parse(newMeetingEndTime);
                        meeting.setMeetingEndTime(endTime);
                        break;
                    case 5:
                        System.out.println("Enter the new meeting location : ");
                        scanner.nextLine();
                        String newMeetingLocation = scanner.nextLine();
                        meeting.setMeetingLocation(newMeetingLocation);
                        break;
                    case 6:
                        System.out.println("Enter the new reservation requirement (true/false) : ");
                        scanner.nextLine();
                        boolean newReservationRequired = scanner.nextBoolean();
                        meeting.setReservationRequired(newReservationRequired);
                        break;
                    case 0:
                        modifying = false;
                        continue;
                    default:
                        System.out.println("Invalid option. Please choose a valid option.");
                        continue;
                }
                MeetingDatabase.updateMeetingInDatabase(meeting);
            }
        }
    }
}