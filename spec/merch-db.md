```mermaid
erDiagram
  %% Tables
  merch {
    uuid merch_id PK
    int status
    uuid vendor
    uuid rack
    string title
    decimal price
    clob description
    timestamp created_at
    string created_by
    timestamp modified_at
    string modified_by
  }
  image {
    uuid image_id PK
    uuid merch_id FK
    string title
    string uri
    timestamp created_at
    string created_by
    timestamp modified_at
    string modified_by
  }
  space {
    uuid space_id PK
    uuid parent FK
    string name
    timestamp created_at
    string created_by
    timestamp modified_at
    string modified_by
  }

  %% Relations
  merch |o--|{ image : "can have"
  merch }|--|| rack : "is part of"
  rack }|--|| rack : "is parent of"
```