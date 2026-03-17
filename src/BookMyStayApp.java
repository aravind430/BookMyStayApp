import java.util.*;
import java.util.concurrent.*;

public class BookMyStayApp {

    // Shared mutable state: Inventory
    private int availableRooms = 5;

    // Thread-safe collection for requests
    private BlockingQueue<String> bookingQueue = new LinkedBlockingQueue<>();

    // List to track successful allocations
    private List<String> confirmedAllocations = Collections.synchronizedList(new ArrayList<>());

    /**
     * The processBooking method represents the Critical Section.
     * Synchronized ensures only one thread can modify inventory at a time.
     */
    public synchronized void processBooking(String guestName) {
        System.out.println(Thread.currentThread().getName() + " is attempting to book for: " + guestName);

        // Check inventory within the synchronized block to prevent Race Conditions
        if (availableRooms > 0) {
            // Simulate a small processing delay to highlight potential race conditions if unsynchronized
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            availableRooms--;
            confirmedAllocations.add(guestName + " (Room #" + (5 - availableRooms) + ")");
            System.out.println("SUCCESS: " + guestName + " secured a room. Rooms left: " + availableRooms);
        } else {
            System.out.println("FAILED: No rooms left for " + guestName);
        }
    }

    public void startSimulation() {
        // Create a thread pool to simulate concurrent guests
        ExecutorService executor = Executors.newFixedThreadPool(3);

        String[] guests = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank"};

        System.out.println("--- Starting Concurrent Simulation (5 Rooms, 6 Guests) ---");

        for (String guest : guests) {
            // Each guest request is handled by a separate thread
            executor.execute(() -> {
                processBooking(guest);
            });
        }

        executor.shutdown();
        try {
            // Wait for all threads to finish
            if (executor.awaitTermination(5, TimeUnit.SECONDS)) {
                printFinalReport();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printFinalReport() {
        System.out.println("\n--- Final Allocation Report ---");
        System.out.println("Confirmed Bookings: " + confirmedAllocations);
        System.out.println("Final Inventory Count: " + availableRooms);
        System.out.println("System Integrity: " + (availableRooms >= 0 ? "PASSED" : "FAILED"));
        System.out.println("--------------------------------");
    }

    public static void main(String[] args) {
        UseCase11ConcurrentBookingSimulation simulation = new UseCase11ConcurrentBookingSimulation();
        simulation.startSimulation();
    }
}