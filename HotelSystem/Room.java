public class Room {
    private final int roomNumber;
    private final RoomType roomType;
    private boolean isAvailable;

    public Room(int roomNumber, RoomType roomType, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isAvailable = isAvailable;
    }

    public int getRoomNumber() { return roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return "Room #" + roomNumber + " [" + roomType + "] - $" + roomType.getPricePerNight() + "/night (" + (isAvailable ? "Available" : "Booked") + ")";
    }
}