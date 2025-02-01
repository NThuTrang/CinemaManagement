package cinemamanagement.model;

import java.sql.Date;
import java.sql.Time;

public class CustomerData {
    private final Integer id;
    private final String type;
    private final String title;
    private final Integer quantity;
    private final double total;
    private final Date date;
    private final Time time;

    public CustomerData(Integer id, String type, String title, Integer quantity, double total, Date date, Time time) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.quantity = quantity;
        this.total = total;
        this.date = date;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return total;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "CustomerData{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", quantity=" + quantity +
                ", total=" + total +
                ", date=" + date +
                ", time=" + time +
                '}';
    }    
}
