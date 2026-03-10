import java.util.LinkedList;
import java.util.Queue;

/**
 * UseCase5BookingRequestQueue demonstrates fair request handling using a Queue.
 * It decouples request intake from the actual room allocation logic.
 * * @author YourName
 * @version 5.0
 */

// --- Reservation Class (Data Holder) ---
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "Request [Guest: " + guestName + ", Room: " + roomType + "]";
    }
}

// --- Booking Request Manager ---
class BookingRequestQueue {
    // LinkedList implements the Queue interface in Java
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        this.requestQueue = new LinkedList<>();
    }

    /**
     * Adds a new booking request to the end of the line (FIFO).
     */
    public void submitRequest(Reservation request) {
        requestQueue.add(request);
        System.out.println("[Queue Update]: " + request.getGuestName() + "'s request received.");
    }

    /**
     * Displays all pending requests in the order they arrived.
     */
    public void displayPendingRequests() {
        System.out.println("\n--- Current Booking Queue (Waiting for Allocation) ---");
        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
        } else {
            for (Reservation res : requestQueue) {
                System.out.println(res);
            }
        }
    }

    /**
     * Helper to check the total count in queue.
     */
    public int getQueueSize() {
        return requestQueue.size();
    }
}

// --- Main Application Class ---
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("*************************************************");
        System.out.println("   Book My Stay - Booking Request Queue (v5.0) ");
        System.out.println("*************************************************");

        // 1. Initialize the Queue System
        BookingRequestQueue queueSystem = new BookingRequestQueue();

        // 2. Simulate Incoming Concurrent Requests
        System.out.println("Incoming requests arriving...");
        queueSystem.submitRequest(new Reservation("Alice", "Suite"));
        queueSystem.submitRequest(new Reservation("Bob", "Single"));
        queueSystem.submitRequest(new Reservation("Charlie", "Suite"));
        queueSystem.submitRequest(new Reservation("Diana", "Double"));

        // 3. Display Queue State
        queueSystem.displayPendingRequests();

        System.out.println("\n[System Note]: Requests are stored in FIFO order.");
        System.out.println("No rooms have been allocated yet.");
        System.out.println("*************************************************");
    }
}