# Use Cases

## Purpose

This document describes the primary user interactions with the Classroom Rental App.

The use cases are based on the project requirements and are intended to guide domain modeling, architecture, and implementation.

---

## UC-01 Register Customer

**Primary Actor:** Staff user

**Preconditions:**

* The application is running.

**Main Flow:**

1. The user selects the option to register a customer.
2. The system asks whether the customer is a company or an individual.
3. The user enters the required customer information.
4. The system validates the entered information.
5. The system saves the customer.
6. The system confirms that the customer was registered.

**Postconditions:**

* A new customer exists in persistent storage.

**Alternative Flows:**

* If required information is invalid or missing, the system displays an error and does not save the customer.

---

## UC-02 Register Booking User

**Primary Actor:** Staff user

**Preconditions:**

* The customer already exists.

**Main Flow:**

1. The user selects a customer.
2. The user selects the option to register a booking user.
3. The user enters the booking user's information.
4. The system validates the entered information.
5. The system associates the booking user with the selected customer.
6. The system saves the booking user.
7. The system confirms that the booking user was registered.

**Postconditions:**

* A booking user exists and is associated with a customer.

**Alternative Flows:**

* If the selected customer does not exist, the operation is cancelled.
* If required information is invalid or missing, the booking user is not saved.

---

## UC-03 Search Available Classrooms

**Primary Actor:** Booking user

**Preconditions:**

* The booking user exists.

**Main Flow:**

1. The booking user enters a requested start date and time.
2. The booking user enters a requested end date and time.
3. The booking user enters the required seating capacity.
4. The booking user selects any required equipment.
5. The booking user specifies whether disability accessibility is required.
6. The system validates the requested time range.
7. The system finds classrooms that meet the capacity requirement.
8. The system filters classrooms based on required equipment.
9. The system filters classrooms based on accessibility requirements.
10. The system excludes classrooms with bookings that overlap the requested time range.
11. The system displays the available classrooms.

**Postconditions:**

* A list of classrooms matching the search criteria is displayed.

**Alternative Flows:**

* If the requested end time is not later than the start time, the system displays an error.
* If no classrooms satisfy the requirements, the system informs the user that no classrooms are available.

---

## UC-04 Create Booking

**Primary Actor:** Booking user

**Preconditions:**

* The booking user exists.
* The associated customer exists.
* At least one classroom matching the requested requirements is available.

**Main Flow:**

1. The booking user searches for available classrooms.
2. The booking user selects an available classroom.
3. The booking user may enter optional comments or special requirements.
4. The system verifies that the classroom is still available for the requested time range.
5. The system creates the booking.
6. The booking is associated with the selected classroom.
7. The booking is associated with the customer.
8. The booking is associated with the booking user who created it.
9. The system saves the booking.
10. The system confirms that the booking was created.

**Postconditions:**

* A new booking exists in persistent storage.

**Alternative Flows:**

* If the classroom is no longer available, the booking is rejected.
* If the classroom does not satisfy the requested requirements, the booking is rejected.
* If invalid booking data is supplied, the booking is not saved.

---

## UC-05 View Bookings by Classroom

**Primary Actor:** Staff user

**Preconditions:**

* The selected classroom exists.

**Main Flow:**

1. The user selects the option to view bookings by classroom.
2. The user selects a classroom.
3. The system retrieves bookings associated with the classroom.
4. The system displays the bookings.

**Postconditions:**

* The bookings for the selected classroom are displayed.

**Alternative Flows:**

* If the classroom has no bookings, the system informs the user.

---

## UC-06 View Bookings by Customer

**Primary Actor:** Staff user

**Preconditions:**

* The selected customer exists.

**Main Flow:**

1. The user selects the option to view bookings by customer.
2. The user selects a customer.
3. The system retrieves bookings associated with the customer.
4. The system displays the bookings.

**Postconditions:**

* The bookings for the selected customer are displayed.

**Alternative Flows:**

* If the customer has no bookings, the system informs the user.

---

## UC-07 View Upcoming Bookings

**Primary Actor:** Staff user

**Preconditions:**

* The application is running.

**Main Flow:**

1. The user selects the option to view upcoming bookings.
2. The system retrieves bookings whose start time is in the future.
3. The system sorts the bookings chronologically.
4. The system displays the bookings.

**Postconditions:**

* Upcoming bookings are displayed in chronological order.

**Alternative Flows:**

* If there are no upcoming bookings, the system informs the user.

---

## Notes

The project specification does not explicitly define separate authentication or authorization functionality. The term `BookingUser` is therefore treated as a domain entity representing the person who creates a booking, rather than as a complete login or security system.

The project specification also does not explicitly require booking cancellation, customer editing, or classroom administration through the console interface. These features should not be added unless they are later required or deliberately chosen as extensions.
