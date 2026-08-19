# Requirements

## Purpose

This document defines the functional, business and technical requirements for the Classroom Rental App.

The application is intended for a facility management company that manages 20 classrooms and rents them to external customers.

---

# Functional Requirements

## Customer Management

FR-01 The system shall allow users to register customers.

FR-02 A customer shall be either a company or an individual client.

## Classroom Management

FR-03 The system shall manage 20 classrooms.

FR-04 Each classroom shall have a name.

FR-05 Each classroom shall have a seating capacity.

FR-06 Each classroom shall provide information about available equipment.

FR-07 Each classroom shall indicate whether it is accessible for people with disabilities.

## Classroom Search

FR-08 The system shall allow users to search for available classrooms for a specified time period.

FR-09 The search shall support minimum seating capacity.

FR-10 The search shall support equipment requirements.

FR-11 The search shall support accessibility requirements.

## Booking Management

FR-12 The system shall allow users to create bookings.

FR-13 A booking shall be associated with a classroom.

FR-14 A booking shall specify a start and end date/time.

FR-15 A booking shall be associated with a customer.

FR-16 A booking shall be associated with the booking user who created it.

FR-17 A booking may contain optional comments.

## Booking Views

FR-18 The system shall display bookings for a specific classroom.

FR-19 The system shall display bookings for a specific customer.

FR-20 The system shall display all upcoming bookings.

## User Interface

FR-21 Users shall interact with the system through a console menu.

---

# Business Rules

BR-01 A classroom shall not have overlapping bookings.

BR-02 A booking may only be created if the classroom is available.

BR-03 The selected classroom shall satisfy the requested seating capacity.

BR-04 The selected classroom shall satisfy the requested equipment requirements.

BR-05 The selected classroom shall satisfy the requested accessibility requirements.

BR-06 Every booking shall reference an existing customer.

BR-07 Every booking shall reference the booking user who created it.

---

# Technical Requirements

TR-01 The application shall be implemented in Java.

TR-02 The application shall demonstrate object-oriented programming.

TR-03 The application shall use SQL together with JDBC for persistence.

TR-04 The application shall support creating, updating, deleting and retrieving persistent data.

TR-05 The application shall use Java Collections.

TR-06 The application shall use Streams and Lambda expressions where appropriate.

TR-07 The application shall use exception handling.

---

# Documentation Requirements

DR-01 The project shall include a UML class diagram.

DR-02 The project shall include a README containing setup instructions.

---

# Version Control Requirements

VR-01 The project shall use Git for version control.

VR-02 Development shall use feature branches.

VR-03 Development shall use frequent commits with meaningful commit messages.

VR-04 Feature branches shall be merged into the main branch.

---

# Pending Design Decisions

The following questions will be answered during the domain modeling phase.

- How should companies and individual customers be represented?
- What information should be stored about a booking user?
- Can a company have multiple booking users?
- How should classroom equipment be represented?
- Should date and time be represented using LocalDateTime or another Java time class?
- Which SQL database implementation should be used (SQLite, MySQL, etc.)?