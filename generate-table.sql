-- Creates a database and a table for storing data
CREATE DATABASE IF NOT EXISTS ebookstore;
USE ebookstore;
CREATE TABLE books(
id varchar(4) NOT NULL,
title varchar(55),
author varchar(55),
qty int,
PRIMARY KEY(id)
);

-- Populates the table with data
INSERT INTO books 
VALUES
("3001", "A Tale of Two Cities" , "Charles Dickens", 30),
("3002", "Harry Potter and the Philosopher's Stone", "J.K. Rowling", 40),
("3003", "The Lion, the Witch and the Wardrobe", "C. S. Lewis", 25),
("3004", "The Lord of the Rings", "J.R.R Tolkien", 37),
("3005", "Alice in Wonderland", "Lewis Carroll", 12),
("3006", "A Game of Thrones", "G.R.R Martin", 27),
("3007", "The Eye of the World", "Robert Jordan", 15)
;