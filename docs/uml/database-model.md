# Database Model

## ER Diagram

```mermaid
erDiagram

    CUSTOMER ||--|{ BOOKING_USER : has
    CUSTOMER ||--o{ BOOKING : billed_for
    BOOKING_USER ||--o{ BOOKING : creates
    CLASSROOM ||--o{ BOOKING : reserved_for
    CLASSROOM ||--o{ CLASSROOM_EQUIPMENT : has

    CUSTOMER {
        int id PK
        string name
        string email
        string phone
        string customer_type
        string organization_number
    }

    BOOKING_USER {
        int id PK
        int customer_id FK
        string name
        string email
    }

    CLASSROOM {
        int id PK
        string name
        int capacity
        boolean disability_accessible
    }

    CLASSROOM_EQUIPMENT {
        int classroom_id PK, FK
        string equipment_type PK
    }

    BOOKING {
        int id PK
        int customer_id FK
        int booking_user_id FK
        int classroom_id FK
        datetime start_date_time
        datetime end_date_time
        string comments
    }