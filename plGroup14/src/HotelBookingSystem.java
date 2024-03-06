import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class HotelBooking {
    private int id;
    private String customerName;
    private String date;

    public HotelBooking(int id, String customerName, String date) {
        this.id = id;
        this.customerName = customerName;
        this.date = date;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    // Override toString method to facilitate writing to file
    @Override
    public String toString() {
        return id + "," + customerName + "," + date;
    }
}

public class HotelBookingSystem {
    private List<HotelBooking> bookings;
    private int nextId;
    private final String FILENAME = "C:\\Users\\Jap Kyle\\OneDrive\\Documents\\IntelliJ Projects\\ProgrammingLanguages\\plGroup14\\src\\bookings.txt";

    public HotelBookingSystem() {
        bookings = new ArrayList<>();
        nextId = 1;
        loadBookingsFromFile(); // Load bookings from file on initialization
    }

    // Load bookings from file
    private void loadBookingsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String customerName = parts[1];
                String date = parts[2];
                bookings.add(new HotelBooking(id, customerName, date));
                nextId = Math.max(nextId, id + 1); // Update nextId
            }
        } catch (IOException | NumberFormatException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    // Save bookings to file
    private void saveBookingsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            for (HotelBooking booking : bookings) {
                bw.write(booking.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    // Create a new booking
    public void createBooking(String customerName, String date) {
        HotelBooking booking = new HotelBooking(nextId++, customerName, date);
        bookings.add(booking);
        saveBookingsToFile(); // Save bookings to file after creation
        System.out.println("Booking created successfully.");
    }


    // Read all bookings
    public void readAllBookings() {
        System.out.println("All Bookings:");
        for (HotelBooking booking : bookings) {
            System.out.println("ID: " + booking.getId() + ", Customer Name: " + booking.getCustomerName() + ", Date: " + booking.getDate());
        }
    }

    // Update a booking
    public void updateBooking(int id, String customerName, String date) {
        for (HotelBooking booking : bookings) {
            if (booking.getId() == id) {
                booking.setCustomerName(customerName);
                booking.setDate(date);
                System.out.println("Booking updated successfully.");
                return;
            }
        }
        System.out.println("Booking not found.");
    }

    // Delete a booking
    public void deleteBooking(int id) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getId() == id) {
                bookings.remove(i);
                System.out.println("Booking deleted successfully.");
                return;
            }
        }
        System.out.println("Booking not found.");
    }

    public static void main(String[] args) {
        HotelBookingSystem bookingSystem = new HotelBookingSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Create Booking\n2. Read All Bookings\n3. Update Booking\n4. Delete Booking\n5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter customer name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter date: ");
                    String date = scanner.nextLine();
                    bookingSystem.createBooking(customerName, date);
                    break;
                case 2:
                    bookingSystem.readAllBookings();
                    break;
                case 3:
                    System.out.print("Enter booking ID to update: ");
                    int idToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter new customer name: ");
                    String newCustomerName = scanner.nextLine();
                    System.out.print("Enter new date: ");
                    String newDate = scanner.nextLine();
                    bookingSystem.updateBooking(idToUpdate, newCustomerName, newDate);
                    break;
                case 4:
                    System.out.print("Enter booking ID to delete: ");
                    int idToDelete = scanner.nextInt();
                    bookingSystem.deleteBooking(idToDelete);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }
}
