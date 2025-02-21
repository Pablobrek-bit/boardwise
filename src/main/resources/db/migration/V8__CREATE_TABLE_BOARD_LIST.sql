CREATE TABLE IF NOT EXISTS board_list (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    position INT NOT NULL,
    project_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);