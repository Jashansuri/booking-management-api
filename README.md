# Restaurant Deals Database Schema
## Database: PostgreSQL
- **Relational data structure** - For restaurants and deals relationship
- **Data integrity** - Foreign keys and constraints keep data consistent
- **Good performance** - Handles complex queries efficiently
- **JSON support**
- **Free and open source**

## Schema Overview
- **Red text** - PK
- **Orange text** - FK

### Tables
**restaurants**
- Stores restaurant information

**cuisines**
- List of cuisine types

**restaurant_cuisine**
- Table linking restaurants to their cuisines (many-to-many relationship for cuisine and restaurant)

**deals**
- Stores deal information (one-to-many)
- Links to restaurants via restaurant_id

### Relationships
```
RESTAURANTS (1) ─→ (N) RESTAURANT_CUISINES (N) ─→ (1) CUISINES
   │ (1)
   │
   ↓ (N)
 DEALS
```

## Key Features
- Stops duplication of cuisines for each restaurant
- Removing a restaurant automatically removes its deals and cuisine links

## Sample Queries
### Find active deals for timeOfDay
```sql
SELECT r.name AS name, r.suburb, d.discount, d.qty_left
FROM restaurant r
JOIN deal d ON r.restaurant_id = d.restaurant_id
WHERE $timeOfDay::TIME BETWEEN COALESCE(d.start_time, r.open_time) AND COALESCE(d.end_time, r.close_time);
```