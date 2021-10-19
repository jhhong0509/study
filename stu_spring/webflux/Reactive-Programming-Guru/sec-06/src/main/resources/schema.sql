CREATE TABLE if NOT EXISTS beer
(
    id             integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    beer_name      varchar(255),
    beer_style     varchar (255),
    upc            varchar (25),
    version        integer,
    quantity_on_hand integer,
    price          decimal,
    created_date   timestamp,
    last_modified_date timestamp
);