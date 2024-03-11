public class Appointment {
    private int id;
    private String customerName;
    private String date;
    private String roomType;
    private String checkIn;
    private String checkOut;

    public Appointment(int id, String customerName, String date, String roomType, String checkIn, String checkOut) {
        this.id = id;
        this.customerName = customerName;
        this.date = date;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
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

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
    @Override
    public String toString() {
        return "ID: " + id + ", Customer Name: " + customerName + ", Date: " + date +
                ", Room Type: " + roomType + ", Check-in: " + checkIn + ", Check-out: " + checkOut;
    }
}