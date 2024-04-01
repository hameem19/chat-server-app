-- Create the chat_room table if it doesn't exist
CREATE TABLE IF NOT EXISTS chat_room (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create the chat_message table if it doesn't exist
CREATE TABLE IF NOT EXISTS chat_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    chat_room_id BIGINT NOT NULL,
    username VARCHAR(255) NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (chat_room_id) REFERENCES chat_room(id)
);
