CREATE USER library WITH PASSWORD 'library123';
CREATE DATABASE library WITH OWNER library;
GRANT ALL PRIVILEGES ON DATABASE library TO library;