
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Library Card associated with the Student
 */
public class LibraryCard {
    /**
     * Card id
     */
    private int ID;

    /**
     * Issue Date of the Card
     */
    private Date IssueDate;

    /**
     * Expiry Date of the Card
     */
    private Date ExpiryDate;

    /**
     * Number of books borrowed
     */
    private List<Book> borrowed = new ArrayList<Book>();

    /**
     * Fine asscoaited with the card
     */
    private double fine;

    /**
     * Details about the cardholder
     */
    private Student student;

    public LibraryCard(Student student, Date IssueDate, Date ExpiryDate, int ID) {
        this.student = student;
        this.IssueDate = IssueDate;
        this.ExpiryDate = ExpiryDate;
        this.ID = ID;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public Date getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(Date IssueDate) {
        this.IssueDate = IssueDate;
    }

    public Date getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(Date ExpiryDate) {
        this.ExpiryDate = ExpiryDate;
    }

    public List<Book> getBooks() {
        return borrowed;
    }

    /**
     * Issue a new book
     * 
     * @param Book: book to borrow
     * @return true if the book is successfully borrowed, false otherwise
     */

    public boolean issueBook(Book book) throws IllegalBookIssueException {

        // Get the number of books borrowed by the student on the library card
        int numBorrowed = borrowed.size();

        // The number of books borrowed should not be greater than 4
        if (numBorrowed > 4) {
            System.out.println("You have reached the borrowing limiit.");
            return false;
        }

        // If student already has the book, they cannot borrow a duplicate one
        if (borrowed.contains(book)) {
            throw new IllegalBookIssueException("This book is already issued on the library card.");
        }

        // Check that the book is available for borrowing
        Date currentDate = new Date(); // Create current date
        if (ExpiryDate.before(currentDate)) {
            System.out.println("Library card has expired and is invalid.");
            return false;
        }

        // Check that the book is available for borrowing
        if (!book.getStatus()) {
            System.out.println("The book titled: " + book.getTitle() + " is currently unavailable for borrowing.");
            return false;
        }
        // The book should not be issued if there is pending fine associated with the
        // library card
        if (fine > 0.0) {
            System.out.println("Cannot issue book. Student has outstanding fine of: $" + getFine());
            return false;
        }
        /*
         * If the above constraints are met then issue the book to the student (the
         * method should return true) and make necessary update to relevant variables to
         * reflect this change.
         * If the above constraints are not met, then the book is not issued and the
         * method should return false.
         * 
         * If book to be borrowed is a high demand book then issue the book for 3 days.
         * For a low demand book, it can be issued for 15 days.
         */
        if (book.getDemand() == 0) {
            book.setDays(15);
            System.out.println("You will be able to borrow the book titled: " + book.getTitle() + " for 15 days");
        } else if (book.getDemand() == 1) {
            book.setDays(3);
            System.out.println("You will be able to borrow the book titled: " + book.getTitle() + " for 3 days");

        }

        /*
         * update status to show it is unavailable. If there is only one copy of the
         * book.
         */
        book.setStatus(true);
        borrowed.add(book); // add the book onto this card's borrowed list to keep track of number of books.
        return true;

    }

}
