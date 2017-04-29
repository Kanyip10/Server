CREATE TABLE attachments(
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
    attachmentName VARCHAR(200) NOT NULL,
    contentType VARCHAR(200) NOT NULL,
    content BLOB NOT NULL,
    topicId INTEGER,
    replyId INTEGER,
    PRIMARY KEY(id),
    CONSTRAINT Fk_TopicAttachment FOREIGN KEY(topicId) REFERENCES topic(id),
    CONSTRAINT Fk_ReplyAttachment FOREIGN KEY(replyId) REFERENCES reply(id)
);

