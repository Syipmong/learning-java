/**
 * NonFiction book implementation
 * Extended borrowing period for educational materials
 */
public class NonFiction extends Book {
    private static final int BORROWING_PERIOD_DAYS = 21;
    private static final double FINE_PER_DAY = 0.75;
    
    public NonFiction(String id, String title, String author, String isbn) {
        super(id, title, author, isbn);
        setGenre("Non-Fiction");
    }
    
    @Override
    public int getBorrowingPeriodDays() {
        return BORROWING_PERIOD_DAYS;
    }
    
    @Override
    public double calculateFine(int daysOverdue) {
        return daysOverdue > 0 ? daysOverdue * FINE_PER_DAY : 0.0;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Book Type: Non-Fiction");
        System.out.println("Borrowing Period: " + BORROWING_PERIOD_DAYS + " days");
        System.out.println("Fine per day: $" + FINE_PER_DAY);
    }
}
