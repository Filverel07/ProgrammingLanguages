import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentBookingSystem {

    private static final String URL = "jdbc:mysql://localhost:3306/plGroup14";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void createAppointment(String customerName, String date, String roomType, String checkIn, String checkOut) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO appointments (customer_name, date, room_type, checkIn, checkOut) VALUES (?, ?, ?, ?, ?)")) {
            statement.setString(1, customerName);
            statement.setString(2, date);
            statement.setString(3, roomType);
            statement.setString(4, checkIn);
            statement.setString(5, checkOut);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Appointment created successfully.");
            } else {
                System.out.println("Failed to create appointment.");
            }
        } catch (SQLException e) {
            System.err.println("Error creating appointment: " + e.getMessage());
        }
    }

    public List<Appointment> readAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments")) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String customerName = resultSet.getString("customer_name");
                String date = resultSet.getString("date");
                String roomType = resultSet.getString("room_type");
                String checkIn = resultSet.getString("checkIn");
                String checkOut = resultSet.getString("checkOut");
                appointments.add(new Appointment(id, customerName, date, roomType, checkIn, checkOut));
            }
        } catch (SQLException e) {
            System.err.println("Error reading appointments: " + e.getMessage());
        }
        return appointments;
    }

    public void updateAppointment(int id, String customerName, String date, String roomType, String checkIn, String checkOut) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE appointments SET customer_name = ?, date = ?, room_type = ?, checkIn = ?, checkOut = ? WHERE id = ?")) {
            statement.setString(1, customerName);
            statement.setString(2, date);
            statement.setString(3, roomType);
            statement.setString(4, checkIn);
            statement.setString(5, checkOut);
            statement.setInt(6, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Appointment updated successfully.");
            } else {
                System.out.println("Appointment not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating appointment: " + e.getMessage());
        }
    }
    public Appointment getAppointment(int id) {
        Appointment appointment = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM appointments WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String customerName = resultSet.getString("customer_name");
                    String date = resultSet.getString("date");
                    String roomType = resultSet.getString("room_type");
                    String checkIn = resultSet.getString("checkIn");
                    String checkOut = resultSet.getString("checkOut");
                    appointment = new Appointment(id, customerName, date, roomType, checkIn, checkOut);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving appointment: " + e.getMessage());
        }
        return appointment;
    }


    public void deleteAppointment(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM appointments WHERE id = ?")) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Appointment deleted successfully.");
            } else {
                System.out.println("Appointment not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting appointment: " + e.getMessage());
        }
    }
}
