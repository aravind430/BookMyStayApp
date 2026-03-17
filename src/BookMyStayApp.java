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

public class BookMyStayApp {

    // List to maintain historical records in insertion order (Audit Trail)
    private List<Reservation> bookingHistory = new ArrayList<>();

    /**
     * Records a confirmed reservation into history.
     * Demonstrates Persistence Mindset and Ordered Storage.
     */
    public void recordReservation(String id, String type, String room, List<String> addons, double cost) {
        Reservation record = new Reservation(id, type, room, addons, cost);
        bookingHistory.add(record);
        System.out.println("LOG: Reservation " + id + " saved to history.");
    }

    /**
     * Generates a summary report for the administrator.
     * Demonstrates Reporting Readiness and Operational Visibility.
     */
    public void generateReport() {
        System.out.println("\n========================================================");
        System.out.println("        OFFICIAL BOOKING HISTORY REPORT                ");
        System.out.println("========================================================");

        if (bookingHistory.isEmpty()) {
            System.out.println("No records found.");
        } else {
            double totalRevenue = 0;
            for (Reservation res : bookingHistory) {
                System.out.println(res);
                totalRevenue += res.totalCost;
            }
            System.out.println("--------------------------------------------------------");
            System.out.println("Total Reservations processed: " + bookingHistory.size());
            System.out.printf("Total Revenue Generated:      $%.2f\n", totalRevenue);
        }
        System.out.println("========================================================\n");
    }

    public static void main(String[] args) {
        UseCase8BookingHistoryReport reportService = new UseCase8BookingHistoryReport();

        // Simulating the recording of several completed bookings
        reportService.recordReservation(
                "RES-101", "Deluxe", "DEL-101",
                Arrays.asList("Breakfast", "WiFi"), 180.0
        );

        reportService.recordReservation(
                "RES-102", "Suite", "SUI-201",
                Arrays.asList("Spa", "Gym"), 450.0
        );

        reportService.recordReservation(
                "RES-103", "Deluxe", "DEL-102",
                new ArrayList<>(), 150.0
        );

        // Administrator requests the report
        reportService.generateReport();
    }
}