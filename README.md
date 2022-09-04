# Bookstore Manager
A program to be used by a bookstore to allow a clerk to access a database and perform a number of functions on the book data. I have also included the SQL script to recreate the database and table that I used.
## Menu
When the user starts the program, they will be given the menu below:
1 - Enter a New Book\
2 - Update a Book Entry\
3 - Delete a Book\
4 - Search Books\
5 - Create a Sale Notice for Highest Quantity Product\
0 - Exit

## Features
### Enter Book
This allows the user to enter details (ID, title, author and quantity) for a new book and adds them to the table.
### Update Book
This allows the user to change the details of a book if they give the ID number for the book.
### Delete Book
This allows the user to delete a book with the given ID number.
### Search Books
This allows a user to search the table for any books that contain the search term in the ID, title or author columns.
### Create Sale Notice
This allows the user to generate a sale notice for the book with the highest quantity of stock in the following format:\
{Highest Quantity Book} is now on sale! Hurry, only {Number of Copies} copies in stock!
