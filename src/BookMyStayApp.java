/**
 * UseCase2RoomInitialization demonstrates basic Room modeling using
 * Abstraction, Inheritance, and Polymorphism.
 * * @author YourName
 * @version 2.0
 */

// --- Abstract Base Class ---
abstract class Room {
    private String roomType;
    private double price;

    public Room(String roomType, double price) {
        this.roomType = roomType;
        this.price = price;
    }

    public String getRoomType() { return roomType; }
    public double getPrice() { return price; }

    // Abstract method to be implemented by subclasses
    public abstract void displayFeatures();
}

// --- Concrete Subclasses ---
class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 100.0); }
    @Override
    public void displayFeatures() {
        System.out.println("Features: 1 Bed, Standard WiFi, Desk.");
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 180.0); }
    @Override
    public void displayFeatures() {
        System.out.println("Features: 2 Beds, Premium WiFi, Mini-fridge.");
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 350.0); }
    @Override
    public void displayFeatures() {
        System.out.println("Features: King Bed, Living Area, Balcony, 24/7 Room Service.");
    }
}

// --- Main Application Class ---
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("*************************************************");
        System.out.println("   Book My Stay - Room Initialization (v2.0)   ");
        System.out.println("*************************************************");

        // Static Availability Representation (Hardcoded variables)
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Polymorphism: Handling different room types using the 'Room' reference
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        for (Room room : rooms) {
            System.out.println("\nRoom Type: " + room.getRoomType());
            System.out.println("Price per Night: $" + room.getPrice());
            room.displayFeatures();

            // Check availability based on type
            int count = 0;
            if (room instanceof SingleRoom) count = singleRoomAvailability;
            else if (room instanceof DoubleRoom) count = doubleRoomAvailability;
            else if (room instanceof SuiteRoom) count = suiteRoomAvailability;

            System.out.println("Current Availability: " + count + " rooms left.");
        }

        System.out.println("\n*************************************************");
        System.out.println("End of Room List.");
    }
}