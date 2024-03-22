package fr.pantheonsorbonne.cri.UserFolder;

import fr.pantheonsorbonne.cri.MeetingFolder.Meeting;
import fr.pantheonsorbonne.cri.StuddyBuddyFolder.FindUser;

import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String location1;
    private String location2;
    private String interest1;
    private String interest2;
    private String userStudies;
    private Calendar calendar;

    public User(int userId, String firstName, String lastName, String userName, String email, String password,
            String location1, String location2, String interest1, String interest2,
            String userStudies) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userEmail = email;
        this.userPassword = password;
        this.location1 = location1;
        this.location2 = location2;
        this.interest1 = interest1;
        this.interest2 = interest2;
        this.userStudies = userStudies;
    }

    public void addEventToCalendar(Meeting meeting) {
        try {
            Event event = new Event()
                    .setSummary("Meeting: " + meeting.getMeetingId())
                    .setDescription("Meeting with group: " + meeting.getMeetingGroup().getGroupName());

            LocalDate meetingDate = meeting.getMeetingDate();
            LocalTime startTime = meeting.getMeetingStartTime();
            LocalTime endTime = meeting.getMeetingEndTime();

            LocalDateTime startDateTime = LocalDateTime.of(meetingDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(meetingDate, endTime);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
            String startStr = startDateTime.format(formatter);
            String endStr = endDateTime.format(formatter);

            DateTime start = new DateTime(startStr);
            DateTime end = new DateTime(endStr);

            EventDateTime startEventDateTime = new EventDateTime().setDateTime(start);
            EventDateTime endEventDateTime = new EventDateTime().setDateTime(end);

            event.setStart(startEventDateTime);
            event.setEnd(endEventDateTime);

            String calendarId = "primary";
            this.calendar.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeEventFromCalendar(Meeting meeting) {
        try {
            this.calendar.events().delete("primary", "Meeting: " + meeting.getMeetingId()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getCalendarEvents() {
        try {
            // Fetch events from the user's calendar
            DateTime now = new DateTime(System.currentTimeMillis());
            Events events = this.calendar.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();

            // Get the list of events
            List<Event> items = events.getItems();
            return items;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUserStudies() {
        return this.userStudies;
    }

    public void setUserStudies(String userStudies) {
        this.userStudies = userStudies;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getLocation1() {
        return location1;
    }

    public void setLocation1(String location1) {
        this.location1 = location1;
    }

    public String getLocation2() {
        return location2;
    }

    public void setLocation2(String location2) {
        this.location2 = location2;
    }

    public String getInterest1() {
        return this.interest1;
    }

    public void setInterest1(String interest1) {
        this.interest1 = interest1;
    }

    public String getInterest2() {
        return this.interest2;
    }

    public void setInterest2(String interest2) {
        this.interest2 = interest2;
    }

    public void setUserId() {
        this.userId = fetchUserId();
    }

    private int fetchUserId() {
        User dataBaseUser = FindUser.getUserByUsername(this.userName);
        return dataBaseUser.getUserId();
    }

    public int getUserId() {
        return this.userId;
    }

    public Calendar getCalendar() {
        return this.calendar;
    }
}
