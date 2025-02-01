package cinemamanagement.controller;

import cinemamanagement.model.Database;
import cinemamanagement.model.MoviesData;
import cinemamanagement.model.GetData;
import cinemamanagement.model.CustomerData;
import cinemamanagement.utils.Helper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane topForm;

    @FXML
    private Button addMovies_btn;

    @FXML
    private TableColumn<MoviesData, String> addMovies_col_date;

    @FXML
    private TableColumn<MoviesData, String> addMovies_col_duration;

    @FXML
    private TableColumn<MoviesData, String> addMovies_col_genre;

    @FXML
    private TableColumn<MoviesData, String> addMovies_col_movieTitle;

    @FXML
    private TextField addMovies_duration;

    @FXML
    private AnchorPane addMovies_form;

    @FXML
    private TextField addMovies_genre;

    @FXML
    private ImageView addMovies_imageView;

    @FXML
    private TextField addMovies_movieTitle;

    @FXML
    private DatePicker addMovies_date;

    @FXML
    private TextField addMovies_search;

    @FXML
    private TableView<MoviesData> addMovies_tableView;

    @FXML
    private Button availableMovies_btn;

    @FXML
    private TableColumn<?, ?> availableMovies_col_genre;

    @FXML
    private TableColumn<?, ?> availableMovies_col_movieTitle;

    @FXML
    private TableColumn<?, ?> availableMovies_col_showingDate;

    @FXML
    private Label availableMovies_date;

    @FXML
    private AnchorPane availableMovies_form;

    @FXML
    private Label availableMovies_genre;

    @FXML
    private ImageView availableMovies_imageView;

    @FXML
    private Label availableMovies_movieTitle;

    @FXML
    private Label availableMovies_normalClass_price;

    @FXML
    private Spinner<Integer> availableMovies_normalClass_quantity;

    @FXML
    private Label availableMovies_specialClass_price;

    @FXML
    private Spinner<Integer> availableMovies_specialClass_quantity;

    @FXML
    private TableView<MoviesData> availableMovies_tableView;

    @FXML
    private Label availableMovies_title;

    @FXML
    private Label availableMovies_total;

    @FXML
    private Button customers_btn;

    @FXML
    private TableView<CustomerData> customer_tableView;

    @FXML
    private TableColumn<CustomerData, String> customers_col_date;

    @FXML
    private TableColumn<CustomerData, String> customers_col_movieTicket;

    @FXML
    private TableColumn<CustomerData, String> customers_col_ticketNumber;

    @FXML
    private TableColumn<CustomerData, String> customers_col_time;

    @FXML
    private Label customers_dateChecked;

    @FXML
    private AnchorPane customers_form;

    @FXML
    private Label customers_movieTitle;

    @FXML
    private TextField customers_search;

    @FXML
    private Label customers_ticketNumber;

    @FXML
    private Label customers_timeChecked;

    @FXML
    private Label dashboard_availableMovies;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_totalEarnToday;

    @FXML
    private Label dashboard_totalSoldTicket;

    @FXML
    private Button editScreening_btn;

    @FXML
    private TableColumn<MoviesData, String> editScreening_col_current;

    @FXML
    private TableColumn<MoviesData, String> editScreening_col_duration;

    @FXML
    private TableColumn<MoviesData, String> editScreening_col_genre;

    @FXML
    private TableColumn<MoviesData, String> editScreening_col_movieTitle;

    @FXML
    private ComboBox<?> editScreening_current;

    @FXML
    private AnchorPane editScreening_form;

    @FXML
    private ImageView editScreening_imageView;

    @FXML
    private TextField editScreening_search;

    @FXML
    private TableView<MoviesData> addScreening_tableView;

    @FXML
    private Label editScreening_title;

    @FXML
    private FontAwesomeIcon signout;

    @FXML
    private Label username;

    private Image image;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private SpinnerValueFactory<Integer> spinner1;
    private SpinnerValueFactory<Integer> spinner2;

    private float price1 = 0;
    private float price2 = 0;
    private float total = 0;
    private int qty1 = 0;
    private int qty2 = 0;

    private int totalMovies;
    private double totalIncome;

    // <editor-fold  desc="dashboard_form">
    public void totalAvailableMovies() {
        String sql = "SELECT COUNT(id) FROM movie WHERE current = 'Showing'";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                totalMovies = result.getInt("count(id)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displaytotalAvailableMovies() {
        totalAvailableMovies();
        dashboard_availableMovies.setText(String.valueOf(totalMovies));
    }

    public void totalIncomeToday() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM customer WHERE date ='"
                + String.valueOf(sqlDate) + "'";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                totalIncome = result.getDouble("SUM(total)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayTotalIncomeToday() {
        totalIncomeToday();
        dashboard_totalEarnToday.setText(String.valueOf(totalIncome));
    }

    private int soldTicket;

    public void countTicket() {
        String sql = "SELECT count(id) FROM customer";
        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                soldTicket = result.getInt("count(id)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayTotalSoldTicket() {
        countTicket();
        dashboard_totalSoldTicket.setText(String.valueOf(soldTicket));
    }
// </editor-fold>

    // <editor-fold  desc="customers_form">
    public ObservableList<CustomerData> customerList() {
        ObservableList<CustomerData> customerL = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer";
        connect = Database.connectDb();

        try {
            CustomerData customerD;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                customerD = new CustomerData(result.getInt("id"),
                        result.getString("type"),
                        result.getString("movieTitle"),
                        result.getInt("quantity"),
                        result.getDouble("total"),
                        result.getDate("date"),
                        result.getTime("time"));
                customerL.add(customerD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerL;
    }

    private ObservableList<CustomerData> custList;
    private FilteredList<CustomerData> filteredData;

    public void showCustomerList() {
        custList = customerList();

        filteredData = new FilteredList<>(custList, e -> true);

        customers_col_ticketNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
        customers_col_movieTicket.setCellValueFactory(new PropertyValueFactory<>("title"));
        customers_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customers_col_time.setCellValueFactory(new PropertyValueFactory<>("time"));

        SortedList<CustomerData> sort = new SortedList<>(filteredData);
        sort.comparatorProperty().bind(customer_tableView.comparatorProperty());
        customer_tableView.setItems(sort);

        customers_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                return customer.getId().toString().toLowerCase().contains(searchKey)
                        || customer.getTitle().toLowerCase().contains(searchKey)
                        || customer.getDate().toString().toLowerCase().contains(searchKey);
            });
        });

        customer_tableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> selectCustomerList());
    }

    public void selectCustomerList() {
        CustomerData custD = customer_tableView.getSelectionModel().getSelectedItem();
        int num = customer_tableView.getSelectionModel().getSelectedIndex();

        if (num < 0) {
            return;
        }

        customers_ticketNumber.setText(String.valueOf(custD.getId()));
        customers_movieTitle.setText(custD.getTitle());
        customers_dateChecked.setText(String.valueOf(custD.getDate()));
        customers_timeChecked.setText(String.valueOf(custD.getTime()));
    }

    public void deleteCustomer() {
        String sql = "DELETE FROM customer WHERE id = '" + customers_ticketNumber.getText() + "'";

        connect = Database.connectDb();

        try {
            Alert alert;

            prepare = connect.prepareStatement(sql);
            if (customers_ticketNumber.getText().isEmpty()
                    || customers_movieTitle.getText().isEmpty()
                    || customers_dateChecked.getText().isEmpty()
                    || customers_timeChecked.getText().isEmpty()) {

                Helper.showAlert(AlertType.ERROR, "Error Message", null, "Please select the customer first");
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText("Are you sure you want to delete "
                        + customers_movieTitle.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get() == ButtonType.OK) {
                    prepare.executeUpdate(sql);

                    showCustomerList();
                    clearCustomer();
                    Helper.showAlert(AlertType.INFORMATION, "Information Message", "Successfully removed!", null);
                } else {
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

            Helper.showAlert(AlertType.ERROR, "Database Error", "An error occurred while deleting customer.", e.getMessage());
        }
    }

    public void clearCustomer() {
        customers_ticketNumber.setText("");
        customers_movieTitle.setText("");
        customers_dateChecked.setText("");
        customers_timeChecked.setText("");
    }
    // </editor-fold>

    // <editor-fold  desc="availableMovies_form">
    public void receipt() {
        connect = Database.connectDb();
        String FILE_NAME = "D:/Code/Java/Team/Report.pdf";

        try {
            //Create PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));

            document.open();

            //Create header
            Font f1 = new Font(Font.FontFamily.TIMES_ROMAN, 25, Font.BOLD, new BaseColor(0, 119, 0));

            Paragraph paragraph = new Paragraph("Cinema", f1);
            paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

            document.add(paragraph);
            document.add(new Paragraph("\n"));

            //Create table
            Font f2 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Type", f2));
            cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Movie Title", f2));
            cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(new Paragraph("Quantity", f2));
            cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table.addCell(cell3);

            PdfPCell cell4 = new PdfPCell(new Paragraph("Total(VND)", f2));
            cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table.addCell(cell4);

            //SQL
            String sql = "SELECT type, movieTitle, quantity, total "
                    + "FROM customer "
                    + "WHERE id = (SELECT MAX(id) FROM customer);";

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                table.addCell(result.getString("type"));
                table.addCell(result.getString("movieTitle"));
                table.addCell(result.getString("quantity"));
                table.addCell(result.getString("total"));
            }

            document.add(table);

            //Footer
            Font f3 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, new BaseColor(0, 0, 0));

            Paragraph foot = new Paragraph("Total Payment: " + result.getString("total") + " VND", f3);
            paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);

            document.add(foot);

            // Đóng tài liệu
            document.close();

            System.out.println("PDF Created!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int num;
    private int qty;

    public void buy() {
        String sql = "INSERT INTO customer (type,movieTitle,quantity,total,date,time) VALUES(?,?,?,?,?,?)";

        connect = Database.connectDb();

        String type = "";

        if (price1 > 0 && price2 == 0) {
            type = "Special Class";
        } else if (price2 > 0 && price1 == 0) {
            type = "Normal Class";
        } else if (price2 > 0 && price1 > 0) {
            type = "Special & Normal Class";
        }

        Date date = new Date();
        java.sql.Date setDate = new java.sql.Date(date.getTime());

        try {
            LocalTime localTime = LocalTime.now();

            Time time = Time.valueOf(localTime);

            qty = qty1 + qty2;

            prepare = connect.prepareStatement(sql);

            prepare.setString(1, type);
            prepare.setString(2, availableMovies_title.getText());
            prepare.setInt(3, qty);
            prepare.setDouble(4, total);
            prepare.setDate(5, setDate);
            prepare.setTime(6, time);

            // check if the user select movie before click buy
            if (availableMovies_imageView.getImage() == null
                    || availableMovies_title.getText().isEmpty()) {
                Helper.showAlert(AlertType.ERROR, "Error Message", null, "Please select the movie first");
            } // check if the quantity of Special Class or Normal Class is zero
            else if (price1 == 0 && price2 == 0) {
                Helper.showAlert(AlertType.ERROR, "Error Message", null, "Please indicate the quantity of ticket you want to purchase.");
            } else {
                prepare.executeUpdate();

                Helper.showAlert(AlertType.INFORMATION, "Information Message", null, "Successfully purchase!");
                // to clear
                clearPurchaseTicketInfo();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Helper.showAlert(AlertType.ERROR, "Error Message", "An error occurred while buying.", e.getMessage());
        }
    }

    public void clearPurchaseTicketInfo() {
        spinner1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        spinner2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);

        availableMovies_specialClass_quantity.setValueFactory(spinner1);
        availableMovies_normalClass_quantity.setValueFactory(spinner2);

        availableMovies_specialClass_price.setText("0.0 VND");
        availableMovies_normalClass_price.setText("0.0 VND");
        availableMovies_total.setText("0.0 VND");

        availableMovies_imageView.setImage(null);
        availableMovies_title.setText("");
        availableMovies_movieTitle.setText("");
        availableMovies_genre.setText("");
        availableMovies_date.setText("");
    }

    public void showSpinnerValue() {
        spinner1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        spinner2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);

        availableMovies_specialClass_quantity.setValueFactory(spinner1);
        availableMovies_normalClass_quantity.setValueFactory(spinner2);
    }

    public void getSpinnerValue() {
        qty1 = availableMovies_specialClass_quantity.getValue();
        qty2 = availableMovies_normalClass_quantity.getValue();

        price1 = (qty1 * 70000); //70 000 -> Special Class
        price2 = (qty2 * 50000); //50 000 -> Normal Class

        total = (price1 + price2);

        availableMovies_specialClass_price.setText(String.valueOf(price1) + " VND");
        availableMovies_normalClass_price.setText(String.valueOf(price2) + " VND");

        availableMovies_total.setText(String.valueOf(total) + " VND");
    }

    public ObservableList<MoviesData> availableMoviesList() {
        ObservableList<MoviesData> listAvMovies = FXCollections.observableArrayList();

        String sql = "SELECT * FROM movie WHERE current = 'Showing'";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            MoviesData movD;

            while (result.next()) {
                movD = new MoviesData(result.getInt("id"),
                        result.getString("movieTitle"),
                        result.getString("genre"),
                        result.getString("duration"),
                        result.getString("image"),
                        result.getDate("date"),
                        result.getString("current"));

                listAvMovies.add(movD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAvMovies;
    }

    private ObservableList<MoviesData> availableMoviesList;

    public void showAvailableMovies() {
        availableMoviesList = availableMoviesList();

        availableMovies_col_movieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        availableMovies_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        availableMovies_col_showingDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        availableMovies_tableView.setItems(availableMoviesList);

        availableMovies_tableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> selectAvailableMovies());
    }

    public void selectAvailableMovies() {
        MoviesData movD = availableMovies_tableView.getSelectionModel().getSelectedItem();
        int num = availableMovies_tableView.getSelectionModel().getSelectedIndex();

        if (num < 0) {
            return;
        }

        availableMovies_movieTitle.setText(movD.getTitle());
        availableMovies_genre.setText(movD.getGenre());
        availableMovies_date.setText(String.valueOf(movD.getDate()));

        GetData.path = movD.getImage();
        GetData.title = movD.getTitle();
    }

    public void selectMovie() {
        if (availableMovies_movieTitle.getText().isEmpty()
                || availableMovies_genre.getText().isEmpty()
                || availableMovies_date.getText().isEmpty()) {
            Helper.showAlert(AlertType.ERROR, "Error Message", null, "Please select the movie first");
        } else {
            String uri = "file:///" + GetData.path.replace("\\", "/");

            image = new Image(uri, 136, 180, false, true);
            availableMovies_imageView.setImage(image);

            availableMovies_title.setText(GetData.title);
        }
    }
    // </editor-fold>

    // <editor-fold  desc="editScreening_form">
    public String[] currentList = {"Showing", "End Showing"};

    public void comboBox() {
        ObservableList listCurrent = FXCollections.observableArrayList(currentList);
        editScreening_current.setItems(listCurrent);
    }

    public void updateEditScreening() {
        String sql = "UPDATE movie SET current = '"
                + editScreening_current.getSelectionModel().getSelectedItem()
                + "' WHERE movieTitle = '" + editScreening_title.getText() + "'";

        connect = Database.connectDb();

        try {
            statement = connect.createStatement();

            if (editScreening_title.getText().isEmpty()
                    || editScreening_imageView.getImage() == null
                    || editScreening_current.getSelectionModel().isEmpty()) {
                Helper.showAlert(AlertType.ERROR, "Error Message", null, "Please select the movie first");
            } else {
                statement.executeUpdate(sql);
                Helper.showAlert(AlertType.INFORMATION, "Information Message", null, "Successfully Update!");

                showEditScreening();
                clearEditScreening();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Helper.showAlert(AlertType.ERROR, "Database Error", "An error occurred while updating.", e.getMessage());
        }
    }

    public void clearEditScreening() {
        editScreening_title.setText("");
        editScreening_imageView.setImage(null);
        editScreening_current.setValue(null);
    }

    public void selectEditScreening() throws SQLException {
        MoviesData movD = addScreening_tableView.getSelectionModel().getSelectedItem();
        int num = addScreening_tableView.getSelectionModel().getSelectedIndex();

        if (num < 0) {
            return;
        }

        String uri = "file:///" + movD.getImage().replace("\\", "/");

        image = new Image(uri, 138, 183, false, true);
        editScreening_imageView.setImage(image);

        editScreening_title.setText(movD.getTitle());
    }

    public ObservableList<MoviesData> editScreeningList() {
        ObservableList<MoviesData> editSList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM movie";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            MoviesData movD;

            while (result.next()) {
                movD = new MoviesData(result.getInt("id"),
                        result.getString("movieTitle"),
                        result.getString("genre"),
                        result.getString("duration"),
                        result.getString("image"),
                        result.getDate("date"),
                        result.getString("current"));

                editSList.add(movD);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return editSList;
    }

    private ObservableList<MoviesData> editScreeningL;

    public void showEditScreening() {
        editScreeningL = editScreeningList();

        FilteredList<MoviesData> filter = new FilteredList(editScreeningL, e -> true);

        editScreening_col_movieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        editScreening_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        editScreening_col_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        editScreening_col_current.setCellValueFactory(new PropertyValueFactory<>("current"));

        SortedList<MoviesData> sortData = new SortedList<>(filter);
        sortData.comparatorProperty().bind(addScreening_tableView.comparatorProperty());
        addScreening_tableView.setItems(sortData);

        editScreening_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(predicateMoviesData -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateMoviesData.getTitle().toLowerCase().contains(searchKey)
                        || predicateMoviesData.getGenre().toLowerCase().contains(searchKey)
                        || predicateMoviesData.getDuration().toLowerCase().contains(searchKey)
                        || predicateMoviesData.getCurrent().toLowerCase().contains(searchKey)) {
                    return true;
                }
                return false;
            });
        });

        addScreening_tableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        selectEditScreening();
                    } catch (SQLException ex) {
                        Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

    }
    // </editor-fold>

    // <editor-fold  desc="addMovies_form">
    public void importImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new ExtensionFilter("Image File", "*png", "*jpg"));

        Stage stage = (Stage) addMovies_form.getScene().getWindow();
        File file = open.showOpenDialog(stage);

        if (file != null) {
            image = new Image(file.toURI().toString(), 120, 150, false, true);
            addMovies_imageView.setImage(image);

            GetData.path = file.getAbsolutePath();
        }
    }

    public void insertAddMovies() {
        String sql1 = "SELECT * FROM movie WHERE movieTitle = '" + addMovies_movieTitle.getText() + "'";
        connect = Database.connectDb();

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql1);

            if (result.next()) {
                Helper.showAlert(AlertType.ERROR, "Error Message", null, addMovies_movieTitle.getText() + " was already exist!");
            } else {
                if (addMovies_movieTitle.getText().isEmpty()
                        || addMovies_genre.getText().isEmpty()
                        || addMovies_duration.getText().isEmpty()
                        || addMovies_imageView.getImage() == null
                        || addMovies_date.getValue() == null) {
                    Helper.showAlert(AlertType.ERROR, "Error Message", null, "Please fill all blank fields!");
                } else {
                    String sql
                            = "INSERT INTO movie (movieTitle,genre,duration,image,date,current) VALUES (?,?,?,?,?,?)";

                    String uri = GetData.path.replace("\\", "\\\\");

                    prepare = connect.prepareStatement(sql);

                    prepare.setString(1, addMovies_movieTitle.getText());
                    prepare.setString(2, addMovies_genre.getText());
                    prepare.setString(3, addMovies_duration.getText());
                    prepare.setString(4, uri);
                    prepare.setString(5, String.valueOf(addMovies_date.getValue()));
                    prepare.setString(6, "Showing");

                    prepare.executeUpdate();

                    Helper.showAlert(AlertType.INFORMATION, "Information Message", null, "Successfully add new movie!");

                    clearAddMoviesList();
                    showAddMoviesList();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Helper.showAlert(AlertType.ERROR, "Database Error", "An error occurred while inserting.", e.getMessage());
        }
    }

    public void updateAddMovies() {
        String uri = GetData.path.replace("\\", "\\\\");

        String sql = "UPDATE movie SET movieTitle = '" + addMovies_movieTitle.getText()
                + "', genre = '" + addMovies_genre.getText()
                + "', duration = '" + addMovies_duration.getText()
                + "', image = '" + uri
                + "', date = '" + addMovies_date.getValue()
                + "' WHERE id = '" + String.valueOf(GetData.movieId) + "'";

        connect = Database.connectDb();
        try {
            statement = connect.createStatement();

            if (addMovies_movieTitle.getText().isEmpty()
                    || addMovies_genre.getText().isEmpty()
                    || addMovies_duration.getText().isEmpty()
                    || addMovies_imageView.getImage() == null
                    || addMovies_date.getValue() == null) {
                Helper.showAlert(AlertType.ERROR, "Error Message", null, "Please select the movie first");
            } else {
                statement.executeUpdate(sql);
                Helper.showAlert(AlertType.INFORMATION, "Information Message", null, "Successfully update " + addMovies_movieTitle.getText());

                showAddMoviesList();
                clearAddMoviesList();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Helper.showAlert(AlertType.ERROR, "Database Error", "An error occurred while updating.", e.getMessage());
        }
    }

    public void deleteAddMovies() {
        String sql = "DELETE FROM movie WHERE movieTitle = '"
                + addMovies_movieTitle.getText() + "'";

        connect = Database.connectDb();

        try {
            statement = connect.createStatement();

            Alert alert;

            if (addMovies_movieTitle.getText().isEmpty()
                    || addMovies_genre.getText().isEmpty()
                    || addMovies_duration.getText().isEmpty()
                    || addMovies_date.getValue() == null) {
                Helper.showAlert(AlertType.ERROR, "Error Message", null, "Please select the movie first");
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete "
                        + addMovies_movieTitle.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get() == ButtonType.OK) {
                    statement.executeUpdate(sql);

                    showAddMoviesList();
                    clearAddMoviesList();

                    Helper.showAlert(AlertType.INFORMATION, "Information Message", null, "Successfully delete!");
                } else {
                    return;
                }
            }
        } catch (SQLException e) {
            Helper.showAlert(AlertType.ERROR, "Error Message", null, "Failed to delete the movie: " + e.getMessage());
        }
    }

    public void clearAddMoviesList() {
        addMovies_movieTitle.setText("");
        addMovies_genre.setText("");
        addMovies_duration.setText("");
        addMovies_imageView.setImage(null);
        addMovies_date.setValue(null);
    }

    public ObservableList<MoviesData> addMoviesList() {
        ObservableList<MoviesData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM movie";
        connect = (Connection) Database.connectDb();

        try {
            prepare = (PreparedStatement) connect.prepareStatement(sql);
            result = prepare.executeQuery();

            MoviesData movD;
            while (result.next()) {
                movD = new MoviesData(result.getInt("id"),
                        result.getString("movieTitle"),
                        result.getString("genre"),
                        result.getString("duration"),
                        result.getString("image"),
                        result.getDate("date"),
                        result.getString("current"));
                listData.add(movD);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<MoviesData> listAddMovies;

    public void showAddMoviesList() {
        listAddMovies = addMoviesList();
        FilteredList<MoviesData> filter = new FilteredList<>(listAddMovies, e -> true);

        addMovies_col_movieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        addMovies_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        addMovies_col_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        addMovies_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        SortedList<MoviesData> sortData = new SortedList<>(filter);
        sortData.comparatorProperty().bind(addMovies_tableView.comparatorProperty());
        addMovies_tableView.setItems(sortData);

        addMovies_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(predicateMoviesData -> {

                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }

                String keySearch = newValue.toLowerCase();

                if (predicateMoviesData.getTitle().toLowerCase().contains(keySearch)
                        || predicateMoviesData.getGenre().toLowerCase().contains(keySearch)
                        || predicateMoviesData.getDuration().toLowerCase().contains(keySearch)
                        || predicateMoviesData.getDate().toString().contains(keySearch)) {
                    return true;
                }
                return false;
            });
        });

        addMovies_tableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> selectAddMoviesList());

    }

    public void selectAddMoviesList() {
        MoviesData movD = addMovies_tableView.getSelectionModel().getSelectedItem();
        int num = addMovies_tableView.getSelectionModel().getSelectedIndex();

        if (num < 0) {
            return;
        }

        GetData.path = movD.getImage();

        GetData.movieId = movD.getId();

        addMovies_movieTitle.setText(movD.getTitle());
        addMovies_genre.setText(movD.getGenre());
        addMovies_duration.setText(movD.getDuration());

        String getDate = String.valueOf(movD.getDate());

        String uri = "file:///" + movD.getImage().replace("\\", "/");

        image = new Image(uri, 127, 167, false, true);
        addMovies_imageView.setImage(image);
        addMovies_date.setValue(movD.getDate().toLocalDate());
    }
    // </editor-fold>

    public void logout() throws IOException {
        signout.getScene().getWindow().hide();
        Helper.loadAndShowFXML("/cinemamanagement/view/FXMLDocument.fxml");
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            addMovies_form.setVisible(false);
            availableMovies_form.setVisible(false);
            editScreening_form.setVisible(false);
            customers_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #006600;");
            addMovies_btn.setStyle("-fx-background-color: transparent;");
            availableMovies_btn.setStyle("-fx-background-color: transparent;");
            editScreening_btn.setStyle("-fx-background-color: transparent;");
            customers_btn.setStyle("-fx-background-color: transparent;");

            displayTotalSoldTicket();
            displayTotalIncomeToday();
            displaytotalAvailableMovies();
        } else if (event.getSource() == addMovies_btn) {
            dashboard_form.setVisible(false);
            addMovies_form.setVisible(true);
            availableMovies_form.setVisible(false);
            editScreening_form.setVisible(false);
            customers_form.setVisible(false);

            addMovies_btn.setStyle("-fx-background-color: #006600;");
            dashboard_btn.setStyle("-fx-background-color: transparent;");
            availableMovies_btn.setStyle("-fx-background-color: transparent;");
            editScreening_btn.setStyle("-fx-background-color: transparent;");
            customers_btn.setStyle("-fx-background-color: transparent;");

            showAddMoviesList();
        } else if (event.getSource() == availableMovies_btn) {
            dashboard_form.setVisible(false);
            addMovies_form.setVisible(false);
            availableMovies_form.setVisible(true);
            editScreening_form.setVisible(false);
            customers_form.setVisible(false);

            availableMovies_btn.setStyle("-fx-background-color: #006600;");
            dashboard_btn.setStyle("-fx-background-color: transparent;");
            addMovies_btn.setStyle("-fx-background-color: transparent;");
            editScreening_btn.setStyle("-fx-background-color: transparent;");
            customers_btn.setStyle("-fx-background-color: transparent;");

            showAvailableMovies();
        } else if (event.getSource() == editScreening_btn) {
            dashboard_form.setVisible(false);
            addMovies_form.setVisible(false);
            availableMovies_form.setVisible(false);
            editScreening_form.setVisible(true);
            customers_form.setVisible(false);

            editScreening_btn.setStyle("-fx-background-color: #006600;");
            dashboard_btn.setStyle("-fx-background-color: transparent;");
            addMovies_btn.setStyle("-fx-background-color: transparent;");
            availableMovies_btn.setStyle("-fx-background-color: transparent;");
            customers_btn.setStyle("-fx-background-color: transparent;");

            showEditScreening();
        } else if (event.getSource() == customers_btn) {
            dashboard_form.setVisible(false);
            addMovies_form.setVisible(false);
            availableMovies_form.setVisible(false);
            editScreening_form.setVisible(false);
            customers_form.setVisible(true);

            customers_btn.setStyle("-fx-background-color: #006600;");
            dashboard_btn.setStyle("-fx-background-color: transparent;");
            addMovies_btn.setStyle("-fx-background-color: transparent;");
            availableMovies_btn.setStyle("-fx-background-color: transparent;");
            editScreening_btn.setStyle("-fx-background-color: transparent;");

            showCustomerList();
        }
    }

    public void displayUsername() {
        username.setText(GetData.username);
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) topForm.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();

        showAddMoviesList();

        showEditScreening();

        comboBox();

        showAvailableMovies();

        showSpinnerValue();

        showCustomerList();

        displayTotalSoldTicket();
        displayTotalIncomeToday();
        displaytotalAvailableMovies();
    }
}