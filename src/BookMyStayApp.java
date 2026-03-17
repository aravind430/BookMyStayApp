import java.util.*;

public class BookMyStayApp {

    // Simulating the system state from previous Use Cases
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, String> activeBookings = new HashMap<>(); // ResID -> RoomType

    // Stack to handle LIFO Rollback for released Room IDs
    private Stack<String> releasedRoomIds = new Stack<>();

    public UseCase10BookingCancellation() {
        // Setup initial state: 1 Deluxe room booked, 0 currently available
        inventory.put("Deluxe", 0);
        activeBookings.put("RES-101", "Deluxe");

        System.out.println("Initial State: 1 Deluxe room booked (RES-101), Inventory: 0");
    }

    /**
     * Performs a controlled rollback of a booking.
     * Demonstrates State Reversal and Inventory Restoration.
     */
    public void cancelBooking(String reservationId, String roomId) {
        System.out.println("\n--- Initiating Cancellation for: " + reservationId + " ---");

        // 1. Validation of Cancellation Request
        if (!activeBookings.containsKey(reservationId)) {
            System.out.println("ERROR: Cancellation failed. Reservation ID " + reservationId + " not found.");
            return;
        }

        // 2. Identify Room Type for Inventory Restoration
        String roomType = activeBookings.get(reservationId);

        // 3. LIFO Rollback Logic: Push released Room ID to Stack
        releasedRoomIds.push(roomId);
        System.out.println("LOG: Room ID " + roomId + " pushed to rollback stack.");

        // 4. Inventory Restoration: Increment count immediately
        inventory.put(roomType, inventory.get(roomType) + 1);

        // 5. Controlled Mutation: Remove from active records
        activeBookings.remove(reservationId);

        System.out.println("SUCCESS: Reservation " + reservationId + " cancelled.");
        System.out.println("Inventory for " + roomType + " restored to: " + inventory.get(roomType));
    }

    public void showRollbackStatus() {
        System.out.println("\n--- Current System Recovery State ---");
        System.out.println("Rooms available for re-assignment (Stack): " + releasedRoomIds);
        System.out.println("Active Bookings remaining: " + activeBookings.size());
        System.out.println("-------------------------------------");
    }

    public static void main(String[] args) {
        UseCase10BookingCancellation service = new UseCase10BookingCancellation();

        // Scenario 1: Valid Cancellation
        service.cancelBooking("RES-101", "DELUXE-101");
        service.showRollbackStatus();

        // Scenario 2: Invalid Cancellation (Non-existent ID)
        service.cancelBooking("RES-999", "NONE-000");
    }
}