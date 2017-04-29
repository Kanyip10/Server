CREATE TABLE category(
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
    catName VARCHAR(20) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO category(catName) VALUES('Lecture');
INSERT INTO category(catName) VALUES('Lab');
INSERT INTO category(catName) VALUES('Others');






