BEGIN;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS restaurant (
    restaurant_id  UUID PRIMARY KEY,
    name           TEXT NOT NULL,
    address1       TEXT,
    suburb         TEXT,
    open_time      TIME NOT NULL,
    close_time     TIME NOT NULL
);

CREATE TABLE IF NOT EXISTS deal (
    deal_id        UUID PRIMARY KEY,
    restaurant_id  UUID NOT NULL REFERENCES restaurant(restaurant_id) ON DELETE CASCADE,
    discount       TEXT,
    dine_in        BOOLEAN,
    lightning      BOOLEAN,
    qty_left       INTEGER,
    start_time     TIME NULL,
    end_time       TIME NULL
);

CREATE TABLE IF NOT EXISTS cuisine (
    cuisine_id     UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name           TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS restaurant_cuisine (
    restaurant_id  UUID NOT NULL REFERENCES restaurant(restaurant_id) ON DELETE CASCADE,
    cuisine_id     UUID NOT NULL REFERENCES cuisine(cuisine_id) ON DELETE RESTRICT,
    PRIMARY KEY (restaurant_id, cuisine_id)
);

CREATE INDEX idx_restaurant_suburb ON restaurant(suburb);
CREATE INDEX idx_deal_restaurant ON deal(restaurant_id);
CREATE INDEX idx_restaurant_cuisine_cuisine ON restaurant_cuisine(cuisine_id);

COMMIT;
