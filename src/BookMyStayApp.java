import java.util.HashMap;
import java.util.Map;

/**
 * UseCase3InventorySetup demonstrates centralized inventory management
 * using a HashMap to ensure a single source of truth for room availability.
 * * @author YourName
 * @version 3.0
 */

// --- Room Inventory Manager ---
class RoomInventory {
    // Encapsulated HashMap: Key = Room Type, Value = Available Count
    private Map<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    /**
     * Registers or updates a room type in the inventory.
     */
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    /**
     * Retrieves the current count for a specific room type.
     * Returns 0 if the room type is not found.
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Displays the full state of the inventory.
     */
    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Available: " + entry.getValue());
        }
    }
}

// --- Main Application Class ---
public class BookMystayApp {

    public static void main(String[] args) {
        System.out.println("*************************************************");
        System.out.println("   Book My Stay - Inventory Management (v3.0)  ");
        System.out.println("*************************************************");

        // 1. Initialize the Centralized Inventory
        RoomInventory hotelInventory = new RoomInventory();

        // 2. Register Room Types (Populating the Map)
        hotelInventory.updateAvailability("Single Room", 10);
        hotelInventory.updateAvailability("Double Room", 7);
        hotelInventory.updateAvailability("Suite Room", 3);

        // 3. Display Initial State
        hotelInventory.displayInventory();

        // 4. Demonstrate a controlled update (e.g., after a booking)
        System.out.println("\n[System Log]: Booking confirmed for 1 Suite Room...");
        int currentSuites = hotelInventory.getAvailability("Suite Room");
        hotelInventory.updateAvailability("Suite Room", currentSuites - 1);

        // 5. Display Updated State
        hotelInventory.displayInventory();

        System.out.println("\n*************************************************");
        System.out.println("Inventory operations completed successfully.");
    }
}