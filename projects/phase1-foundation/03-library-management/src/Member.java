import java.util.ArrayList;
import java.util.List;

/**
 * Library member class
 * Manages member information and borrowing history
 */
public class Member {
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private List<String> borrowedItems;
    private List<String> borrowingHistory;
    private double totalFines;
    
    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.borrowedItems = new ArrayList<>();
        this.borrowingHistory = new ArrayList<>();
        this.totalFines = 0.0;
    }
    
    // Getters
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public List<String> getBorrowedItems() { return new ArrayList<>(borrowedItems); }
    public List<String> getBorrowingHistory() { return new ArrayList<>(borrowingHistory); }
    public double getTotalFines() { return totalFines; }
    
    // Setters
    public void setPhone(String phone) { this.phone = phone; }
    
    // Member operations
    public void borrowItem(String itemId) {
        if (!borrowedItems.contains(itemId)) {
            borrowedItems.add(itemId);
            borrowingHistory.add(itemId + " - Borrowed on " + java.time.LocalDate.now());
        }
    }
    
    public void returnItem(String itemId) {
        borrowedItems.remove(itemId);
        borrowingHistory.add(itemId + " - Returned on " + java.time.LocalDate.now());
    }
    
    public void addFine(double amount) {
        this.totalFines += amount;
    }
    
    public void payFine(double amount) {
        this.totalFines = Math.max(0, this.totalFines - amount);
    }
    
    public boolean canBorrow() {
        return borrowedItems.size() < 5 && totalFines < 10.0; // Max 5 items, fines under $10
    }
    
    public void displayInfo() {
        System.out.println("=== Member Information ===");
        System.out.println("ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + (phone != null ? phone : "Not provided"));
        System.out.println("Currently Borrowed: " + borrowedItems.size() + " items");
        System.out.println("Total Fines: $" + String.format("%.2f", totalFines));
        System.out.println("Can Borrow: " + (canBorrow() ? "Yes" : "No"));
        System.out.println("==========================");
    }
    
    public void displayBorrowedItems() {
        System.out.println("=== Currently Borrowed Items ===");
        if (borrowedItems.isEmpty()) {
            System.out.println("No items currently borrowed.");
        } else {
            for (String itemId : borrowedItems) {
                System.out.println("- " + itemId);
            }
        }
        System.out.println("================================");
    }
    
    public void displayBorrowingHistory() {
        System.out.println("=== Borrowing History ===");
        if (borrowingHistory.isEmpty()) {
            System.out.println("No borrowing history.");
        } else {
            for (String record : borrowingHistory) {
                System.out.println("- " + record);
            }
        }
        System.out.println("=========================");
    }
}
