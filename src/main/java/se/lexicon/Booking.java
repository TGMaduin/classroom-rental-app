package se.lexicon;

import java.time.LocalDateTime;

public class Booking {

    private int id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String comments;
    private int customerId;
    private int bookingUserId;
    private int classroomId;

    /* --- This constructor is used when creating a new booking, the DB automatically generates the id --- */
    public Booking(LocalDateTime startDateTime, LocalDateTime endDateTime, String comments, int customerId, int bookingUserId, int classroomId) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.comments = comments;
        this.customerId = customerId;
        this.bookingUserId = bookingUserId;
        this.classroomId = classroomId;

    }

    /* --- This constructor is used when reading an existing booking from the DB --- */
    public Booking(int id, LocalDateTime startDateTime, LocalDateTime endDateTime, String comments, int customerId, int bookingUserId, int classroomId) {
        this(startDateTime, endDateTime, comments,  customerId, bookingUserId, classroomId);
        this.id = id;
    }

    /* --- Getters & Setters --- */
    public int getId(){
        return id;
    }

    public LocalDateTime getStartDateTime(){
        return startDateTime;
    }

    public LocalDateTime getEndDateTime(){
        return endDateTime;
    }

    public String getComments(){
        return comments;
    }

    public void setStartDateTime(LocalDateTime startDateTime){
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime){
        this.endDateTime = endDateTime;
    }

    public void setComments(String comments){
        this.comments = comments;
    }

    /* --- Check if two times overlap --- */
    public boolean overlaps(LocalDateTime start, LocalDateTime end){
        return startDateTime.isBefore(end) && endDateTime.isAfter(start);
    }

    /* --- Print info layout --- */
    @Override
    public String toString(){
        return String.format(
                "Booking %d | Date and time: %s - %s | Comments: %s",
                id, startDateTime, endDateTime, comments == null || comments.isBlank() ? "None" : comments
        );
    }
}
