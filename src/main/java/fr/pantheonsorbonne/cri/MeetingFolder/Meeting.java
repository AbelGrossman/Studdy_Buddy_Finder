package fr.pantheonsorbonne.cri.MeetingFolder;

import fr.pantheonsorbonne.cri.UserFolder.*;
import fr.pantheonsorbonne.cri.GroupFolder.*;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import java.time.LocalDate;
import java.time.LocalTime;

public class Meeting {

    private int meetingId;
    private String meetingName;
    private Group meetingGroup;
    private LocalDate meetingDate;
    private LocalTime meetingStartTime;
    private LocalTime meetingEndTime;
    private String meetingLocation;
    private double latitude;
    private double longitude;
    private String googleMapsLink;
    private boolean reservationRequired;
    private User meetingAdmin;

    public Meeting(int meetingId, String meetingName, Group meetingGroup, LocalDate meetingDate,
            LocalTime meetingStartTime,
            LocalTime meetingEndTime,
            String meetingLocation,
            boolean reservationRequired, User meetingAdmin) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.meetingGroup = meetingGroup;
        this.meetingDate = meetingDate;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
        this.meetingLocation = meetingLocation;
        this.latitude = 0;
        this.longitude = 0;
        this.googleMapsLink = generateGoogleMapsLink(meetingLocation);
        this.reservationRequired = reservationRequired;
        this.meetingAdmin = meetingAdmin;
    }

    private String generateGoogleMapsLink(String meetingLocation) {
        try {
            GeoApiContext context = new GeoApiContext.Builder().apiKey("YOUR_API_KEY").build();
            GeocodingResult[] results = GeocodingApi.geocode(context, meetingLocation).await();
            if (results.length > 0) {
                this.latitude = results[0].geometry.location.lat;
                this.longitude = results[0].geometry.location.lng;
                return "https://www.google.com/maps?q=" + this.latitude + "," + this.longitude;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    public LocalTime getMeetingStartTime() {
        return meetingStartTime;
    }

    public LocalTime getMeetingEndTime() {
        return meetingEndTime;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public boolean isReservationRequired() {
        return reservationRequired;
    }

    public User getMeetingAdmin() {
        return meetingAdmin;
    }

    public Group getMeetingGroup() {
        return meetingGroup;
    }

    public String getGoogleMapsLink() {
        return googleMapsLink;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        this.meetingDate = meetingDate;
    }

    public void setMeetingStartTime(LocalTime meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public void setMeetingEndTime(LocalTime meetingEndTime) {
        this.meetingEndTime = meetingEndTime;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    public void setReservationRequired(boolean reservationRequired) {
        this.reservationRequired = reservationRequired;
    }

}
