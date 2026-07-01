import java.io.*;
import java.util.*;

public class HotelManager {
    private final Map<Integer, Room> rooms = new LinkedHashMap<>();
    private final Map<String, Booking> bookings = new HashMap<>();
    private final String ROOMS_FILE = "rooms.txt";
    private final String BOOKINGS_FILE = "bookings.txt";

    public HotelManager() {
        loadRooms();
        loadBookings();
    }

    // --- File I/O operations ---
    private void loadRooms() {
        File file = new File(ROOMS_FILE);
        if (!file.exists()) {
            // Seed initial data if file doesn't exist
            rooms.put(101, new Room(101, RoomType.STANDARD, true));
            rooms.put(102, new Room(102, RoomType.STANDARD, true));
            rooms.put(201, new Room(201, RoomType.DELUXE, true));
            rooms.put(202, new Room(202, RoomType.DELUXE, true));
            rooms.put(301, new Room(301, RoomType.SUITE, true));
            saveRooms();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ROOMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int num = Integer.parseInt(parts[0]);
                RoomType type = RoomType.valueOf(parts[1]);
                boolean avail = Boolean.parseBoolean(parts[2]);
                rooms.put(num, new Room(num, type, avail));
            }
        } catch (IOException e) {
            System.out.println("Error reading rooms database.");
        }
    }

    private void saveRooms() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ROOMS_FILE))) {
            for (Room room : rooms.values()) {
                writer.println(room.getRoomNumber() + "," + room.getRoomType().name() + "," + room.isAvailable());
            }
        } catch (IOException e) {
            System.out.println("Error saving rooms to database.");
        }
    }

    private void loadBookings() {
        File file = new File(BOOKINGS_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Booking b = new Booking(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Double.parseDouble(parts[4]));
                bookings.put(b.getBookingId(), b);
            }
        } catch (IOException e) {
            System.out.println("Error reading bookings database.");
        }
    }

    private void saveBookings() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKINGS_FILE))) {
            for (Booking b : bookings.values()) {
                writer.println(b.getBookingId() + "," + b.getGuestName() + "," + b.getRoomNumber() + "," + b.getNights() + "," + b.getTotalPaid());
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings database.");
        }
    }

    // --- Core Functional Features ---
    public void displayAvailableRooms(RoomType type) {
        System.out.println("\n--- Available Rooms (" + type + ") ---");
        boolean found = false;
        for (Room r : rooms.values()) {
            if (r.getRoomType() == type && r.isAvailable()) {
                System.out.println(r);
                found = true;
            }
        }
        if (!found) System.out.println("No available rooms in this category.");
    }

public void displayAllRooms() {
    System.out.println("\n--- Current Room Inventory Status ---");

    rooms.values().forEach(room -> System.out.println(room.toString()));
}
    public Booking getBookingDetails(String id) {
        return bookings.get(id);
    }

    public boolean bookRoom(String guestName, int roomNumber, int nights) {
        Room room = rooms.get(roomNumber);
        if (room == null || !room.isAvailable()) {
            System.out.println("Error: Room is either invalid or already occupied!");
            return false;
        }

        double totalCost = room.getRoomType().getPricePerNight() * nights;
        System.out.printf("Total Cost to process: $%.2f\n", totalCost);
        
        // Payment Simulation
        System.out.print("Simulating Secure Payment Gateway Interaction...");
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        System.out.println(" Success!");

        // Update State
        String bookingId = "BK-" + System.currentTimeMillis() % 100000;
        room.setAvailable(false);
        Booking newBooking = new Booking(bookingId, guestName, roomNumber, nights, totalCost);
        bookings.put(bookingId, newBooking);

        // Sync to files
        saveRooms();
        saveBookings();

        System.out.println("\n🎉 Booking Confirmed successfully!");
        System.out.println(newBooking);
        return true;
    }

    public boolean cancelReservation(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) {
            System.out.println("Error: Booking Reference ID not found.");
            return false;
        }

        // Free up room
        Room room = rooms.get(booking.getRoomNumber());
        if (room != null) room.setAvailable(true);

        // Remove booking record
        bookings.remove(bookingId);

        // Sync updates to files
        saveRooms();
        saveBookings();

        System.out.println("Cancellation completed successfully. Processing refund parameters.");
        return true;
    }
}