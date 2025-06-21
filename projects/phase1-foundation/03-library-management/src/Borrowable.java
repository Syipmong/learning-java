/**
 * Interface for items that can be borrowed from the library
 */
public interface Borrowable {
    boolean canBorrow();
    void borrowItem(String memberId);
    void returnItem();
    int getBorrowingPeriodDays();
}
