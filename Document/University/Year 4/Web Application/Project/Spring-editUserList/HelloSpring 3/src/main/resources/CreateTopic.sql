CREATE TABLE topic(
    id INTEGER NOT NULL,
    creater VARCHAR(20) NOT NULL,
    title VARCHAR(50) NOT NULL,
    body VARCHAR(1000) NOT NULL,
    catId INTEGER,
    PRIMARY KEY(id),
    CONSTRAINT Fk_CatTopic FOREIGN KEY(catId) REFERENCES category(id)
);


