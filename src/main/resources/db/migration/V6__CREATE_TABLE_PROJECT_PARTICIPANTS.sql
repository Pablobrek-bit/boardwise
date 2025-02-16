CREATE TABLE IF NOT EXISTS project_participants (
    project_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (project_id, user_id),
    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);