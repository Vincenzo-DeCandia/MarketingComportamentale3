package com.market.newmarketapp.adminScenes;

import com.market.entity.Product;
import com.market.newmarketapp.MarketApplication;
import com.market.newmarketapp.managerscene.Controller;

import com.market.repository.IRepository;
import com.market.repository.concreteRepository.ProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controller for managing products in the application.
 * This class handles the functionality for inserting, modifying, deleting,
 * and searching for products, as well as displaying product details in the list view.
 */
public class ProductManagementController extends Controller {
    private final ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private ListView<Product> productListView;

    @FXML
    private TextField productIdField;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextArea productDescriptionField;

    // Constructor for the ProductManagementController
    public ProductManagementController() {
        super();
    }

    /**
     * Initializes the product management interface.
     * Populates the product list and sets up the cell factory for displaying products.
     * Configures listeners for product selection and the enabling/disabling of buttons.
     */
    @FXML
    public void initialize() {
        List<Product> listProducts = getProductList();
        productListView.setItems(products);

        // If no products are found, log it and return early
        if (listProducts == null || listProducts.isEmpty()) {
            System.out.println("No products found");
            return;
        }

        // Add the fetched products to the observable list
        products.addAll(listProducts);

        // Set up the cell factory for product list display
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

        // Set up listener for when a product is selected in the list view
        productListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean productSelected = newSelection != null;
            modifyProductButton.setDisable(!productSelected);
            deleteProductButton.setDisable(!productSelected);

            // Clear and update product details when a product is selected
            clearProductDetails();
            if (productSelected) {
                showProductDetails(newSelection);
            }
        });

        // Set the list selection mode to single selection
        productListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private Button insertProductButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private TextField searchByCategoryTextField;

    @FXML
    private TextField searchByNameTextField;

    /**
     * Handles the click of the insert product button.
     * Opens the product modal to insert a new product.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    protected void handleInsertButton(ActionEvent event) {
        openProductModal(0); // Type 0 for insertion (new product)
        productListView.refresh();
    }

    /**
     * Handles the click of the modify product button.
     * Opens the product modal to modify the selected product.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    protected void handleModifyButton(ActionEvent event) {
        openProductModal(1); // Type 1 for modification (existing product)
        productListView.refresh();
    }

    /**
     * Handles the back button action, switching the scene to the admin dashboard.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    protected void handleBackButton(ActionEvent event) {
        this.getSceneManager().switchScene("admin_dashboard");
    }

    /**
     * Handles the search by name functionality.
     * Filters the product list based on the provided name.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    protected void handleSearchByName(ActionEvent event) {
        if (!searchByNameTextField.getText().isEmpty()) {
            ProductRepository productRepository = new ProductRepository();
            System.out.println(searchByNameTextField.getText());
            List<Product> products1 = productRepository.searchByName(searchByNameTextField.getText());
            products.clear();
            if (products1 != null) {
                products.addAll(products1);
            }
        }
        productListView.refresh();
    }

    /**
     * Handles the search by category functionality.
     * Filters the product list based on the provided category.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    protected void handleSearchByCategory(ActionEvent event) {
        if (!searchByCategoryTextField.getText().isEmpty()) {
            ProductRepository productRepository = new ProductRepository();
            System.out.println(searchByCategoryTextField.getText());
            List<Product> products1 = productRepository.searchByCategory(searchByCategoryTextField.getText());
            products.clear();
            if (products1 != null) {
                products.addAll(products1);
            }
        }
        productListView.refresh();
    }

    /**
     * Handles the deletion of the selected product.
     * Removes the product from the repository and the observable list.
     *
     * @param event The action event triggered by the delete button click.
     */
    @FXML
    protected void handleDeleteProduct(ActionEvent event) {
        IRepository<Product, Integer> productRepository = new ProductRepository();
        Product product = productListView.getSelectionModel().getSelectedItem();
        if (product != null) {
            productRepository.delete(product);
        }
        products.remove(product); // Remove the product from the observable list
        clearProductDetails(); // Clear the product details fields
        productListView.getSelectionModel().clearSelection(); // Clear selection
        productListView.refresh(); // Refresh the list view
    }

    /**
     * Fetches the list of all products from the repository.
     *
     * @return A list of all products.
     */
    private List<Product> getProductList() {
        IRepository<Product, Integer> productRepository = new ProductRepository();
        return productRepository.findAll();
    }

    /**
     * Opens the product modal window for inserting or modifying a product.
     *
     * @param type The type (0 for inserting, 1 for modifying).
     */
    @FXML
    protected void openProductModal(int type) {
        try {
            FXMLLoader loader = new FXMLLoader(MarketApplication.class.getResource("modal-product-view.fxml"));
            VBox modalRoot = loader.load();

            ProductModalController productModalController = loader.getController();
            productModalController.setProduct(productListView.getSelectionModel().getSelectedItem());
            productModalController.setType(type);

            // Create and show the modal window
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(searchByCategoryTextField.getScene().getWindow());
            modalStage.setScene(new Scene(modalRoot));
            modalStage.setTitle("Gestione Prodotto");
            modalStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the details of the selected product in the detail fields.
     *
     * @param product The product to display details for.
     */
    private void showProductDetails(Product product) {
        productIdField.setText(String.valueOf(product.getProductId()));
        productNameField.setText(product.getProductName());
        productPriceField.setText(String.format("%.2f", product.getProductPrice()));
        productDescriptionField.setText(product.getProductDescription());
    }

    /**
     * Clears the product details fields.
     */
    private void clearProductDetails() {
        productIdField.clear();
        productNameField.clear();
        productPriceField.clear();
        productDescriptionField.clear();
    }
}
