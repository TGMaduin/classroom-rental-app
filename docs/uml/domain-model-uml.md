```mermaid
classDiagram

    class Customer {
        -int id
        -String name
        -String email
        -String phone
        -CustomerType type
        -String organizationNumber
    }

    class CustomerType {
        <<enumeration>>
        COMPANY
        INDIVIDUAL
    }

    class BookingUser {
        -int id
        -String name
        -String email
    }

    class Booking {
        -int id
        -LocalDateTime startDateTime
        -LocalDateTime endDateTime
        -String comments
        +boolean overlaps(LocalDateTime start, LocalDateTime end)
    }

    class Classroom {
        -int id
        -String name
        -int capacity
        -boolean disabilityAccessible
        -Set~Equipment~ equipment
        +boolean meetsCapacity(int requiredCapacity)
        +boolean hasRequiredEquipment(Set~Equipment~ requiredEquipment)
    }

    class Equipment {
        <<enumeration>>
        PROJECTOR
        WHITEBOARD
    }

    Customer "1" --> "1..*" BookingUser : has
    Customer "1" --> "0..*" Booking : billed to
    BookingUser "1" --> "0..*" Booking : creates
    Classroom "1" --> "0..*" Booking : reserved for
```git fe