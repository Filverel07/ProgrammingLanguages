public class Appointment {
    private int id;
    private String customerName;
    private String date;
    private String time;

    public Appointment(int id, String customerName, String date, String time) {
        this.id = id;
        this.customerName = customerName;
        this.date = date;
        this.time = time;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}