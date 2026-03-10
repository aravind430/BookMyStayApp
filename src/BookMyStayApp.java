import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * UseCase4RoomSearch demonstrates a read-only search service that
 * separates data retrieval from state mutation.
 * * @author YourName
 * @version 4.0
 */

// --- Reusing Room Hierarchy from UC2 ---
abstract class Room {
    private String type;
    private double price;
    public Room(String type, double price) { this.type = type; this.price = price; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public abstract String getFeatures();
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single", 100.0); }
    public String getFeatures() { return "1 Bed, WiFi, Desk"; }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double", 180.0); }
    public String getFeatures() { return "2 Beds, Mini-fridge"; }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite", 350.0); }
    public String getFeatures() { return "King Bed, Balcony, Room Service"; }
}

// --- Inventory Manager (State Holder) ---
class InventoryManager {
    private Map<String, Integer> stock = new HashMap<>();

    public void addStock(String type, int count) { stock.put(type, count); }
    public int getStock(String type) { return stock.getOrDefault(type, 0); }
    public Map<String, Integer> getAllStock() { return new HashMap<>(stock); }
}

// --- Search Service (The New Layer) ---
class SearchService {
    private InventoryManager inventory;
    private List<Room> roomPrototypes;

    public SearchService(InventoryManager inventory) {
        this.inventory = inventory;
        this.roomPrototypes = new ArrayList<>();
        // Pre-defining room characteristics
        roomPrototypes.add(new SingleRoom());
        roomPrototypes.add(new DoubleRoom());
        roomPrototypes.add(new SuiteRoom());
    }

    /**
     * Performs a read-only search for available rooms.
     * Filters out rooms with 0 availability.
     */
    public void performSearch() {
        System.out.println("\n--- Available Rooms Search Results ---");
        boolean found = false;

        for (Room room : roomPrototypes) {
            int count = inventory.getStock(room.getType());

            // Validation Logic: Only show rooms with stock > 0
            if (count > 0) {
                System.out.println("Type: " + room.getType() + " | Price: $" + room.getPrice());
                System.out.println("Details: " + room.getFeatures());
                System.out.println("Current Availability: " + count + " left.");
                System.out.println("--------------------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Sorry, no rooms are currently available.");
        }
    }
}

// --- Main Application Class ---
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("*************************************************");
        System.out.println("   Book My Stay - Room Search Service (v4.0)   ");
        System.out.println("*************************************************");

        // 1. Setup Inventory
        InventoryManager hotelInv = new InventoryManager();
        hotelInv.addStock("Single", 5);
        hotelInv.addStock("Double", 0); // This should be filtered out
        hotelInv.addStock("Suite", 2);

        // 2. Initialize Search Service
        SearchService searchService = new SearchService(hotelInv);

        // 3. Perform Search (Read-only operation)
        searchService.performSearch();

        System.out.println("Search completed. Inventory state remains unchanged.");
        System.out.println("*************************************************");
    }
}