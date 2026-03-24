public class BookMyStayApp {
    import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

    class BookingService {

        private RoomInventory inventory;
        private BookingQueue bookingQueue;

        // Map: RoomType -> Set of allocated Room IDs
        private Map<String, Set<String>> allocatedRooms;

        // Global set to ensure uniqueness
        private Set<String> allRoomIds;

        private int idCounter = 1;

        public BookingService(RoomInventory inventory, BookingQueue bookingQueue) {
            this.inventory = inventory;
            this.bookingQueue = bookingQueue;
            this.allocatedRooms = new HashMap<>();
            this.allRoomIds = new HashSet<>();
        }

        // Process all requests (FIFO)
        public void processBookings() {
            System.out.println("=== Processing Bookings ===");

            Reservation request;

            while ((request = bookingQueue.peekNext()) != null) {

                // remove from queue
                request = bookingQueue.pollRequest();

                String type = request.getRoomType();
                int available = inventory.getAvailability(type);

                if (available > 0) {

                    // Generate unique room ID
                    String roomId = generateRoomId(type);

                    // Ensure uniqueness
                    while (allRoomIds.contains(roomId)) {
                        roomId = generateRoomId(type);
                    }

                    // Store globally
                    allRoomIds.add(roomId);

                    // Store per room type
                    allocatedRooms
                            .computeIfAbsent(type, k -> new HashSet<>())
                            .add(roomId);

                    // Update inventory immediately
                    inventory.updateAvailability(type, available - 1);

                    // Confirm booking
                    System.out.println("Booking Confirmed → "
                            + request.getGuestName()
                            + " | " + type
                            + " | Room ID: " + roomId);

                } else {
                    System.out.println("Booking Failed (No Availability) → "
                            + request.getGuestName()
                            + " | " + type);
                }
            }

            System.out.println("----------------------------");
        }

        // Generate ID
        private String generateRoomId(String type) {
            return type.replaceAll(" ", "").substring(0, 2).toUpperCase() + idCounter++;
        }

        // View allocations
        public void displayAllocations() {
            System.out.println("=== Allocated Rooms ===");
            for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
            System.out.println("------------------------");
        }
    }
}
