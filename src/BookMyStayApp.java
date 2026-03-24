import java.util.*;

// Abstract Room
abstract class Room {
    private int beds;
    private double price;

    public Room(int beds, double price) {
        this.beds = beds;
        this.price = price;
    }

    public abstract String getRoomType();

    public void display(int availability) {
        System.out.println("Room: " + getRoomType()
                + " | Beds: " + beds
                + " | Price: ₹" + price
                + " | Available: " + availability);
    }
}

// Concrete Rooms
class SingleRoom extends Room {
    public SingleRoom() { super(1, 1500); }
    public String getRoomType() { return "Single Room"; }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super(2, 3000); }
    public String getRoomType() { return "Double Room"; }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super(3, 7000); }
    public String getRoomType() { return "Suite Room"; }
}

// Inventory (Read-only usage)
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single Room", 5);
        availability.put("Double Room", 3);
        availability.put("Suite Room", 0); // example unavailable
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }
}

// Search Service (Read-only)
class RoomSearchService {
    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void search(List<Room> rooms) {
        System.out.println("=== Available Rooms ===");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());

            // Show only available rooms
            if (available > 0) {
                room.display(available);
            }
        }

        System.out.println("------------------------");
    }
}

// Main
public class HotelApp {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        RoomSearchService searchService =
                new RoomSearchService(inventory);

        // Guest searches rooms
        searchService.search(rooms);
    }
}
