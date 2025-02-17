
```mermaid
erDiagram
    User {
        Long id
        String name
        String email
        String password
        Role role
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
    Delivery {
        Long id
        String description
        LocalDateTime deliveryDate
        DeliveryStatus status
        User user
        LocalDateTime createdAt
        LocalDateTime updatedAt
    }
    Role {
        <<enumeration>>
        ADMIN
        USER
    }
    DeliveryStatus {
        <<enumeration>>
        PENDING
        IN_TRANSIT
        DELIVERED
    }
    User ||--o{ Delivery : "has"
    Delivery ||--o{ User : "has"
```