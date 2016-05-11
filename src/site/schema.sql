CREATE TABLE Message
(
    id NUMERIC(19) PRIMARY KEY NOT NULL,
    createdDate DATETIME,
    updateDate DATETIME,
    version NUMERIC(19),
    description TEXT NOT NULL,
    image VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL
);

CREATE TABLE hibernate_sequence
(
    next_val NUMERIC(19)
);