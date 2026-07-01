import java.util.Scanner;

public class HotelSystem {
    public static void main(String[] args) {
        HotelManager manager = new HotelManager();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=========================================");
        System.out.println("       WELCOME TO GRAND HORIZON HOTEL     ");
        System.out.println("=========================================");

        while (true) {
            System.out.println("\n1. Search Available Rooms By Type");
            System.out.println("2. Make a Room Booking");
            System.out.println("3. View Reservation Details");
            System.out.println("4. Cancel a Reservation");
            System.out.println("5. Admin View: All Room Statuses");
            System.out.println("6. Exit");
            System.out.print("Please select an option: ");
            
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    RoomType selectedType = promptRoomType(scanner);
                    if (selectedType != null) {
                        manager.displayAvailableRooms(selectedType);
                    }
                    break;
                case "2":
                    System.out.print("Enter Guest full name: ");
                    String name = scanner.nextLine();
                    RoomType typeChoice = promptRoomType(scanner);
                    if (typeChoice == null) break;
                    
                    manager.displayAvailableRooms(typeChoice);
                    System.out.print("Enter the precise Room Number you wish to book: ");
                    int roomNum = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter total length of stay (Nights): ");
                    int nights = Integer.parseInt(scanner.nextLine());
                    
                    manager.bookRoom(name, roomNum, nights);
                    break;
                case "3":
                    System.out.print("Enter your Booking ID reference string: ");
                    String bId = scanner.nextLine().trim();
                    Booking booking = manager.getBookingDetails(bId);
                    if (booking != null) {
                        System.out.println("\n[Booking Summary Found]");
                        System.out.println(booking);
                    } else {
                        System.out.println("No records matched that Booking ID.");
                    }
                    break;
                case "4":
                    System.out.print("Enter the Booking reference ID to cancel: ");
                    String cancelId = scanner.nextLine().trim();
                    manager.cancelReservation(cancelId);
                    break;
                case "5":
                    manager.displayAllRooms();
                    break;
                case "6":
                    System.out.println("Thank you for using Grand Horizon Hotel Core System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid execution flag entry. Select 1-6.");
            }
        }
    }

    private static RoomType promptRoomType(Scanner scanner) {
        System.out.println("Select Category Tier: [1] Standard  [2] Deluxe  [3] Suite");
        System.out.print("Choice: ");
        String selection = scanner.nextLine().trim();
        switch (selection) {
            case "1": return RoomType.STANDARD;
            case "2": return RoomType.DELUXE;
            case "3": return RoomType.SUITE;
            default:
                System.out.println("Invalid Room Type tier criteria.");
                return null;
        }
    }
}