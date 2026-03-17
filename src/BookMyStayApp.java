import java.util.*;

// Represents an individual optional offering
class Service {
    String name;
    double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}

public class BookMyStayApp {

    // One-to-Many Relationship: Map reservation ID to a list of services
    private Map<String, List<Service>> reservationAddOns = new HashMap<>();

    // Available services in the hotel
    private Map<String, Service> catalog = new HashMap<>();

    public UseCase7AddOnServiceSelection() {
        // Initialize Service Catalog
        catalog.put("BRK", new Service("Breakfast Buffet", 20.0));
        catalog.put("WFI", new Service("High-Speed WiFi", 10.0));
        catalog.put("SPA", new Service("Spa Treatment", 50.0));
        catalog.put("GYM", new Service("Gym Access", 15.0));
    }

    /**
     * Adds a service to a specific reservation.
     * Demonstrates Map and List combination.
     */
    public void addServiceToReservation(String reservationId, String serviceCode) {
        Service service = catalog.get(serviceCode);

        if (service != null) {
            // Ensure the list exists for this reservation ID
            reservationAddOns.putIfAbsent(reservationId, new ArrayList<>());

            // Add the service to the list (Composition)
            reservationAddOns.get(reservationId).add(service);
            System.out.println("Added " + service.name + " to Reservation: " + reservationId);
        } else {
            System.out.println("Service code " + serviceCode + " not found.");
        }
    }

    /**
     * Calculates the total additional cost for a reservation.
     * Demonstrates Cost Aggregation.
     */
    public double calculateTotalAddOnCost(String reservationId) {
        List<Service> services = reservationAddOns.getOrDefault(reservationId, new ArrayList<>());
        double total = 0;
        for (Service s : services) {
            total += s.price;
        }
        return total;
    }

    public void displayReservationSummary(String reservationId) {
        System.out.println("\n--- Summary for Reservation: " + reservationId + " ---");
        List<Service> services = reservationAddOns.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
        } else {
            System.out.println("Selected Services: " + services);
            System.out.println("Total Add-On Cost: $" + calculateTotalAddOnCost(reservationId));
        }
        System.out.println("------------------------------------------------");
    }

    public static void main(String[] args) {
        UseCase7AddOnServiceSelection manager = new UseCase7AddOnServiceSelection();

        // Simulating guest selections for two different reservations
        String res1 = "RES-DELUXE-101";
        String res2 = "RES-SUITE-202";

        // Guest 1 selects Breakfast and WiFi
        manager.addServiceToReservation(res1, "BRK");
        manager.addServiceToReservation(res1, "WFI");

        // Guest 2 selects Spa and Gym
        manager.addServiceToReservation(res2, "SPA");
        manager.addServiceToReservation(res2, "GYM");

        // Displaying results
        manager.displayReservationSummary(res1);
        manager.displayReservationSummary(res2);
    }
}
