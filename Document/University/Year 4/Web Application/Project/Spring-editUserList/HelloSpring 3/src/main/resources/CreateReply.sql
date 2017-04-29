CREATE TABLE reply(
    id INTEGER NOT NULL,
    replier VARCHAR(20) NOT NULL,
    body VARCHAR(50) NOT NULL,
    topicId INTEGER,
    PRIMARY KEY(id),
    CONSTRAINT Fk_TopicReply FOREIGN KEY(topicId) REFERENCES topic(id)
);

