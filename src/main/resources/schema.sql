CREATE TABLE chat_room (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL
);

CREATE TABLE chat_message (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              content TEXT NOT NULL,
                              created_at TIMESTAMP NOT NULL,
                              chat_room_id BIGINT NOT NULL,
                              username VARCHAR(255) NOT NULL,
                              FOREIGN KEY (chat_room_id) REFERENCES chat_room(id)
);