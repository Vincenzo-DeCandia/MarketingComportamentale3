package com.market.newmarketapp.adminScenes;

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

public class AdminDashboardController extends Controller {

    private final MySqlDatabaseFacade mySqlDatabaseFacade = new MySqlDatabaseFacade();

    @FXML
    private VBox dynamicContent;

    @FXML
    private Text panelText;

    @FXML
    private LineChart<String, Float> moneyLineChart;

    @FXML
    public void initialize() {
        moneyLineChart.getXAxis().setLabel("Mese");
        moneyLineChart.getYAxis().setLabel("Guadagno");
        moneyLineChart.setAnimated(false);
        moneyLineChart.setTitle("Analisi guadagno");
        moneyLineChart.setLegendVisible(false);
        initLineChart();
    }

    private void initLineChart() {
        XYChart.Series<String, Float> series = new XYChart.Series<>();

        IRepository<Order, Integer> orderRepository = new OrderRepository();
        List<Order> orderList = orderRepository.findAll();

        series.getData().add(new XYChart.Data<>("January", 0.0f));
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

        if (orderList != null && !orderList.isEmpty()) {
            for (Order order : orderList) {
                if (order.getDateOrder().toLocalDate().getYear() == LocalDate.now().getYear()) {
                    LocalDate localDate = order.getDateOrder().toLocalDate();
                    Float num = series.getData().get(localDate.getMonthValue() - 1).getYValue();
                    num += order.getTotalPrice();
                    series.getData().get(localDate.getMonthValue() - 1).setYValue(num);
                }
            }
        }

        moneyLineChart.getData().add(series);
    }

    @FXML
    protected void goToProfile(ActionEvent event) {
        this.getSceneManager().switchScene("profile");
    }

    @FXML
    protected void goToProductManagement() {
        this.getSceneManager().switchScene("productManagement");
    }

    @FXML
    protected void goToUserMarketAnalysis() {
        this.getSceneManager().switchScene("marketAnalysis");
    }

    @FXML
    protected void onLogoutButton(ActionEvent actionEvent) {
        if (showConfirmationDialog("Logout", "Sei sicuro di voler uscire?")) {
            LoggedUserSingleton.getInstance().getLoggedUser().logout();
            LoggedUserSingleton.getInstance().setLoggedUser(null);
            System.exit(0);
        }
    }

    private boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }
}
