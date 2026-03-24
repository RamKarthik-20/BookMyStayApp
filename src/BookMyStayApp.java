import java.util.*;

// Centralized Inventory
class RoomInventory {

    private Map<String, Integer> availability;

    // Initialize inventory
    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 5);
        availability.put("Double Room", 3);
        availability.put("Suite Room", 2);
    }

    // Get availability (O(1))
    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    // Update availability (controlled)
    public void updateAvailability(String roomType, int count) {
        if (availability.containsKey(roomType)) {
            availability.put(roomType, count);
        } else {
            System.out.println("Invalid room type: " + roomType);
        }
    }

    // Add new room type (scalable)
    public void addRoomType(String roomType, int count) {
        availability.put(roomType, count);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("=== Room Inventory ===");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
        System.out.println("----------------------");
    }
}

// Main
public class HotelApp {
    public static void main(String[] args) {

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial state
        inventory.displayInventory();

        // Retrieve availability
        System.out.println("Single Room Available: " +
                inventory.getAvailability("Single Room"));

        // Update availability
        inventory.updateAvailability("Single Room", 4);

        // Add new room type (scalability demo)
        inventory.addRoomType("Deluxe Room", 2);

        // Display updated state
        inventory.displayInventory();
    }
}
