package cinemamanagement.controller;

import cinemamanagement.model.Database;
import cinemamanagement.model.GetData;
import cinemamanagement.utils.Helper;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author hoai thuong
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane signIn_form;

    @FXML
    private TextField signIn_username;

    @FXML
    private PasswordField signIn_password;

    @FXML
    private Button signIn_loginBtn;

    @FXML
    private Hyperlink signIn_createAccount;

    @FXML
    private AnchorPane signUp_form;

    @FXML
    private TextField signUp_email;

    @FXML
    private TextField signUp_username;

    @FXML
    private PasswordField signUp_password;

    @FXML
    private Hyperlink signUp_alreadyHaveAccount;

    // Database
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public boolean validEmail() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");

        Matcher match = pattern.matcher(signUp_email.getText());

        if (match.find() && match.group().matches(signUp_email.getText())) {
            return true;
        } else {
            Helper.showAlert(AlertType.ERROR, "Error Message", null, "Invalid email");
            return false;
        }
    }

    public void signup() throws IOException {
        String sql = "INSERT INTO admin (email, username, password) VALUES (?, ?, ?)";
        String sql1 = "SELECT username FROM admin WHERE username = ?";

        connect = Database.connectDb();

        try {
            // Check if the email/username/password is empty
            if (signUp_email.getText().isEmpty() || signUp_username.getText().isEmpty()
                    || signUp_password.getText().isEmpty()) {
                Helper.showAlert(AlertType.ERROR, "Error Message", null, "Please fill all blank fields");

                // Check if the password is at least 8 characters long
            } else if (signUp_password.getText().length() < 8) {
                Helper.showAlert(AlertType.ERROR, "Error Message", null, "Min 8 characters");

            } else {
                if (validEmail()) {
                    prepare = (PreparedStatement) connect.prepareStatement(sql1);
                    prepare.setString(1, signUp_username.getText());
                    result = prepare.executeQuery();

                    if (result.next()) {
                        Helper.showAlert(AlertType.ERROR, "Error Message", null, signUp_username.getText() + " already exists!");
                    } else {
                        prepare = (PreparedStatement) connect.prepareStatement(sql);
                        prepare.setString(1, signUp_email.getText());
                        prepare.setString(2, signUp_username.getText());
                        prepare.setString(3, signUp_password.getText());
                        prepare.executeUpdate();

                        Helper.showAlert(AlertType.INFORMATION, "Information Message", null, "Successfully created a new account!");
                        
                        GetData.username = signUp_username.getText();

                        // After successful creation of a new account
                        Helper.loadAndShowFXML("/cinemamanagement/view/Dashboard.fxml");

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Helper.showAlert(AlertType.ERROR, "Database Error", "An error occurred while signing up.", e.getMessage());
        }
    }

    public void signin() throws IOException {
        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";
        connect = Database.connectDb();

        try {
            prepare = (PreparedStatement) connect.prepareStatement(sql);
            prepare.setString(1, signIn_username.getText());
            prepare.setString(2, signIn_password.getText());

            result = prepare.executeQuery();

            // check if the username or password is empty
            if (signIn_username.getText().isEmpty() || signIn_password.getText().isEmpty()) {
                Helper.showAlert(AlertType.ERROR, "Error Message", null, "Please fill all bank fields");
            } else {
                if (result.next()) {
                    GetData.username = signIn_username.getText();

                    Helper.showAlert(AlertType.INFORMATION, "Information Message", null, "Successfully Login!");

                    signIn_loginBtn.getScene().getWindow().hide();

                    Helper.loadAndShowFXML("/cinemamanagement/view/Dashboard.fxml");

                } else {
                    Helper.showAlert(AlertType.ERROR, "Error Message", null, "Wrong Username/Password!");
                }
            }
        } catch (SQLException e) {
            e.getMessage();
            Helper.showAlert(AlertType.ERROR, "Database Error", "An error occurred while signing in.", e.getMessage());
        }
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == signIn_createAccount) {
            signIn_form.setVisible(false);
            signUp_form.setVisible(true);
        } else if (event.getSource() == signUp_alreadyHaveAccount) {
            signIn_form.setVisible(true);
            signUp_form.setVisible(false);
        }
    }

    public void signIn_close() {
        System.exit(0);
    }

    public void signIn_minimize() {
        Stage stage = (Stage) signIn_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void signUp_close() {
        System.exit(0);
    }

    public void signUp_minimize() {
        Stage stage = (Stage) signUp_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
