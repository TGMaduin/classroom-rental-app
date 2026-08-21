package se.lexicon.model;

import java.time.LocalDateTime;

public class Booking {

    private int id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String comments;
    private Customer customer;
    private BookingUser bookingUser;
    private Classroom classroom;

    /* --- This constructor is used when creating a new booking, the DB automatically generates the id --- */
    public Booking(LocalDateTime startDateTime, LocalDateTime endDateTime, String comments, Customer customer, BookingUser bookingUser, Classroom classroom) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.comments = comments;
        this.customer = customer;
        this.bookingUser = bookingUser;
        this.classroom = classroom;
    }

    /* --- This constructor is used when reading an existing booking from the DB --- */
    public Booking(int id, LocalDateTime startDateTime, LocalDateTime endDateTime, String comments, Customer customer, BookingUser bookingUser, Classroom classroom) {
        this(startDateTime, endDateTime, comments, customer, bookingUser, classroom);
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

    public Customer getCustomer() {
        return customer;
    }

    public BookingUser getBookingUser() {
        return bookingUser;
    }

    public Classroom getClassroom() {
        return classroom;
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
