# Database Design

## Purpose

This document describes the planned database structure for the Classroom Rental App.

The database design is based on the project requirements, use cases, and domain model. The purpose is to define how persistent data should be represented before the JDBC implementation is created.

---

## Database Technology

The application shall use a relational SQL database accessed through JDBC.

The specific database implementation will be selected before persistence is implemented.

The design should avoid unnecessary database-specific features so that the persistence model remains simple and portable.

---

## Main Tables

The current domain model requires persistent storage for the following concepts:

* Customer
* Booking User
* Classroom
* Booking
* Classroom Equipment

---

## Customer

The `customer` table represents the customer or billing entity associated with bookings.

A customer may represent either a company or an individual client.

### Planned fields

* `id` — primary key
* `name` — customer or billing name
* `email`
* `phone`
* `customer_type` — identifies whether the customer is a company or an individual
* `organization_number` — optional and primarily applicable to company customers

### Relationships

A customer:

* has one or more booking users
* may have zero or more bookings

---

## Booking User

The `booking_user` table represents a person who can create bookings on behalf of a customer.

### Planned fields

* `id` — primary key
* `customer_id` — foreign key referencing `customer`
* `name`
* `email`

### Relationships

Each booking user:

* belongs to exactly one customer
* may create zero or more bookings

A customer may have multiple booking users.

This model also allows an individual customer to have a booking user representing the individual themselves.

---

## Classroom

The `classroom` table represents one of the classrooms managed by the application.

### Planned fields

* `id` — primary key
* `name`
* `capacity`
* `disability_accessible`

### Relationships

A classroom:

* may contain zero or more equipment types
* may have zero or more bookings

The application is expected to manage 20 classrooms.

---

## Equipment

Equipment is represented in the Java domain model as an enum because only predefined equipment types are required.

A classroom may contain multiple equipment types, so the database must support a many-to-many style relationship between classrooms and equipment values.

A simple relational representation is planned using a junction table.

### `classroom_equipment`

Planned fields:

* `classroom_id` — foreign key referencing `classroom`
* `equipment_type` — stored representation of an `Equipment` enum value

The combination of `classroom_id` and `equipment_type` should be unique so that the same equipment type cannot be assigned to a classroom more than once.

This avoids creating a full equipment entity when the system only needs to know whether a classroom provides a particular equipment type.

---

## Booking

The `booking` table represents a reservation of a classroom.

### Planned fields

* `id` — primary key
* `customer_id` — foreign key referencing `customer`
* `booking_user_id` — foreign key referencing `booking_user`
* `classroom_id` — foreign key referencing `classroom`
* `start_date_time`
* `end_date_time`
* `comments` — optional

### Relationships

Each booking:

* belongs to exactly one customer
* is created by exactly one booking user
* reserves exactly one classroom

A customer, booking user, or classroom may be associated with many bookings.

---

## Booking Rules

The database relationships guarantee that bookings reference existing customers, booking users, and classrooms.

The following business rules require application-level validation:

* The booking end time must be later than the start time.
* Two bookings for the same classroom must not overlap.
* The selected classroom must meet the requested capacity.
* The selected classroom must contain all required equipment.
* The selected classroom must meet the requested accessibility requirement.

The application should perform these validations before inserting a booking.

---

## Referential Integrity

Foreign keys should be used to maintain valid relationships between tables.

Planned foreign keys include:

* `booking_user.customer_id` → `customer.id`
* `booking.customer_id` → `customer.id`
* `booking.booking_user_id` → `booking_user.id`
* `booking.classroom_id` → `classroom.id`
* `classroom_equipment.classroom_id` → `classroom.id`

Deletion behavior should be decided before the database schema is implemented.

In particular, the system must decide what should happen if a customer, booking user, or classroom has existing bookings.

---

## Date and Time Storage

The Java domain model uses `LocalDateTime` for booking start and end times.

The exact SQL representation will depend on the selected database implementation.

Conversion between SQL values and `LocalDateTime` will be handled by the persistence layer.

---

## Pending Database Decisions

The following decisions should be resolved before implementing the database:

* Which SQL database implementation should be used.
* How Java enum values should be stored in SQL.
* What deletion behavior should apply to records referenced by bookings.
* Whether database-level constraints should supplement application-level booking validation.
