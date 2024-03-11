import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentBookingSystem {

    private static final String url="jdbc:mysql://localhost:3306/plGroup14";
    private static final String username="root";
    private static final String password="";

    public void createAppointment(String customerName, String date, String time, String roomType) throws SQLException {

        Connection connection = getConnection();

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO appointments (customer_name, date, time, room_type) VALUES (?, ?, ?, ?)")) {

            // Validate and set appointment details
            if (customerName.isEmpty()) {
                throw new IllegalArgumentException("Customer name cannot be blank.");
            }
            statement.setString(1, customerName);

            if (!date.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")) {
                throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
            }
            statement.setString(2, date);

            if (!time.matches("^([0-1][0-9]|2[0-3]):([0-5][0-9])$")) {
                throw new IllegalArgumentException("Invalid time format. Please use HH:MM.");
            }
            statement.setString(3, time);

            if (!roomType.matches("Single|Double|Family")) {
                throw new IllegalArgumentException("Invalid room type. Please choose Single, Double, or Family.");
            }
            statement.setString(4, roomType);

            // Execute the statement and close resources within the try-with-resources block
            statement.executeUpdate();
            System.out.println("Appointment created successfully.");

        } finally {
            // Ensure connection is closed even if an exception occurs
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<Appointment> readAllAppointments() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String customerName = resultSet.getString("customer_name");
            String date = resultSet.getString("date");
            String time = resultSet.getString("time");
            String roomType = resultSet.getString("room_type");
            appointments.add(new Appointment(id, customerName, date, time, roomType));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return appointments;
    }

    public void updateAppointment(int id, String customerName, String date, String time, String roomType) throws SQLException {
        if (customerName == null || date == null || time == null || roomType == null) {
            throw new IllegalArgumentException("Customer name, date, time, and room type cannot be blank.");
        }

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE appointments SET customer_name = ?, date = ?, time = ?, room_type = ? WHERE id = ?");
        statement.setString(1, customerName);
        statement.setString(2, date);
        statement.setString(3, time);
        statement.setString(4, roomType);  // Include roomType in the prepared statement
        statement.setInt(5, id);
        int rowsUpdated = statement.executeUpdate();

        if (customerName == null || customerName.isEmpty() ||
                date == null || date.isEmpty() ||
                time == null || time.isEmpty() ||
                roomType == null || roomType.isEmpty()) {
            throw new IllegalArgumentException("Customer name, date, time, and room type cannot be blank.");
        } else if (rowsUpdated > 0) {
            System.out.println("Appointment updated successfully.");
        } else {
            System.out.println("Appointment not found.");
        }
        statement.close();
        connection.close();
    }

    public void deleteAppointment(int id) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM appointments WHERE id = ?");
        statement.setInt(1, id);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Appointment deleted successfully.");
        } else {
            System.out.println("Appointment not found.");
        }
        statement.close();
        connection.close();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}