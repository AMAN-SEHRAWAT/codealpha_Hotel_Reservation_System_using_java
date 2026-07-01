public enum RoomType {
    STANDARD(100.0),
    DELUXE(200.0),
    SUITE(450.0);

    private final double pricePerNight;

    RoomType(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }
}