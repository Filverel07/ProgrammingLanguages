import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentBookingSystem {

    private static final String url="jdbc:mysql://localhost:3306/plGroup14";
    private static final String username="root";
    private static final String password="";

    public void createAppointment(String customerName, String date, String time, String roomType) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO appointments (customer_name, date, time, room_type) VALUES (?, ?, ?, ?)");
        statement.setString(1, customerName);
        statement.setString(2, date);
        statement.setString(3, time);
        statement.setString(4, roomType);
        statement.executeUpdate();
        statement.close();
        connection.close();
        System.out.println("Appointment created successfully.");
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