package cinemamanagement;

import cinemamanagement.utils.Helper;
import javafx.application.Application;
import javafx.stage.Stage;


public class CinemaManagement extends Application {    
    @Override
    public void start(Stage stage) throws Exception {
        Helper.loadAndShowFXML("/cinemamanagement/view/FXMLDocument.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }   
}

