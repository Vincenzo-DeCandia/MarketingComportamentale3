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

/**
 * Controller for managing the offer modal in the application.
 * This class handles the creation and deletion of offers,
 * and the functionality of displaying products and offers related to a user.
 */
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

    // FXML elements for the offer table view
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

    // Constructor for the OfferModalController
    public OfferModalController() {}

    /**
     * Initializes the offer modal view.
     * Sets up the combo box, table view, and list view for products.
     */
    @FXML
    private void initialize() {
        initComboBox();  // Initialize the category combo box
        initTableView();  // Initialize the offer table view
        initListView();   // Initialize the product list view
    }

    /**
     * Initializes the category combo box by populating it with categories from the database.
     * Sets up an event listener to refresh the product list when the category changes.
     */
    private void initComboBox() {
        categoryComboBox.getItems().clear();
        MySqlDatabaseFacade databaseFacade = new MySqlDatabaseFacade();

        // Fetch categories from the database and populate the combo box
        List<Map<String, Object>> list = databaseFacade.fetchData("SELECT * FROM category");
        if (!list.isEmpty()) {
            for (Map<String, Object> map : list) {
                categoryComboBox.getItems().add(map.get("category_name").toString());
            }
        }

        // Refresh product list when a category is selected
        categoryComboBox.setOnAction(event -> {
            refreshProductListView();
        });
    }

    /**
     * Initializes the offer table view by setting up the cell value factories for each column.
     * Configures the table view to allow single selection mode.
     */
    private void initTableView() {
        offerObservableList = FXCollections.observableArrayList();
        offerTableView.setItems(offerObservableList);

        // Setting cell value factories for each table column
        offerNameColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getNameOffer()));
        offerDescriptionColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getDescription()));
        offerDiscountColumn.setCellValueFactory(celldata -> new SimpleFloatProperty(celldata.getValue().getDiscount()).asObject());
        offerStartDateColumn.setCellValueFactory(celldata -> new SimpleObjectProperty<>(celldata.getValue().getStartDate()));
        offerEndDateColumn.setCellValueFactory(celldata -> new SimpleObjectProperty<>(celldata.getValue().getEndDate()));

        // Allow single selection mode in the offer table
        offerTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Handles the click event of the delete offer button.
     * Deletes the selected offer and refreshes the offer table view.
     */
    @FXML
    protected void handleDeleteOfferButton(ActionEvent event) {
        IRepository<Offer, Integer> offerRepository = new OfferRepository();
        Offer selectedOffer = offerTableView.getSelectionModel().getSelectedItem();

        if (selectedOffer != null) {
            offerRepository.delete(selectedOffer);  // Delete the selected offer
            offerObservableList.remove(selectedOffer);  // Remove the offer from the observable list
            offerTableView.getSelectionModel().clearSelection();  // Clear the selection
            refreshOfferTableView();  // Refresh the offer table view
        }
    }

    /**
     * Handles the click event of the create offer button.
     * Creates a new offer using the provided details and refreshes the offer table view.
     */
    @FXML
    protected void handleCreateOfferButton(ActionEvent event) {
        System.out.println("Create Offer");
        System.out.println("User ID: " + baseUser.getIdUser());

        IOfferService offerService = new OfferService();
        try {
            // Create a new offer and associate it with the selected product
            offerService.createAndNotifyOffer(
                    offerName.getText(),
                    offerDescription.getText(),
                    Float.parseFloat(offerDiscount.getText()),
                    Date.valueOf(offerStartDate.getValue()),
                    Date.valueOf(offerEndDate.getValue()),
                    baseUser.getIdUser(),
                    productListView.getSelectionModel().getSelectedItem().getProductId()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Clear product selection and refresh the offer table view
        productListView.getSelectionModel().clearSelection();
        refreshOfferTableView();
    }

    /**
     * Initializes the product list view by setting up the cell factory for displaying products.
     * Configures the list view to allow single selection mode.
     */
    private void initListView() {
        productObservableList = FXCollections.observableArrayList();
        productListView.setItems(productObservableList);

        // Set up the product list cell factory
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

        // Allow single selection mode in the product list
        productListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Refreshes the offer table view by fetching offers associated with the current user.
     */
    private void refreshOfferTableView() {
        offerObservableList.clear();
        OfferRepository offerRepository = new OfferRepository();

        // Fetch offers for the current user
        List<Offer> offers = offerRepository.findByUserId(baseUser.getIdUser());
        if (offers != null && !offers.isEmpty()) {
            offerObservableList.addAll(offers);  // Add fetched offers to the observable list
        }
    }

    /**
     * Sets the base user for the current session and refreshes the offer table view.
     *
     * @param user The base user to set.
     */
    public void setBaseUser(BaseUser user) {
        baseUser = user;
        refreshOfferTableView();
    }

    /**
     * Sets the current offer to be edited or displayed.
     *
     * @param offer The offer to set.
     */
    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    /**
     * Gets the current offer being edited or displayed.
     *
     * @return The current offer.
     */
    public Offer getOffer() {
        return offer;
    }

    /**
     * Gets the base user for the current session.
     *
     * @return The base user.
     */
    public BaseUser getBaseUser() {
        return baseUser;
    }

    /**
     * Closes the offer modal when the close button is clicked.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    protected void closeModal(ActionEvent event) {
        ((Stage) offerName.getScene().getWindow()).close();  // Close the modal window
    }

    /**
     * Sets the selected category and refreshes the product list view based on that category.
     *
     * @param category The category to set.
     */
    public void setCategory(String category) {
        this.category = category;
        refreshProductListView();
    }

    /**
     * Gets the selected category for filtering products.
     *
     * @return The current category.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Refreshes the product list view by fetching products from the selected category.
     */
    private void refreshProductListView() {
        productObservableList.clear();
        ProductRepository repository = new ProductRepository();

        // Fetch products by category from the repository
        List<Product> productList = repository.searchByCategory(categoryComboBox.getValue());
        if (productList == null || productList.isEmpty()) {
            return;
        }

        // Add products to the observable list
        productObservableList.addAll(productList);
    }
}
