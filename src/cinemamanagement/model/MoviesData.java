package cinemamanagement.model;

import java.sql.Date;

public class MoviesData {
    private final int id;
    private final String title;
    private final String genre;
    private final String duration;
    private final String image;
    private final Date date;
    private final String current;
    
    public MoviesData(int id, String title, String genre, String duration, String image, Date date, String current) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.image = image;
        this.date = date;
        this.current = current;
    }
    
    public int getId(){
        return id;
    }
    public String getTitle() {
        return title;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public String getDuration() {
        return duration;
    }
    
    public String getImage() {
        return image;
    }
    
    public Date getDate() {
        return date;
    }
    
    public String getCurrent(){
        return current;
    }
}
