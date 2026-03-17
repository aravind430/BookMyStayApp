import java.util.*;

// Class representing a complete Reservation record for history
class Reservation {
    String reservationId;
    String roomType;
    String roomId;
    List<String> services;
    double totalCost;

    public Reservation(String reservationId, String roomType, String roomId, List<String> services, double totalCost) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.services = services;
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return String.format("ID: %-15s | Type: %-8s | Room: %-10s | Cost: $%-6.2f | Services: %s",
                reservationId, roomType, roomId, totalCost, services);
    }
}

public class BookMyStayApp import java.util.*;

// Custom Exception for Domain-Specific Errors
class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

public class UseCase9ErrorHandlingValidation {

    private Map<String, Integer> inventory = new HashMap<>();
    private Set<String> validRoomTypes = new HashSet<>(Arrays.asList("Deluxe", "Suite", "Penthouse"));

    public UseCase9ErrorHandlingValidation() {
        // Initialize Inventory
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 0); // Out of stock
    }

    /**
     * Validates and processes a booking request.
     * Demonstrates Fail-Fast Design and Guarding System State.
     */
    public void processBooking(String roomType) throws BookingException {
        System.out.println("Validating request for: " + roomType);

        // 1. Validate Input (Case Sensitive as per requirements)
        if (!validRoomTypes.contains(roomType)) {
            throw new BookingException("INVALID_ROOM_TYPE: Room type '" + roomType + "' does not exist.");
        }

        // 2. Validate System State (Inventory Check)
        int availableCount = inventory.getOrDefault(roomType, 0);
        if (availableCount <= 0) {
            throw new BookingException("INSUFFICIENT_INVENTORY: No rooms available for type '" + roomType + "'.");
        }

        // 3. Commit State Change (Only reached if validations pass)
        inventory.put(roomType, availableCount - 1);
        System.out.println("SUCCESS: Room allocated for " + roomType + ". Remaining: " + (availableCount - 1));
    }

    public static void main(String[] args) {
        UseCase9ErrorHandlingValidation system = new UseCase9ErrorHandlingValidation();

        // List of test cases including invalid inputs
        String[] testRequests = {"Deluxe", "Economy", "Suite", "deluxe"};

        for (String request : testRequests) {
            System.out.println("\n--- Processing Request ---");
            try {
                system.processBooking(request);
            } catch (BookingException e) {
                // Graceful Failure Handling
                System.err.println("ERROR: " + e.getMessage());
            } finally {
                System.out.println("System remains stable. Ready for next request.");
            }
        }
    }
}