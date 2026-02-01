BEGIN;

CREATE TABLE IF NOT EXISTS restaurant (
    restaurant_id  TEXT PRIMARY KEY,
    name           TEXT NOT NULL,
    address1       TEXT,
    suburb         TEXT,
    open_time      TIME NOT NULL,
    close_time     TIME NOT NULL
);

CREATE TABLE IF NOT EXISTS deal (
    deal_id        TEXT PRIMARY KEY,
    restaurant_id  TEXT NOT NULL,
    discount       TEXT,
    dine_in        BOOLEAN,
    lightning      BOOLEAN,
    qty_left       INTEGER,
    start_time     TIME NULL,
    end_time       TIME NULL
);

CREATE TABLE IF NOT EXISTS cuisine (
    cuisine_id     TEXT PRIMARY KEY,
    name           TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS restaurant_cuisine (
    restaurant_id  TEXT NOT NULL,
    cuisine_id     TEXT NOT NULL,
    PRIMARY KEY (restaurant_id, cuisine_id)
);

COMMIT;
