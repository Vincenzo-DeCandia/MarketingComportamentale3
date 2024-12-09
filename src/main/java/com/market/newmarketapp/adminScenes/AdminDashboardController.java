package com.market.newmarketapp.adminScenes;

import com.market.database.facade.IDatabase;
import com.market.database.facade.MySqlDatabaseFacade;
import com.market.entity.Order;
import com.market.newmarketapp.managerscene.Controller;
import com.market.repository.IRepository;
import com.market.repository.concreteRepository.OrderRepository;
import com.market.user.singletonloggeduser.LoggedUserSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller class for the admin dashboard view.
 * This class manages the dashboard features, including displaying a line chart
 * for monthly earnings, and navigating to different sections of the admin panel.
 */
public class AdminDashboardController extends Controller {

    private final IDatabase mySqlDatabaseFacade = new MySqlDatabaseFacade();  // Database facade for accessing MySQL data

    // FXML elements for the admin dashboard UI
    @FXML
    private VBox dynamicContent;  // Placeholder for dynamic content to be added

    @FXML
    private Text panelText;  // Text element used for displaying text on the dashboard

    @FXML
    private LineChart<String, Float> moneyLineChart;  // Line chart to display earnings per month

    /**
     * Initializes the dashboard by setting up the line chart and its axes.
     * It also populates the chart with data for the current year.
     */
    @FXML
    public void initialize() {
        // Set labels for the axes of the chart
        moneyLineChart.getXAxis().setLabel("Mese");  // X-axis: Month
        moneyLineChart.getYAxis().setLabel("Guadagno");  // Y-axis: Earnings
        moneyLineChart.setAnimated(false);  // Disable chart animation for better performance
        moneyLineChart.setTitle("Analisi guadagno");  // Title of the chart
        moneyLineChart.setLegendVisible(false);  // Hide the legend

        // Initialize the line chart with data
        initLineChart();
    }

    /**
     * Populates the line chart with data for monthly earnings.
     * It fetches all orders from the repository, calculates the total earnings
     * for each month in the current year, and updates the chart.
     */
    private void initLineChart() {
        // Create a new series for the line chart
        XYChart.Series<String, Float> series = new XYChart.Series<>();

        // Create a repository instance to fetch orders
        IRepository<Order, Integer> orderRepository = new OrderRepository();
        List<Order> orderList = orderRepository.findAll();  // Fetch all orders

        // Initialize the chart data for each month with a value of 0.0
        series.getData().add(new XYChart.Data<>("Gennaio", 0.0f));
        series.getData().add(new XYChart.Data<>("Febbraio", 0.0f));
        series.getData().add(new XYChart.Data<>("Marzo", 0.0f));
        series.getData().add(new XYChart.Data<>("Aprile", 0.0f));
        series.getData().add(new XYChart.Data<>("Maggio", 0.0f));
        series.getData().add(new XYChart.Data<>("Giugno", 0.0f));
        series.getData().add(new XYChart.Data<>("Luglio", 0.0f));
        series.getData().add(new XYChart.Data<>("Agosto", 0.0f));
        series.getData().add(new XYChart.Data<>("Settembre", 0.0f));
        series.getData().add(new XYChart.Data<>("Ottobre", 0.0f));
        series.getData().add(new XYChart.Data<>("Novembre", 0.0f));
        series.getData().add(new XYChart.Data<>("Dicembre", 0.0f));

        // Iterate over all orders to calculate total earnings per month for the current year
        if (orderList != null && !orderList.isEmpty()) {
            for (Order order : orderList) {
                // Only consider orders from the current year
                if (order.getDateOrder().toLocalDate().getYear() == LocalDate.now().getYear()) {
                    LocalDate localDate = order.getDateOrder().toLocalDate();
                    // Get the current month's earnings and add the order's total price
                    Float num = series.getData().get(localDate.getMonthValue() - 1).getYValue();
                    num += order.getTotalPrice();
                    series.getData().get(localDate.getMonthValue() - 1).setYValue(num);  // Update the data point
                }
            }
        }

        // Add the series to the chart
        moneyLineChart.getData().add(series);
    }

    /**
     * Navigates to the profile page when the "Profile" button is clicked.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    protected void goToProfile(ActionEvent event) {
        this.getSceneManager().switchScene("profile");  // Switch to the profile scene
    }

    /**
     * Navigates to the product management page when the "Product Management" button is clicked.
     */
    @FXML
    protected void goToProductManagement() {
        this.getSceneManager().switchScene("productManagement");  // Switch to the product management scene
    }

    /**
     * Navigates to the market analysis page when the "Market Analysis" button is clicked.
     */
    @FXML
    protected void goToUserMarketAnalysis() {
        this.getSceneManager().switchScene("marketAnalysis");  // Switch to the market analysis scene
    }

    /**
     * Handles the logout action when the "Logout" button is clicked.
     * Displays a confirmation dialog and logs out the user if confirmed.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    @FXML
    protected void onLogoutButton(ActionEvent actionEvent) {
        // Show a confirmation dialog for logout
        if (showConfirmationDialog("Logout", "Sei sicuro di voler uscire?")) {
            // Log out the current user and exit the application
            LoggedUserSingleton.getInstance().getLoggedUser().logout();
            LoggedUserSingleton.getInstance().setLoggedUser(null);  // Clear the logged-in user
            System.exit(0);  // Exit the application
        }
    }

    /**
     * Displays a confirmation dialog with a specified title and message.
     *
     * @param title   The title of the confirmation dialog.
     * @param message The message to be displayed in the dialog.
     * @return true if the user confirms, false otherwise.
     */
    private boolean showConfirmationDialog(String title, String message) {
        // Create a confirmation dialog with YES and NO options
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        // Return true if the user presses "YES", false if "NO"
        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }
}
