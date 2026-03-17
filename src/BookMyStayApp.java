import java.io.*;
import java.util.*;

// The Reservation class must implement Serializable to be saved to a file
class PersistentReservation implements Serializable {
    private static final long serialVersionUID = 1L;
    String id;
    String roomType;

    public PersistentReservation(String id, String roomType) {
        this.id = id;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "ID: " + id + " [" + roomType + "]";
    }
}

public class BookMyStayApp {

    private static final String STORAGE_FILE = "hotel_state.ser";

    // System state to persist
    private Map<String, Integer> inventory = new HashMap<>();
    private List<PersistentReservation> history = new ArrayList<>();

    public UseCase12DataPersistenceRecovery() {
        // Default initial state if no file exists
        inventory.put("Deluxe", 10);
        inventory.put("Suite", 5);
    }

    /**
     * Serialization: Saves system state to a file.
     */
    public void saveSystemState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
            oos.writeObject(inventory);
            oos.writeObject(history);
            System.out.println("LOG: System state successfully persisted to " + STORAGE_FILE);
        } catch (IOException e) {
            System.err.println("ERROR: Failed to save state: " + e.getMessage());
        }
    }

    /**
     * Deserialization: Restores system state from a file.
     */
    @SuppressWarnings("unchecked")
    public void loadSystemState() {
        File file = new File(STORAGE_FILE);
        if (!file.exists()) {
            System.out.println("LOG: No persistence file found. Starting with fresh state.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STORAGE_FILE))) {
            inventory = (Map<String, Integer>) ois.readObject();
            history = (List<PersistentReservation>) ois.readObject();
            System.out.println("LOG: System state recovered successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ERROR: Recovery failed. File might be corrupted: " + e.getMessage());
        }
    }

    public void addBooking(String id, String type) {
        if (inventory.getOrDefault(type, 0) > 0) {
            inventory.put(type, inventory.get(type) - 1);
            history.add(new PersistentReservation(id, type));
            System.out.println("SUCCESS: Added booking " + id);
        }
    }

    public void showStatus() {
        System.out.println("--- Current System State ---");
        System.out.println("Inventory: " + inventory);
        System.out.println("Total Bookings in History: " + history.size());
        System.out.println("----------------------------");
    }

    public static void main(String[] args) {
        UseCase12DataPersistenceRecovery app = new UseCase12DataPersistenceRecovery();

        // 1. Attempt to recover previous state
        app.loadSystemState();
        app.showStatus();

        // 2. Perform some operations
        if (app.history.isEmpty()) {
            System.out.println("Simulating first-time run...");
            app.addBooking("RES-001", "Deluxe");
            app.addBooking("RES-002", "Suite");
        } else {
            System.out.println("Simulating post-restart run...");
            app.addBooking("RES-00" + (app.history.size() + 1), "Deluxe");
        }

        // 3. Persist before shutdown
        app.saveSystemState();
        app.showStatus();

        System.out.println("\nApplication shutting down. Run again to see recovered state!");
    }
}