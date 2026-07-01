public class Booking {
    private final String bookingId;
    private final String guestName;
    private final int roomNumber;
    private final int nights;
    private final double totalPaid;

    public Booking(String bookingId, String guestName, int roomNumber, int nights, double totalPaid) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.nights = nights;
        this.totalPaid = totalPaid;
    }

    public String getBookingId() { return bookingId; }
    public String getGuestName() { return guestName; }
    public int getRoomNumber() { return roomNumber; }
    public int getNights() { return nights; }
    public double getTotalPaid() { return totalPaid; }

    @Override
    public String toString() {
        return String.format("Booking ID: %s | Guest: %s | Room: %d | Nights: %d | Paid: $%.2f", 
                bookingId, guestName, roomNumber, nights, totalPaid);
    }
}