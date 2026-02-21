-- Create tables for the university system

CREATE TABLE IF NOT EXISTS departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS teachers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    department_id BIGINT REFERENCES departments(id)
);

CREATE TABLE IF NOT EXISTS students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    department_id BIGINT REFERENCES departments(id)
);

CREATE TABLE IF NOT EXISTS courses (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    department_id BIGINT REFERENCES departments(id),
    teacher_id BIGINT REFERENCES teachers(id)
);

CREATE TABLE IF NOT EXISTS student_courses (
    student_id BIGINT NOT NULL REFERENCES students(id),
    course_id BIGINT NOT NULL REFERENCES courses(id),
    PRIMARY KEY (student_id, course_id)
);

-- Insert sample data
INSERT INTO departments (name) VALUES ('Computer Science'), ('Mathematics') ON CONFLICT DO NOTHING;

INSERT INTO teachers (name, department_id) VALUES
    ('Alice', 1),
    ('Bob', 2)
ON CONFLICT DO NOTHING;

INSERT INTO students (name, department_id) VALUES
    ('John', 1),
    ('Mary', 2)
ON CONFLICT DO NOTHING;

INSERT INTO courses (title, department_id, teacher_id) VALUES
    ('Algorithms', 1, 1),
    ('Calculus', 2, 2)
ON CONFLICT DO NOTHING;

INSERT INTO student_courses (student_id, course_id) VALUES
    (1, 1),
    (1, 2),
    (2, 1)
ON CONFLICT DO NOTHING;

