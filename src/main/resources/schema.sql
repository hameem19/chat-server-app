CREATE TABLE chat_message (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              sender VARCHAR(255) NOT NULL,
                              room_id VARCHAR(255) NOT NULL,
                              content TEXT NOT NULL
);