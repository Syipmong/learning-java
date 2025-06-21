/**
 * Abstract class for all book types
 * Extends Item and implements Borrowable interface
 */
public abstract class Book extends Item implements Borrowable {
    protected String isbn;
    protected String genre;
    protected int pages;
    protected String borrowedBy;
    protected String borrowDate;
    
    public Book(String id, String title, String author, String isbn) {
        super(id, title, author);
        this.isbn = isbn;
        this.borrowedBy = null;
        this.borrowDate = null;
    }
    
    // Getters
    public String getIsbn() { return isbn; }
    public String getGenre() { return genre; }
    public int getPages() { return pages; }
    public String getBorrowedBy() { return borrowedBy; }
    public String getBorrowDate() { return borrowDate; }
    
    // Setters
    public void setGenre(String genre) { this.genre = genre; }
    public void setPages(int pages) { this.pages = pages; }
    
    // Common borrowable implementation
    @Override
    public boolean canBorrow() {
        return isAvailable && borrowedBy == null;
    }
    
    @Override
    public void borrowItem(String memberId) {
        if (canBorrow()) {
            this.borrowedBy = memberId;
            this.borrowDate = java.time.LocalDate.now().toString();
            setAvailable(false);
        }
    }
    
    @Override
    public void returnItem() {
        this.borrowedBy = null;
        this.borrowDate = null;
        setAvailable(true);
    }
    
    @Override
    public String getItemType() {
        return "Book";
    }
    
    // Common display method for all books
    public void displayInfo() {
        System.out.println("=== Book Information ===");
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + isbn);
        System.out.println("Genre: " + genre);
        System.out.println("Pages: " + pages);
        System.out.println("Available: " + isAvailable);
        if (!isAvailable && borrowedBy != null) {
            System.out.println("Borrowed by: " + borrowedBy);
            System.out.println("Borrow date: " + borrowDate);
        }
        System.out.println("========================");
    }
}
