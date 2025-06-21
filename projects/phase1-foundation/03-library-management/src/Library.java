import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main Library class that manages all library operations
 * Demonstrates collections, polymorphism, and object management
 */
public class Library {
    private List<Item> items;
    private Map<String, Member> members;
    private String libraryName;
    
    public Library(String libraryName) {
        this.libraryName = libraryName;
        this.items = new ArrayList<>();
        this.members = new HashMap<>();
    }
    
    public Library() {
        this("City Library");
    }
    
    // Item management
    public void addItem(Item item) {
        if (findItemById(item.getId()) == null) {
            items.add(item);
            System.out.println("Added item: " + item.getTitle());
        } else {
            System.out.println("Item with ID " + item.getId() + " already exists.");
        }
    }
    
    public void removeItem(String itemId) {
        Item item = findItemById(itemId);
        if (item != null) {
            if (item.isAvailable()) {
                items.remove(item);
                System.out.println("Removed item: " + item.getTitle());
            } else {
                System.out.println("Cannot remove item - currently borrowed.");
            }
        } else {
            System.out.println("Item not found: " + itemId);
        }
    }
    
    // Member management
    public void registerMember(Member member) {
        if (!members.containsKey(member.getMemberId())) {
            members.put(member.getMemberId(), member);
            System.out.println("Registered member: " + member.getName());
        } else {
            System.out.println("Member with ID " + member.getMemberId() + " already exists.");
        }
    }
    
    public void removeMember(String memberId) {
        Member member = members.get(memberId);
        if (member != null) {
            if (member.getBorrowedItems().isEmpty()) {
                members.remove(memberId);
                System.out.println("Removed member: " + member.getName());
            } else {
                System.out.println("Cannot remove member - has borrowed items.");
            }
        } else {
            System.out.println("Member not found: " + memberId);
        }
    }
    
    // Borrowing operations
    public boolean borrowItem(String itemId, String memberId) {
        Item item = findItemById(itemId);
        Member member = members.get(memberId);
        
        if (item == null) {
            System.out.println("Item not found: " + itemId);
            return false;
        }
        
        if (member == null) {
            System.out.println("Member not found: " + memberId);
            return false;
        }
        
        if (!member.canBorrow()) {
            System.out.println("Member cannot borrow: " + member.getName());
            return false;
        }
        
        if (item instanceof Borrowable) {
            Borrowable borrowable = (Borrowable) item;
            if (borrowable.canBorrow()) {
                borrowable.borrowItem(memberId);
                member.borrowItem(itemId);
                System.out.println("Item borrowed successfully: " + item.getTitle());
                return true;
            } else {
                System.out.println("Item cannot be borrowed: " + item.getTitle());
                return false;
            }
        } else {
            System.out.println("Item is not borrowable: " + item.getTitle());
            return false;
        }
    }
    
    public boolean returnItem(String itemId, String memberId) {
        Item item = findItemById(itemId);
        Member member = members.get(memberId);
        
        if (item == null || member == null) {
            System.out.println("Item or member not found.");
            return false;
        }
        
        if (item instanceof Borrowable) {
            Borrowable borrowable = (Borrowable) item;
            borrowable.returnItem();
            member.returnItem(itemId);
            System.out.println("Item returned successfully: " + item.getTitle());
            return true;
        }
        
        return false;
    }
    
    // Search operations
    public List<Item> searchByTitle(String title) {
        List<Item> results = new ArrayList<>();
        for (Item item : items) {
            if (item.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(item);
            }
        }
        return results;
    }
    
    public List<Item> searchByAuthor(String author) {
        List<Item> results = new ArrayList<>();
        for (Item item : items) {
            if (item.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(item);
            }
        }
        return results;
    }
    
    public Item findItemById(String itemId) {
        for (Item item : items) {
            if (item.getId().equals(itemId)) {
                return item;
            }
        }
        return null;
    }
    
    // Display operations
    public void displayAllItems() {
        System.out.println("=== " + libraryName + " - All Items ===");
        if (items.isEmpty()) {
            System.out.println("No items in the library.");
        } else {
            for (Item item : items) {
                System.out.println(item.getId() + " - " + item.getTitle() + " by " + item.getAuthor() + 
                                 " (" + (item.isAvailable() ? "Available" : "Borrowed") + ")");
            }
        }
        System.out.println("=====================================");
    }
    
    public void displayAvailableItems() {
        System.out.println("=== Available Items ===");
        boolean hasAvailable = false;
        for (Item item : items) {
            if (item.isAvailable()) {
                System.out.println(item.getId() + " - " + item.getTitle() + " by " + item.getAuthor());
                hasAvailable = true;
            }
        }
        if (!hasAvailable) {
            System.out.println("No items available.");
        }
        System.out.println("=======================");
    }
    
    public void displayAllMembers() {
        System.out.println("=== All Members ===");
        if (members.isEmpty()) {
            System.out.println("No registered members.");
        } else {
            for (Member member : members.values()) {
                System.out.println(member.getMemberId() + " - " + member.getName() + 
                                 " (Borrowed: " + member.getBorrowedItems().size() + ")");
            }
        }
        System.out.println("===================");
    }
    
    // Statistics
    public void displayStatistics() {
        int totalItems = items.size();
        int availableItems = 0;
        int borrowedItems = 0;
        
        for (Item item : items) {
            if (item.isAvailable()) {
                availableItems++;
            } else {
                borrowedItems++;
            }
        }
        
        System.out.println("=== Library Statistics ===");
        System.out.println("Library: " + libraryName);
        System.out.println("Total Items: " + totalItems);
        System.out.println("Available Items: " + availableItems);
        System.out.println("Borrowed Items: " + borrowedItems);
        System.out.println("Registered Members: " + members.size());
        System.out.println("===========================");
    }
}
