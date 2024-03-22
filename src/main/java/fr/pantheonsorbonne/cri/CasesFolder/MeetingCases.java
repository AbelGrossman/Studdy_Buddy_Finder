package fr.pantheonsorbonne.cri.CasesFolder;

import fr.pantheonsorbonne.cri.MeetingFolder.*;
import fr.pantheonsorbonne.cri.UserFolder.*;
import fr.pantheonsorbonne.cri.ControllerFolder.*;
import fr.pantheonsorbonne.cri.GroupFolder.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class MeetingCases {
    private static Scanner scanner = new Scanner(System.in);

    private MeetingCases() {
        throw new IllegalStateException("Utility class");
    }

    public static void viewMeetings(Group currentGroup, User currentUser) {
        boolean meetingRunning = true;
        while (meetingRunning) {
            String meetingName = scanner.nextLine();
            Meeting currentMeeting = MeetingDatabase.getMeetingByName(meetingName);
            if (currentMeeting == null) {
                throw new NoSuchElementException("The meeting doesn't exist");
            } else if (currentUser != currentMeeting.getMeetingAdmin()) {
                while (meetingRunning) {
                    System.out.println("Meeting Menu :");
                    System.out.println("1. Join Meeting");
                    System.out.println("2. Leave Meeting");
                    System.out.println("0. Back to the Group Menu");
                    System.out.print("Please choose an option : ");
                    if (scanner.hasNextInt()) {
                        int meetingResult = meetingMemberSelectionCases(currentMeeting, currentUser);
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
                    System.out.println("0. Back to the Group Menu");
                    System.out.print("Please choose an option : ");
                    if (scanner.hasNextInt()) {
                        int meetingResult = meetingAdminSelectionCases(currentMeeting);
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
            System.out.println("Meeting Menu :");
            System.out.println("0. Back to the Group Menu");
            System.out.print("Please choose an option : ");

        }
    }

    public static int meetingMemberSelectionCases(Meeting currentMeeting, User currentUser) {
        int meetingOption = scanner.nextInt();
        switch (meetingOption) {
            case 1:
                System.out.println("You chose to join the meeting.");
                MeetingController.joinMeeting(currentUser, currentMeeting);
                break;
            case 2:
                System.out.println("You chose to leave the meeting.");
                MeetingController.leaveMeeting(currentUser, currentMeeting);
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

    public static int meetingAdminSelectionCases(Meeting currentMeeting) {
        int meetingOption = scanner.nextInt();
        switch (meetingOption) {
            case 1:
                System.out.println("You chose to remove the meeting.");
                MeetingController.removeMeeting(currentMeeting);
                return 1;
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
