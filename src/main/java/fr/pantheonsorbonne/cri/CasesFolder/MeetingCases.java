package fr.pantheonsorbonne.cri.CasesFolder;

import fr.pantheonsorbonne.cri.MeetingFolder.*;
import fr.pantheonsorbonne.cri.UserFolder.*;
import fr.pantheonsorbonne.cri.ControllerFolder.*;
import fr.pantheonsorbonne.cri.GroupFolder.*;

import java.util.Scanner;

public class MeetingCases {

    private MeetingCases() {
        throw new IllegalStateException("Utility class");
    }

    public static void viewMeetings(Group currentGroup, User currentUser, Scanner scanner) {
        boolean meetingRunning = true;
        while (meetingRunning) {
            System.out.println("Select a meeting by entering its name :");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
                String meetingName = scanner.nextLine();
                Meeting currentMeeting = MeetingDatabase.getMeetingByName(meetingName);
                if (currentMeeting == null) {
                    System.out.println("The meeting doesn't exist");
                    System.out.println("Do you want to leave the meeting menu? (true/false)");
                    if (scanner.hasNextBoolean()) {
                        boolean choice = scanner.nextBoolean();
                        if (choice) {
                            break;
                        }
                        continue;
                    } else {
                        System.out.println("Invalid input.");
                        break;
                    }
                } else if (currentUser.getUserId() != currentMeeting.getMeetingAdmin().getUserId()) {
                    while (meetingRunning) {
                        System.out.println("Meeting Menu :");
                        System.out.println("1. Join Meeting");
                        System.out.println("2. Leave Meeting");
                        System.out.println("0. Back to the Group Menu");
                        System.out.print("Please choose an option : ");
                        if (scanner.hasNextInt()) {
                            int meetingResult = meetingMemberSelectionCases(currentMeeting, currentUser, scanner);
                            if (meetingResult == 0) {
                                meetingRunning = false;
                            }
                        } else {
                            System.out.println("Invalid input. Please input an integer.");
                            scanner.nextLine(); // Pour vider le buffer du scanner
                        }
                    }
                } else {
                    while (meetingRunning) {
                        System.out.println("Meeting Admin Menu :");
                        System.out.println("1. Remove Meeting");
                        System.out.println("2. Update Meeting");
                        System.out.println("0. Back to the Group Menu");
                        System.out.print("Please choose an option : ");
                        if (scanner.hasNextInt()) {
                            int meetingResult = meetingAdminSelectionCases(currentMeeting, scanner);
                            if (meetingResult == 1) {
                                currentMeeting = null;
                            } else if (meetingResult == 0) {
                                meetingRunning = false;
                            }
                        } else {
                            System.out.println("Invalid input. Please input an integer.");
                            scanner.nextLine(); // Pour vider le buffer du scanner
                        }
                    }
                }
            } else {
                System.out.println("Invalid input. Please input a string.");
                scanner.nextLine();
            }
        }
    }

    public static int meetingMemberSelectionCases(Meeting currentMeeting, User currentUser, Scanner scanner) {
        int meetingOption = scanner.nextInt();
        switch (meetingOption) {
            case 1:
                if (MeetingParticipantsDatabase.findMeetingParticipant(currentMeeting, currentUser) != null) {
                    System.out.println("You are already in the meeting.");
                } else {
                    System.out.println("You chose to join the meeting.");
                    MeetingController.joinMeeting(currentUser, currentMeeting);
                }
                break;
            case 2:
                if (MeetingParticipantsDatabase.findMeetingParticipant(currentMeeting, currentUser) == null) {
                    System.out.println("You are not in the meeting.");
                } else {
                    System.out.println("You chose to leave the meeting.");
                    MeetingController.leaveMeeting(currentUser, currentMeeting);
                }
                break;
            case 0:
                System.out.println("Back to the Group Menu");
                return 0;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
                break;
        }
        return 3;
    }

    public static int meetingAdminSelectionCases(Meeting currentMeeting, Scanner scanner) {
        int meetingOption = scanner.nextInt();
        switch (meetingOption) {
            case 1:
                System.out.println("You chose to remove the meeting.");
                MeetingController.removeMeeting(currentMeeting);
                return 1;
            case 2:
                System.out.println("You chose to update the meeting.");
                MeetingController.updateMeeting(currentMeeting, scanner);
                break;
            case 0:
                System.out.println("Back to the Group Menu");
                return 0;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
                break;
        }
        return 2;
    }
}
