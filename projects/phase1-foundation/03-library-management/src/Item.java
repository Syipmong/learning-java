/**
 * Abstract base class for all library items
 * Demonstrates abstract class concepts and common functionality
 */
public abstract class Item {
    protected String id;
    protected String title;
    protected String author;
    protected boolean isAvailable;
    
    public Item(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }
    
    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }
    
    // Setters
    public void setAvailable(boolean available) { this.isAvailable = available; }
    
    // Abstract methods to be implemented by subclasses
    public abstract void displayInfo();
    public abstract double calculateFine(int daysOverdue);
    public abstract String getItemType();
}
