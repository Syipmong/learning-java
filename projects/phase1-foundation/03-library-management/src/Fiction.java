/**
 * Fiction book implementation
 * Standard borrowing period and fine calculation
 */
public class Fiction extends Book {
    private static final int BORROWING_PERIOD_DAYS = 14;
    private static final double FINE_PER_DAY = 0.50;
    
    public Fiction(String id, String title, String author, String isbn) {
        super(id, title, author, isbn);
        setGenre("Fiction");
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
        System.out.println("Book Type: Fiction");
        System.out.println("Borrowing Period: " + BORROWING_PERIOD_DAYS + " days");
        System.out.println("Fine per day: $" + FINE_PER_DAY);
    }
}
