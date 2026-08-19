# Project Structure

## UML Diagram

```mermaid
classDiagram

    class Customer {
        -int id
        -String name
        -String email
        -String phone
        -CustomerType customerType
        -String organizationNumber
        +int getId()
        +String getName()
        +String getEmail()
        +String getPhone()
        +CustomerType getCustomerType()
        +String getOrganizationNumber()
    }

    class CustomerType {
        <<enumeration>>
        COMPANY
        INDIVIDUAL
    }

    class Classroom {
        -int id
        -String name
        -int capacity
        -boolean isDisabilityAccessible
        -Set~Equipment~ equipment
        +boolean hasRequiredEquipment(Set~Equipment~ requiredEquipment)
        +boolean meetsCapacity(int requiredCapacity)
    }

    class Equipment {
        <<enumeration>>
        PROJECTOR
        WHITEBOARD
    }

    class BookingUser {
        -int id
        -String username
        -String email
    }

    class Booking {
        -int id
        -LocalDateTime startDateTime
        -LocalDateTime endDateTime
        -String comments
        -Classroom classroom
        -Customer customer
        -BookingUser bookingUser
        +boolean overlaps(LocalDateTime requestedStart, LocalDateTime requestedEnd)
    }

    Customer --> CustomerType
    Classroom "1" --> "*" Equipment
    Customer "1" --> "*" Booking
    Classroom "1" --> "*" Booking
    BookingUser "1" --> "*" Booking

```


* A Customer can have many Bookings.
* A Classroom can have many Bookings, but not at overlapping times.
* A BookingUser creates bookings.
* A Booking belongs to one customer, one classroom, and one booking user.
* A classroom can contain multiple pieces of equipment.
* A customer can either be a CompanyCustomer or an IndividualCustomer.