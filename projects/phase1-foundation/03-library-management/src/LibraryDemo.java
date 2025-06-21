/**
 * Demo class to showcase the Library Management System
 * Demonstrates inheritance, polymorphism, and abstract classes
 */
public class LibraryDemo {
    public static void main(String[] args) {
        // Create library
        Library library = new Library("Central City Library");
        
        System.out.println("=== Library Management System Demo ===\n");
        
        // Add different types of books
        System.out.println("1. Adding Books to Library:");
        library.addItem(new Fiction("F001", "1984", "George Orwell", "978-0451524935"));
        library.addItem(new Fiction("F002", "To Kill a Mockingbird", "Harper Lee", "978-0061120084"));
        library.addItem(new NonFiction("NF001", "Sapiens", "Yuval Noah Harari", "978-0062316097"));
        library.addItem(new NonFiction("NF002", "Educated", "Tara Westover", "978-0399590504"));
        library.addItem(new Reference("R001", "Oxford English Dictionary", "Oxford Press", "978-0198611868"));
        
        System.out.println("\n2. Registering Members:");
        library.registerMember(new Member("M001", "Alice Johnson", "alice@email.com"));
        library.registerMember(new Member("M002", "Bob Smith", "bob@email.com"));
        library.registerMember(new Member("M003", "Carol Davis", "carol@email.com"));
        
        System.out.println("\n3. Displaying Library Statistics:");
        library.displayStatistics();
        
        System.out.println("\n4. Displaying All Items:");
        library.displayAllItems();
        
        System.out.println("\n5. Borrowing Items:");
        library.borrowItem("F001", "M001"); // Alice borrows 1984
        library.borrowItem("NF001", "M002"); // Bob borrows Sapiens
        library.borrowItem("R001", "M003"); // Carol tries to borrow reference book
        
        System.out.println("\n6. Displaying Available Items:");
        library.displayAvailableItems();
        
        System.out.println("\n7. Searching for Books:");
        System.out.println("Search results for 'Sapiens':");
        var searchResults = library.searchByTitle("Sapiens");
        for (var item : searchResults) {
            item.displayInfo();
        }
        
        System.out.println("\n8. Displaying Member Information:");
        // Get member and display their info
        System.out.println("Alice's borrowed items:");
        var alice = library.members.get("M001");
        if (alice != null) {
            alice.displayBorrowedItems();
        }
        
        System.out.println("\n9. Returning Items:");
        library.returnItem("F001", "M001"); // Alice returns 1984
        
        System.out.println("\n10. Final Library Statistics:");
        library.displayStatistics();
        
        System.out.println("\n11. Demonstrating Polymorphism:");
        demonstratePolymorphism();
        
        System.out.println("\n=== Demo Complete ===");
    }
    
    /**
     * Demonstrates polymorphism with different book types
     */
    private static void demonstratePolymorphism() {
        System.out.println("Creating different book types and calling methods:");
        
        // Create array of books (polymorphism)
        Book[] books = {
            new Fiction("F999", "Test Fiction", "Test Author", "123456789"),
            new NonFiction("NF999", "Test NonFiction", "Test Author", "987654321"),
            new Reference("R999", "Test Reference", "Test Author", "111222333")
        };
        
        // Call methods on each book (polymorphic behavior)
        for (Book book : books) {
            System.out.println("\n--- " + book.getClass().getSimpleName() + " ---");
            System.out.println("Borrowing period: " + book.getBorrowingPeriodDays() + " days");
            System.out.println("Can borrow: " + book.canBorrow());
            System.out.println("Fine for 5 days overdue: $" + book.calculateFine(5));
        }
    }
}
