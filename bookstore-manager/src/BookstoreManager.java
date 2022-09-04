import java.sql.*;
import java.util.Scanner;

/**
 * The type Bookstore manager.
 */
public class BookstoreManager {
    /**
     * The main method of the program.
     *
     * @param args the args
     */
    public static void main(String[] args) {
        //Connects to the ebookstore database
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ebookstore?useSSL=false",
                    "tom",
                    "Suss3x225!"
            );
            // Creates a line to the database for running queries
            Statement statement = connection.createStatement();
            Scanner input = new Scanner(System.in);
            while(true){
                // Prints the menu options
                printMenu();
                // Asks the user to select an option from the menu
                int menuSelection = input.nextInt();
                input.nextLine();
                // If they select 1, allow them to add a new book
                if(menuSelection == 1){
                    enterBook(statement);
                }
                // If they select 2, allow them to update a book
                else if(menuSelection == 2){
                    updateBook(statement);
                }
                // If the select 3, allow them to delete a book
                else if(menuSelection == 3){
                    deleteBook(statement);
                }
                // If they select 4, allow them to search for books
                else if(menuSelection == 4){
                    searchBooks(statement);
                }
                // If they select 5, exit the program
                else if(menuSelection == 5){
                    System.out.println("Thank you for using our services! See you soon!");
                    System.exit(0);
                    break;
                }
                // Otherwise give an error message and let them try again
                else{
                    System.out.println("Please enter a valid integer between 1 and 5 and try " +
                            "again.");
                }
            }
        }
        // If an error occurs, prints the stack
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the menu of options for the user.
     */
    public static void printMenu(){
        System.out.println("""
    What would you like to do?
    1 - Enter a New Book
    2 - Update a Book Entry
    3 - Delete a Book
    4 - Search Books
    5 - Exit""");
    }

    /**
     * Adds a row to the books table based on user input.
     *
     * @param statement the statement
     * @throws SQLException the sql exception
     */
    public static void enterBook(Statement statement) throws SQLException {
        // Asks the user for the details of the book they want to enter
        Scanner input = new Scanner(System.in);
        String id;
        while (true) {
            System.out.println("What is the ID of the book?");
            id = input.nextLine();
            ResultSet results = checkID(statement, id);
            // If the ID already exists, asks user to try again
            if (results.isBeforeFirst()) {
                System.out.println("This ID already exists, please try again.");
            }
            else {
                break;
            }
        }
        System.out.println("What is the title of the book?");
        String title = input.nextLine();
        System.out.println("Who is the author of the book?");
        String author = input.nextLine();
        System.out.println("How many copies are in stock?");
        int qty = input.nextInt();
        input.nextLine();
        // Adds the book to the table
        String query = "INSERT INTO books VALUES('%s','%s','%s',%s)".
                formatted(id, title, author, qty);
        statement.executeUpdate(query);
        System.out.println("Book has been added.");
    }

    /**
     * Updates a chosen field of the book with the id given.
     *
     * @param statement the statement
     * @throws SQLException the sql exception
     */
    public static void updateBook(Statement statement) throws SQLException {
        Scanner input = new Scanner(System.in);
        String idSelection;
        // Prints a summary of all the books in the table (id - title)
        printSummary(statement);
        // Asks the user to select the book they would like to update based on its id
        while (true) {
            System.out.println("What is the ID of the book you would like to update?");
            idSelection = input.nextLine();
            ResultSet results = checkID(statement, idSelection);
            // If there are no rows returned, allows the user to try again
            if (!results.isBeforeFirst()) {
                System.out.println("The ID you entered was not found, please try again.");
            } else {
                break;
            }
        }
        // Asks the user which field they would like to update
        while (true) {
            System.out.println("""
                    Which field would you like to update?
                    1 - ID
                    2 - Name
                    3 - Author
                    4 - Quantity""");
            int fieldSelection = input.nextInt();
            input.nextLine();
            boolean validSelection = false;
            // If user selects 1, runs an SQL query updating the id
            if (fieldSelection == 1) {
                validSelection = true;
                changeID(statement, input, idSelection);
            }
            // If user selects 2, runs an SQL query updating the name
            else if (fieldSelection == 2) {
                validSelection = true;
                changeTitle(statement, input, idSelection);
            }
            // If user selects 3, runs an SQL query updating the author
            else if (fieldSelection == 3) {
                validSelection = true;
                changeAuthor(statement, input, idSelection);
            }
            // If user selects 4, runs an SQL query updating the quantity
            else if (fieldSelection == 4) {
                validSelection = true;
                changeQty(statement, input, idSelection);
            }
            // If the user does not enter a valid input allows them to try again
            if (!validSelection) {
                System.out.println("Input not recognised, please enter a number from 1 to 4.");
            }
            // Otherwise prints a confirmation and breaks
            else {
                System.out.println("Book has been updated successfully.");
                break;
            }
        }
    }

    /**
     * Changes the quantity of a book with the id provided.
     * @param statement statement
     * @param input Scanner input
     * @param idSelection book id
     * @throws SQLException if an error occurs with the SQL query
     */
    private static void changeQty(Statement statement, Scanner input, String idSelection) throws SQLException {
        System.out.println("What would you like to change the quantity to?");
        String updateQty = input.nextLine();
        statement.executeUpdate(
                "UPDATE books SET qty='%s' WHERE id='%s';"
                        .formatted(updateQty, idSelection));
    }

    /**
     * Change the author of a book with the id provided.
     * @param statement statement
     * @param input Scanner input
     * @param idSelection book id
     * @throws SQLException if an error occurs with the SQL query
     */
    private static void changeAuthor(Statement statement, Scanner input, String idSelection) throws SQLException {
        System.out.println("What would you like to change the author of the book to?");
        String updateAuthor = input.nextLine();
        statement.executeUpdate(
                "UPDATE books SET author='%s' WHERE id='%s';"
                        .formatted(updateAuthor, idSelection));
    }

    /**
     * Changes the title of the book with the id provided.
     * @param statement statement
     * @param input Scanner input
     * @param idSelection book id
     * @throws SQLException if an error occurs with the SQL query
     */
    private static void changeTitle(Statement statement, Scanner input, String idSelection) throws SQLException {
        System.out.println("What would you like to change the title of the book to?");
        String updateName = input.nextLine();
        statement.executeUpdate(
                "UPDATE books SET title='%s' WHERE id='%s';"
                        .formatted(updateName, idSelection));
    }

    /**
     * Changes the book id of the book id provided.
     * @param statement statement
     * @param input Scanner input
     * @param idSelection book id
     * @throws SQLException if an error occurs with the SQL query
     */
    private static void changeID(Statement statement, Scanner input, String idSelection) throws SQLException {
        while (true) {
            try {
                System.out.println("What would you like to change the ID to?");
                String updateID = input.nextLine();
                statement.executeUpdate(
                        "UPDATE books SET id='%s' WHERE id='%s';"
                                .formatted(updateID, idSelection));
                break;
            }
            // If there is already an entry with that ID, throws an error and allows the
            // user to try again
            catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("The ID you selected already exists. Please try again.");
            }
        }
    }

    /**
     * Checks that the id provided exists in the table.
     * @param statement statement
     * @param idSelection book id
     * @return ResultSet
     * @throws SQLException if an error occurs with the SQL query
     */
    private static ResultSet checkID(Statement statement, String idSelection) throws SQLException {
        return statement.executeQuery("SELECT * FROM books WHERE id = '%s';".
                formatted(idSelection));
    }

    /**
     * Delete a book with the book id provided.
     *
     * @param statement the statement
     * @throws SQLException the sql exception
     */
    public static void deleteBook(Statement statement) throws SQLException {
        Scanner input = new Scanner(System.in);
        // Prints a summary of all the books in the table (id - title)
        printSummary(statement);
        // Asks the user for the ID of the book they want to delete
        String idSelection;
        while (true) {
            System.out.println("Please enter the ID of the book you would like to delete:");
            idSelection = input.nextLine();
            ResultSet results = checkID(statement, idSelection);
            // If there are no rows found with the id, gives an error and allows the user to try
            // again
            if (!results.isBeforeFirst()) {
                System.out.println("The ID you entered was not found, please try again.");
            } else {
                break;
            }
        }
        // Runs an SQL query to delete the book and prints a confirmation
        statement.executeUpdate("DELETE FROM books WHERE id='%s';".formatted(idSelection));
        System.out.println("Book has been deleted.");
    }

    /**
     * Search books based on a search term.
     *
     * @param statement the statement
     * @throws SQLException the sql exception
     */
    public static void searchBooks(Statement statement) throws SQLException {
        Scanner input = new Scanner(System.in);
        // Asks the user what they would like to search for
        while (true) {
            System.out.println("Please enter the search term you would like to enter");
            String searchTerm = input.nextLine();
            // Executes an SQL query with the search term
            ResultSet results = statement.executeQuery(
                    "SELECT * FROM books " +
                            "WHERE id LIKE \"%" + searchTerm + "%\"" +
                            "OR title LIKE \"%" + searchTerm + "%\"" +
                            "OR author LIKE \"%"+ searchTerm + "%\"");
            // If no results are found, prints a message and asks if they would like to try again
            if (!results.isBeforeFirst()) {
                noResults(statement, input);
                return;
            }
            // If results are found, prints them in an easy-to-read format
            else {
                printResults(results);
                break;
            }
        }
    }

    /**
     * Prints a summary of all the books in the table with the format id - title.
     * @param statement statement
     * @throws SQLException if an error occurs with the SQL query
     */
    private static void printSummary(Statement statement) throws SQLException{
        ResultSet results = statement.executeQuery("SELECT * FROM books;");
        while(results.next()){
            System.out.println(results.getString("id")+ " - "
                    + results.getString("title"));
        }
    }

    /**
     * Prints the results of a query in an easy-to-read format.
     * @param results ResultSet results
     * @throws SQLException if an error occurs with the SQL query
     */
    private static void printResults(ResultSet results) throws SQLException {
        while (results.next()) {
            System.out.println(
                    "ID: " + results.getString("id") + "\n"
                    + "Title: " + results.getString("title") + "\n"
                    + "Author: " + results.getString("author") + "\n"
                    + "Quantity: " + results.getInt("qty") + "\n");
        }
    }

    /**
     * Displays a menu if no search results were found and allows the user to try again if they want
     * to.
     * @param statement statement
     * @param input Scanner input
     * @throws SQLException if an error occurs with the SQL query
     */
    private static void noResults(Statement statement, Scanner input) throws SQLException {
        while (true) {
            System.out.println("No results were found, would you like to try again? (y/n)");
            String tryAgain = input.nextLine();
            // If they select y, restarts the method
            if (tryAgain.equals("y")) {
                searchBooks(statement);
            }
            // If they select n, exits the method
            else if (tryAgain.equals("n")){
                return;
            }
            // Otherwise, prints an error message and allows them to try again
            else {
                System.out.println("Input not recognised, please enter either y or n and try " +
                        "again");
            }
        }
    }
}



