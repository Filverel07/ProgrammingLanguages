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
                    while (true) {
                        try {
                            System.out.print("Enter customer name: ");
                            String customerName = scanner.nextLine();
                            if (customerName.isEmpty()) {
                                throw new IllegalArgumentException("Customer name cannot be blank.");
                            }

                            System.out.print("Enter date (YYYY-MM-DD): ");
                            String date;
                            while (true) {  // Loop for valid date
                                date = scanner.nextLine();
                                if (date.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")) {
                                    break;  // Exit the inner loop for valid date
                                } else {
                                    throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
                                }
                            }

                            System.out.print("Enter time (HH:MM): ");
                            String time;
                            while (true) {  // Loop for valid time
                                time = scanner.nextLine();
                                if (time.matches("^([0-1][0-9]|2[0-3]):([0-5][0-9])$")) {
                                    break;  // Exit the inner loop for valid time
                                } else {
                                    throw new IllegalArgumentException("Invalid time format. Please use HH:MM.");
                                }
                            }

                            System.out.print("Enter room type (Single, Double, Family): ");
                            String roomType;
                            while (true) {  // Loop for valid room type
                                roomType = scanner.nextLine();
                                if (roomType.matches("Single|Double|Family")) {
                                    break;  // Exit the inner loop for valid room type
                                } else {
                                    throw new IllegalArgumentException("Invalid room type. Please choose Single, Double, or Family.");
                                }
                            }

                            bookingSystem.createAppointment(customerName, date, time, roomType);
                            System.out.println("Appointment created successfully.");
                            break;  // Exit the outer loop if all inputs are valid
                        } catch (IllegalArgumentException e) {
                            System.err.println(e.getMessage());
                        } catch (SQLException e) {
                            System.err.println("Error creating appointment: " + e.getMessage());
                        }
                        System.out.println("Would you like to retry creating an appointment? (y/n)");
                        String retry = scanner.nextLine().toLowerCase();
                        if (!retry.equals("y")) {
                            break; // Exit the outer loop if user doesn't want to retry
                        }
                    }
                    scanner.close();
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
                                        ", Date: " + appointment.getDate() + ", Time: " + appointment.getTime() + ", Room Type: " + appointment.getRoomType());
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
                        String newRoomType;
                        while (true) {
                            System.out.print("Enter new Room Type (Single, Double, Family): ");
                            newRoomType = scanner.nextLine();
                            if (!newRoomType.isEmpty()) {
                                break;  // Valid time entered
                            }
                            System.out.println("Room Type cannot be blank. Please enter a type of room.");
                        }

                        try {
                            bookingSystem.updateAppointment(updateId, newCustomerName, newDate, newTime, newRoomType);
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