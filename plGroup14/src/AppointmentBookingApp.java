import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AppointmentBookingApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AppointmentBookingSystem bookingSystem = new AppointmentBookingSystem();

        while (true) {
            System.out.println("\nAppointment Booking System");
            System.out.println("1. Create Appointment");
            System.out.println("2. Read All Appointments");
            System.out.println("3. Update Appointment");
            System.out.println("4. Delete Appointment");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter customer name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter time (HH:MM): ");
                    String time = scanner.nextLine();
                    try {
                        bookingSystem.createAppointment(customerName, date, time);
                    } catch (SQLException e) {
                        System.err.println("Error creating appointment: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        List<Appointment> appointments = bookingSystem.readAllAppointments();
                        if (appointments.isEmpty()) {
                            System.out.println("No appointments found.");
                        } else {
                            System.out.println("\nAll Appointments:");
                            for (Appointment appointment : appointments) {
                                System.out.println("ID: " + appointment.getId() + ", Customer Name: " + appointment.getCustomerName() +
                                        ", Date: " + appointment.getDate() + ", Time: " + appointment.getTime());
                            }
                        }
                    } catch (SQLException e) {
                        System.err.println("Error reading appointments: " + e.getMessage());
                    }
                    break;
                case 3:
                    while (true) {
                        System.out.print("Enter appointment ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();  // Consume leftover newline character


                        String newCustomerName;
                        while (true) {
                            System.out.print("Enter new customer name: ");
                            newCustomerName = scanner.nextLine();
                            if (!newCustomerName.isEmpty()) {
                                break;  // Valid customer name entered
                            }
                            System.out.println("Customer name cannot be blank. Please enter a name.");
                        }

                        String newDate;
                        while (true) {
                            System.out.print("Enter new date (YYYY-MM-DD): ");
                            newDate = scanner.nextLine();
                            if (!newDate.isEmpty()) {
                                break;  // Valid date entered
                            }
                            System.out.println("Date cannot be blank. Please enter a date.");
                        }

                        String newTime;
                        while (true) {
                            System.out.print("Enter new time (HH:MM): ");
                            newTime = scanner.nextLine();
                            if (!newTime.isEmpty()) {
                                break;  // Valid time entered
                            }
                            System.out.println("Time cannot be blank. Please enter a time.");
                        }

                        try {
                            bookingSystem.updateAppointment(updateId, newCustomerName, newDate, newTime);
                            System.out.println("Appointment updated successfully.");
                            break;  // Exit the outer while loop after successful update
                        } catch (SQLException e) {
                            System.err.println("Error updating appointment: " + e.getMessage());
                        }
                    }
                    break;
                case 4:
                    System.out.print("Enter appointment ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        bookingSystem.deleteAppointment(deleteId);
                    } catch (SQLException e) {
                        System.err.println("Error deleting appointment: " + e.getMessage());
                    }
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