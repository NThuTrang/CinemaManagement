package cinemamanagement.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hoai thuong
 */
public class Database {
    public static Connection connectDb(){
        try{            
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management", "root", "");
            
            return connect;
        
        } catch(SQLException e){
            e.getMessage();
        }
        
        return null;
    }
}
