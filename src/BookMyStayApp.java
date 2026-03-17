import java.util.*;

public class UseCase6RoomAllocationService {

    // Queue to store incoming booking requests (FIFO)
    private Queue<String> bookingQueue = new LinkedList<>();

    // Map to store assigned Room IDs for each Room Type
    // HashMap<RoomType, Set<RoomIDs>>
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Simple Inventory Map to track available counts
    private Map<String, Integer> inventory = new HashMap<>();

    public UseCase6RoomAllocationService() {
        // Initialize some dummy data
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);

        bookingQueue.add("Deluxe");
        bookingQueue.add("Deluxe");
        bookingQueue.add("Suite");
        bookingQueue.add("Deluxe"); // This one should fail due to inventory
    }

    public void processAllocations() {
        System.out.println("--- Starting Room Allocation Process ---\n");

        while (!bookingQueue.isEmpty()) {
            String roomType = bookingQueue.poll();
            System.out.println("Processing request for: " + roomType);

            if (isAvailable(roomType)) {
                String roomId = generateUniqueRoomId(roomType);

                // Perform Atomic Logical Operations
                confirmBooking(roomType, roomId);

                System.out.println("SUCCESS: Reserved " + roomType + ". Assigned Room ID: " + roomId);
            } else {
                System.out.println("FAILED: No inventory available for " + roomType);
            }
            System.out.println("Current Inventory for " + roomType + ": " + inventory.get(roomType));
            System.out.println("-------------------------------------------");
        }
    }

    private boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    private String generateUniqueRoomId(String roomType) {
        // Generate a simple unique ID (e.g., DELUXE-101)
        // In a real app, this might involve a database sequence or UUID
        int currentAllocatedCount = allocatedRooms.getOrDefault(roomType, new HashSet<>()).size();
        return roomType.toUpperCase() + "-" + (101 + currentAllocatedCount);
    }

    private void confirmBooking(String roomType, String roomId) {
        // 1. Record the room ID in the Set to enforce uniqueness
        allocatedRooms.putIfAbsent(roomType, new HashSet<>());
        allocatedRooms.get(roomType).add(roomId);

        // 2. Decrement inventory immediately (Inventory Synchronization)
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public static void main(String[] args) {
        UseCase6RoomAllocationService service = new UseCase6RoomAllocationService();
        service.processAllocations();
    }
}