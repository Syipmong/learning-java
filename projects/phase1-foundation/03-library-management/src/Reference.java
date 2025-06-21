/**
 * Reference book implementation
 * Library use only - cannot be borrowed
 */
public class Reference extends Book {
    private static final int BORROWING_PERIOD_DAYS = 0; // Cannot be borrowed
    
    public Reference(String id, String title, String author, String isbn) {
        super(id, title, author, isbn);
        setGenre("Reference");
    }
    
    @Override
    public boolean canBorrow() {
        return false; // Reference books cannot be borrowed
    }
    
    @Override
    public void borrowItem(String memberId) {
        System.out.println("Reference books cannot be borrowed. Library use only.");
    }
    
    @Override
    public int getBorrowingPeriodDays() {
        return BORROWING_PERIOD_DAYS;
    }
    
    @Override
    public double calculateFine(int daysOverdue) {
        return 0.0; // No fines for reference books as they cannot be borrowed
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Book Type: Reference");
        System.out.println("Borrowing: Library use only");
    }
}
