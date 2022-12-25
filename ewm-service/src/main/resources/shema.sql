CREATE TABLE IF NOT EXISTS USERS
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR                                 NOT NULL,
    email VARCHAR                                 NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS CATEGORIES
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR                                 NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT UQ_CATEGORY_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS LOCATION
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lat FLOAT                                   NOT NULL,
    lon FLOAT                                   NOT NULL,
    CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS EVENTS
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation       VARCHAR(2000)                           NOT NULL,
    category_id      INTEGER REFERENCES CATEGORIES (id),
    confirmedRequest INTEGER,
    createdOn        TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    description      VARCHAR(7000)                           NOT NULL,
    eventDate        TIMESTAMP                               NOT NULL,
    initiator_id     INTEGER REFERENCES USERS (id),
    location_id      INTEGER REFERENCES LOCATION (id),
    paid             boolean,
    participantLimit INTEGER,
    publishedOn      TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    requestModerator BOOLEAN,
    state            VARCHAR(20)                             NOT NULL,
    title            VARCHAR                                 NOT NULL,
    views            INTEGER,
    CONSTRAINT pk_events PRIMARY KEY (id)

);

CREATE TABLE IF NOT EXISTS REQUEST
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created  VARCHAR                                 NOT NULL,
    event_id INTEGER REFERENCES EVENTS (id),
    user_id  INTEGER REFERENCES USERS (id),
    status   VARCHAR                                 NOT NULL
);

CREATE TABLE IF NOT EXISTS COMPILATIONS
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN,
    title  VARCHAR                                 NOT NULL,
    CONSTRAINT pk_compilations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS COMPILATIONS_EVENTS
(
    compilation_id INTEGER REFERENCES COMPILATIONS (id),
    event_id       INTEGER REFERENCES EVENTS (id)
)
