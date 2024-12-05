package com.market.newmarketapp.userScenes;

import com.market.entity.Order;
import com.market.newmarketapp.managerscene.Controller;
import com.market.repository.IOrderRepository;
import com.market.repository.concreteRepository.OrderRepository;
import com.market.user.singletonloggeduser.LoggedUserSingleton;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Date;
import java.util.List;

public class OrderListController extends Controller {

    @FXML
    private TableView orderTableView;

    @FXML
    private TableColumn<Order, Integer> orderIdColumn;

    @FXML
    private TableColumn<Order, Date> orderDateColumn;

    @FXML
    private TableColumn<Order, Character> orderStatusColumn;

    @FXML
    private TableColumn<Order, String> orderPaymentMethodColumn;

    @FXML
    private TableColumn<Order, Float> orderTotalColumn;

    private ObservableList<Order> orders;

    @FXML
    private void initialize() {
        orders = FXCollections.observableArrayList();
        orderTableView.setItems(orders);
        orderIdColumn.setCellValueFactory(celldata -> new SimpleIntegerProperty(celldata.getValue().getOrderId()).asObject());
        orderDateColumn.setCellValueFactory(celldata -> new SimpleObjectProperty<>(celldata.getValue().getDateOrder()));
        orderStatusColumn.setCellValueFactory(celldata -> new SimpleObjectProperty<>(celldata.getValue().getStatus()));
        orderPaymentMethodColumn.setCellValueFactory(celldata -> new SimpleObjectProperty<>(celldata.getValue().getPaymentMethod()));
        orderTotalColumn.setCellValueFactory(celldata -> new SimpleFloatProperty(celldata.getValue().getTotalPrice()).asObject());
        refresh();
    }

    public void refresh() {
        IOrderRepository<Order> orderRepository = new OrderRepository();
        List<Order> list = orderRepository.findByUserId(LoggedUserSingleton.getInstance().getLoggedUser().getIdUser());
        orders.clear();
        if (list != null && !list.isEmpty()) {
            orders.addAll(list);
        }
    }

    @FXML
    public void onBackButton(ActionEvent actionEvent) {
        this.getSceneManager().switchScene("userHome");
    }


}
