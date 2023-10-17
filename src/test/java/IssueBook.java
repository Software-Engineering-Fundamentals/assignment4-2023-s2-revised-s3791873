
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Implement and test {Programme.addStudent } that respects the considtion given
 * the assignment specification
 * OTE: You are expected to verify that the constraints to borrow a new book fro
 * a library
 *
 * 
 * Each test criteria must be in an independent test method .
 *
 * Initialize the test object with "setting" method.
 */
public class IssueBook {
    private LibraryCard testLibraryCard;
    private Book testBook;
    private Student testStudent;

    @BeforeEach
    private void before() {
        testStudent = new Student("Edward", 3791873);
        testBook = new Book(1234567, "Testing Book", 0); // Book used for quick testing.
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 10, 17);
        Date start = calendar.getTime();
        calendar.set(2023, 10, 17);
        Date end = calendar.getTime();
        testLibraryCard = new LibraryCard(testStudent, start, end, 12345);

    }

    /*
     * Executes a fresh testing unit each time.Since we are usingsame test objects.
     */

    @AfterEach
    private void after() {
        testStudent = null;
        testBook = null;
        testLibraryCard = null;
    }

    // testing if the card has less than 4 books borrowed, this should
    // return true
    @Test
    public void numberBooks_True_ifLessThan4BooksBorrowed() throws IllegalBookIssueException {
        Book testBook1 = new Book(1, "1", 0);
        testLibraryCard.issueBook(testBook1);
        boolean result = testLibraryCard.issueBook(testBook);
        assertTrue(result);
    }

    // testing if the card has exactly 4 books borrowed, this should
    // return true
    @Test
    public void numberBooks_True_if4BooksBorrowed() throws IllegalBookIssueException {
        Book testBook1 = new Book(1, "1", 0);
        testLibraryCard.issueBook(testBook1);
        Book testBook2 = new Book(2, "2", 0);
        testLibraryCard.issueBook(testBook2);
        Book testBook3 = new Book(3, "3", 0);
        testLibraryCard.issueBook(testBook3);
        Book testBook4 = new Book(4, "4", 0);
        testLibraryCard.issueBook(testBook4);

        boolean result = testLibraryCard.issueBook(testBook);
        assertTrue(result);
    }

    // testing if the card has more than 4 books borrowed, this should return false
    @Test
    public void returnFalse_ifMoreThan4BooksBorrowed() throws IllegalBookIssueException {
        Book testBook1 = new Book(1, "1", 0);
        testLibraryCard.issueBook(testBook1);
        Book testBook2 = new Book(2, "2", 0);
        testLibraryCard.issueBook(testBook2);
        Book testBook3 = new Book(3, "3", 0);
        testLibraryCard.issueBook(testBook3);
        Book testBook4 = new Book(4, "4", 0);
        testLibraryCard.issueBook(testBook4);
        Book testBook5 = new Book(5, "5", 0);
        testLibraryCard.issueBook(testBook5);
        boolean result = testLibraryCard.issueBook(testBook);
        assertFalse(result);
    }

    // testing adding the same book into the card twice, this should throw
    // IllegalBookIssueException if book is same twice.
    @Test
    public void DuplicateIssue_ThrowsIllegalBookIssueException_ifSameBookTwice() throws IllegalBookIssueException {
        Book testBook1 = new Book(1, "Duplicate Issue 1", 0);
        testLibraryCard.issueBook(testBook1); // we are issuing this book twice.
        assertThrows(IllegalBookIssueException.class, () -> {
            testLibraryCard.issueBook(testBook1);
        });
    }

    // testing issuing a book, card expiry date is after current date. Returns true
    @Test
    public void returnTrue_ifIssueExpiredCard() throws IllegalBookIssueException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, 10, 17);
        Date end = calendar.getTime();
        testLibraryCard.setExpiryDate(end);
        boolean result = testLibraryCard.issueBook(testBook);
        assertTrue(result);
    }

    /*
     * testing issuing a book, card expiry date is before current date (expired).
     * Returns False
     */
    @Test
    public void returnFalse_ifIssueExpiredCard() throws IllegalBookIssueException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 10, 17);
        Date end = calendar.getTime();
        testLibraryCard.setExpiryDate(end);
        boolean result = testLibraryCard.issueBook(testBook);
        assertFalse(result);
    }

    // testing attempting to issue an avaiable book, this should return true
    @Test
    public void returnTrue_ifBookUnavailable() throws IllegalBookIssueException {
        testBook.setStatus(true);
        boolean result = testLibraryCard.issueBook(testBook);
        assertTrue(result);
    }

    // testing attempting to issue an unavailable book, this should return false
    @Test
    public void returnFalse_ifBookUnavailable() throws IllegalBookIssueException {
        Book unavailBook = new Book(1, "The Unavailable Book", 0);
        unavailBook.setStatus(false);
        boolean result = testLibraryCard.issueBook(unavailBook);
        assertFalse(result);
    }

    /*
     * testing attempting to issue a book while there is a fine on the card, this
     * student cannot borrowing a book, therefore turns false
     */
    @Test
    public void returnFalse_ifIssueWithFine() throws IllegalBookIssueException {
        double fine = 69.10;
        testLibraryCard.setFine(fine);
        //
        boolean result = testLibraryCard.issueBook(testBook);
        assertFalse(result);
    }

    // testing the getDays count after issuing a book not in demand, should have the
    // value 15 Successful borrowing.
    @Test
    public void daysIsEqual15_ifIssueLowDemand() throws IllegalBookIssueException {
        testBook.setDemand(0);
        testLibraryCard.issueBook(testBook);
        //
        assertEquals(15, testBook.getDays());
    }

    // testing the getDays after issuing a book in demand, should have the value 3
    // Successful borrowing.
    @Test
    public void daysIs3_ifIssueHighDemand() throws IllegalBookIssueException {
        testBook.setDemand(1);
        testLibraryCard.issueBook(testBook);
        assertEquals(3, testBook.getDays());
    }
}
