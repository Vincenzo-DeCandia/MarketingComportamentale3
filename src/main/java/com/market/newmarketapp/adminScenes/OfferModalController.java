package com.market.newmarketapp.adminScenes;

import com.market.entity.Offer;
import com.market.entity.Product;
import com.market.database.facade.MySqlDatabaseFacade;
import com.market.repository.IRepository;
import com.market.repository.concreteRepository.OfferRepository;
import com.market.repository.concreteRepository.ProductRepository;
import com.market.service.OfferService;
import com.market.user.BaseUser;
import com.market.service.IOfferService;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class OfferModalController {
    private Offer offer;
    private BaseUser baseUser;
    private String category;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private TextField offerName;

    @FXML
    private TextArea offerDescription;

    @FXML
    private TextField offerDiscount;

    @FXML
    private DatePicker offerStartDate;

    @FXML
    private DatePicker offerEndDate;

    @FXML
    private ListView<Product> productListView;

    private ObservableList<Product> productObservableList;

    private ObservableList<Offer> offerObservableList;

    @FXML
    private TableView<Offer> offerTableView;

    @FXML
    private TableColumn<Offer, String> offerNameColumn;

    @FXML
    private TableColumn<Offer, String> offerDescriptionColumn;

    @FXML
    private TableColumn<Offer, Float> offerDiscountColumn;

    @FXML
    private TableColumn<Offer, Date> offerStartDateColumn;

    @FXML
    private TableColumn<Offer, Date> offerEndDateColumn;

    @FXML
    private Button createOffer;

    public OfferModalController() {}

    @FXML
    private void initialize() {
        initComboBox();
        initTableView();
        initListView();
    }

    private void initComboBox() {
        categoryComboBox.getItems().clear();
        MySqlDatabaseFacade databaseFacade = new MySqlDatabaseFacade();
        List<Map<String, Object>> list = databaseFacade.fetchData("SELECT * FROM category");
        if (!list.isEmpty()) {
            for (Map<String, Object> map : list) {
                categoryComboBox.getItems().add(map.get("category_name").toString());
            }
        }
        categoryComboBox.setOnAction(event -> {
            refreshProductListView();
        });
    }

    private void initTableView() {
        offerObservableList = FXCollections.observableArrayList();
        offerTableView.setItems(offerObservableList);

        offerNameColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getNameOffer()));
        offerDescriptionColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getDescription()));
        offerDiscountColumn.setCellValueFactory(celldata -> new SimpleFloatProperty(celldata.getValue().getDiscount()).asObject());
        offerStartDateColumn.setCellValueFactory(celldata -> new SimpleObjectProperty<>(celldata.getValue().getStartDate()));
        offerEndDateColumn.setCellValueFactory(celldata -> new SimpleObjectProperty<>(celldata.getValue().getEndDate()));

        offerTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    protected void handleDeleteOfferButton(ActionEvent event) {
        IRepository<Offer, Integer> offerRepository = new OfferRepository();
        offerRepository.delete(offerTableView.getSelectionModel().getSelectedItem());
        offerObservableList.remove(offerTableView.getSelectionModel().getSelectedItem());
        offerTableView.getSelectionModel().clearSelection();
        refreshOfferTableView();
    }

    @FXML
    protected void handleCreateOfferButton(ActionEvent event) {
        System.out.println("Create Offer");
        System.out.println("Id: " + baseUser.getIdUser());
        IOfferService offerService = new OfferService();
        offerService.createAndNotifyOffer(offerName.getText(), offerDescription.getText(), Float.parseFloat(offerDiscount.getText()), Date.valueOf(offerStartDate.getValue()), Date.valueOf(offerEndDate.getValue()), baseUser.getIdUser(), productListView.getSelectionModel().getSelectedItem().getProductId());
        productListView.getSelectionModel().clearSelection();
        refreshOfferTableView();
    }

    private void initListView() {
        productObservableList = FXCollections.observableArrayList();
        productListView.setItems(productObservableList);

        productListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                } else {
                    setText(product.getProductId() + " - " + product.getProductCode() + " - " + product.getProductName() + " - " + product.getProductPrice() + " €");
                }
            }
        });
        productListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void refreshOfferTableView() {
        offerObservableList.clear();
        OfferRepository offerRepository = new OfferRepository();
        List<Offer> offers =  offerRepository.findByUserId(baseUser.getIdUser());
        if (offers != null && !offers.isEmpty()) {
            offerObservableList.addAll(offers);
        }
    }

    public void setBaseUser(BaseUser user) {
        baseUser = user;
        refreshOfferTableView();
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Offer getOffer() {
        return offer;
    }

    public BaseUser getBaseUser() {
        return baseUser;
    }

    @FXML
    protected void closeModal(ActionEvent event) {
        ((Stage)offerName.getScene().getWindow()).close();
    }

    public void setCategory(String category) {
        this.category = category;
        refreshProductListView();
    }

    public String getCategory() {
        return category;
    }

    private void refreshProductListView() {
        productObservableList.clear();
        ProductRepository repository = new ProductRepository();
        List<Product> productList = repository.searchByCategory(categoryComboBox.getValue());
        if (productList == null || productList.isEmpty()) {
            return;
        }
        productObservableList.addAll(productList);
    }

}
