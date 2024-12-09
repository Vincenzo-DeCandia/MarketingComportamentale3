package com.market.newmarketapp.adminScenes;

import com.market.entity.Product;
import com.market.repository.IRepository;
import com.market.repository.concreteRepository.ProductRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the product modal, responsible for handling product creation and updates.
 * The modal is used to either create a new product or update an existing one.
 */
public class ProductModalController {
    private Product product;
    private int type; // Used to differentiate between save (0) and update (1)

    @FXML
    private TextField productCodeTextField;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField productCategoryTextField;

    @FXML
    private TextField productPriceTextField;

    @FXML
    private TextField productQuantityTextField;

    @FXML
    private TextArea productDescriptionTextArea;

    // Constructor for initializing the controller
    public ProductModalController() {}

    /**
     * Handles the action when the save button is clicked.
     * Depending on the type, either saves a new product or updates an existing product.
     *
     * @param event The event triggered by clicking the save button.
     */
    @FXML
    protected void handleSaveButton(ActionEvent event) {
        if (type == 0) // Save new product
            saveProduct();
        else if (type == 1) // Update existing product
            updateProduct();
    }

    /**
     * Saves a new product to the repository.
     */
    protected void saveProduct() {
        IRepository<Product, Integer> productRepository = new ProductRepository();
        product = new Product(); // Create a new product instance
        modifyProduct(); // Modify the product's attributes with data from the form
        productRepository.save(product); // Save the product to the repository
        closeModal(); // Close the modal after saving
    }

    /**
     * Updates an existing product in the repository.
     */
    protected void updateProduct() {
        IRepository<Product, Integer> productRepository = new ProductRepository();
        if (product != null && product.getProductId() != null) {
            modifyProduct(); // Modify the product's attributes with data from the form
            productRepository.update(product); // Update the product in the repository
        }
        closeModal(); // Close the modal after updating
    }

    /**
     * Closes the product modal window.
     */
    @FXML
    protected void closeModal() {
        Stage stage = (Stage) productCategoryTextField.getScene().getWindow(); // Get the current stage
        stage.close(); // Close the stage (modal)
    }

    /**
     * Modifies the product's attributes using the data from the form fields.
     * This method is used for both saving and updating products.
     */
    private void modifyProduct() {
        if (product != null) {
            product.setProductCode(productCodeTextField.getText());
            product.setProductName(productNameTextField.getText());
            product.setProductDescription(productDescriptionTextArea.getText());
            product.setProductQuantity(Integer.parseInt(productQuantityTextField.getText()));
            product.setProductCategory(productCategoryTextField.getText());
            product.setProductPrice(Float.parseFloat(productPriceTextField.getText()));
        }
    }

    /**
     * Sets the product to be edited or created.
     * This method populates the fields with the current product data if available.
     *
     * @param selectedItem The product to set in the modal.
     */
    public void setProduct(Product selectedItem) {
        this.product = selectedItem;

        if (product != null) {
            // Populate the fields with product data
            this.productCodeTextField.setText(product.getProductCode());
            this.productNameTextField.setText(product.getProductName());
            this.productDescriptionTextArea.setText(product.getProductDescription());
            this.productPriceTextField.setText(String.valueOf(product.getProductPrice()));
            this.productQuantityTextField.setText(String.valueOf(product.getProductQuantity()));
            this.productCategoryTextField.setText(product.getProductCategory());
        }
    }

    /**
     * Sets the type of the modal.
     * The type determines if the modal is for creating a new product (0) or updating an existing one (1).
     *
     * @param type The type of the modal (0 for save, 1 for update).
     */
    public void setType(int type) {
        this.type = type;
    }
}
