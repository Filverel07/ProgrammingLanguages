import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AppointmentBookingApp {

    private final Scanner scanner = new Scanner(System.in);
    private final AppointmentBookingSystem bookingSystem = new AppointmentBookingSystem();

    public static void main(String[] args) {
        new AppointmentBookingApp().run();
    }

    private void run() {
        while (true) {
            displayMenu();
            int choice = getChoice();
            switch (choice) {
                case 1:
                    createAppointment();
                    break;
                case 2:
                    readAllAppointments();
                    break;
                case 3:
                    updateAppointment();
                    break;
                case 4:
                    deleteAppointment();
                    break;
                case 5:
                    exit();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nAppointment Booking System");
        System.out.println("1. Create Appointment");
        System.out.println("2. Read All Appointments");
        System.out.println("3. Update Appointment");
        System.out.println("4. Delete Appointment");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void createAppointment() {
        scanner.nextLine();
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        // Regex pattern to match alphabetic characters and spaces
        String nameRegex = "^[a-zA-Z ]+$";

        while (!customerName.matches(nameRegex)) {
            System.out.println("Invalid customer name. Please enter a name without special characters or numbers.");
            System.out.print("Enter customer name: ");
            customerName = scanner.nextLine();
        }

        String dateRegex = "\\d{4}-\\d{2}-\\d{2}";
        String roomTypeRegex = "(Single|Double|Family)";

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        while (!date.matches(dateRegex)) {
            System.out.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
            System.out.print("Enter date (YYYY-MM-DD): ");
            date = scanner.nextLine();
        }

        System.out.print("Enter room type (Single, Double, Family): ");
        String roomType = scanner.nextLine();
        while (!roomType.matches(roomTypeRegex)) {
            System.out.println("Invalid room type. Please enter Single, Double, or Family.");
            System.out.print("Enter room type (Single, Double, Family): ");
            roomType = scanner.nextLine();
        }

        // Consume newline character
        scanner.nextLine();

        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        String checkIn = scanner.nextLine();
        while (!checkIn.matches(dateRegex)) {
            System.out.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
            System.out.print("Enter check-in date (YYYY-MM-DD): ");
            checkIn = scanner.nextLine();
        }

        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        String checkOut = scanner.nextLine();
        while (!checkOut.matches(dateRegex)) {
            System.out.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
            System.out.print("Enter check-out date (YYYY-MM-DD): ");
            checkOut = scanner.nextLine();
        }

        bookingSystem.createAppointment(customerName, date, roomType, checkIn, checkOut);
    }



    private void readAllAppointments() {
        List<Appointment> appointments = bookingSystem.readAllAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.println("\nAll Appointments:");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
    }

    private void updateAppointment() {
        int updateId = getValidAppointmentId();
        scanner.nextLine(); // Consume newline character

        Appointment existingAppointment = bookingSystem.getAppointment(updateId);
        System.out.println("Current Appointment Details:");
        System.out.println(existingAppointment);

        System.out.println("Enter new details (leave blank to skip):");

        System.out.print("New customer name: ");
        String newCustomerName = readNewValue(existingAppointment.getCustomerName());

        System.out.print("New date (YYYY-MM-DD): ");
        String newDate = readNewValue(existingAppointment.getDate());

        System.out.print("New room type (Single, Double, Family): ");
        String newRoomType = readNewValue(existingAppointment.getRoomType());

        System.out.print("New check-in date (YYYY-MM-DD): ");
        String newCheckIn = readNewValue(existingAppointment.getCheckIn());

        System.out.print("New check-out date (YYYY-MM-DD): ");
        String newCheckOut = readNewValue(existingAppointment.getCheckOut());

        // Update only if at least one field is changed
        if (!newCustomerName.equals(existingAppointment.getCustomerName()) ||
                !newDate.equals(existingAppointment.getDate()) ||
                !newRoomType.equals(existingAppointment.getRoomType()) ||
                !newCheckIn.equals(existingAppointment.getCheckIn()) ||
                !newCheckOut.equals(existingAppointment.getCheckOut())) {

            bookingSystem.updateAppointment(updateId,
                    newCustomerName.isEmpty() ? null : newCustomerName,
                    newDate.isEmpty() ? null : newDate,
                    newRoomType.isEmpty() ? null : newRoomType,
                    newCheckIn.isEmpty() ? null : newCheckIn,
                    newCheckOut.isEmpty() ? null : newCheckOut);
        } else {
            System.out.println("No changes made. Appointment remains unchanged.");
        }
    }

    private String readNewValue(String currentValue) {
        String newValue = scanner.nextLine();
        return newValue.trim().isEmpty() ? currentValue : newValue;
    }


    private void deleteAppointment() {
        int deleteId = getValidAppointmentId();
        bookingSystem.deleteAppointment(deleteId);
        System.out.println("Appointment deleted successfully.");
    }

    private int getValidAppointmentId() {
        while (true) {
            System.out.print("Enter appointment ID: ");
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Error: Please enter a valid integer appointment ID.");
                scanner.next(); // Consume the invalid input
            }
        }
    }

    private void exit() {
        System.out.println("Exiting...");
        scanner.close();
        System.exit(0);
    }
}
