package com.market.newmarketapp.adminScenes;

import com.market.entity.Product;
import com.market.repository.IRepository;
import com.market.repository.concreteRepository.ProductRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProductModalController {
    private Product product;
    private int type;

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

    public ProductModalController() {}

    @FXML
    protected void handleSaveButton(ActionEvent event) {
        if (type == 0)
            saveProduct();
        else if (type == 1)
            updateProduct();
    }

    protected void saveProduct() {
        IRepository<Product, Integer> productRepository = new ProductRepository();
        product = new Product();
        modifyProduct();
        productRepository.save(product);
        closeModal();
    }


    protected void updateProduct() {
        IRepository<Product, Integer> productRepository = new ProductRepository();
        if (product != null && product.getProductId() != null) {
            modifyProduct();
            productRepository.update(product);
        }
        closeModal();
    }

    // Metodo per chiudere il modal
    @FXML
    protected void closeModal() {
        Stage stage = (Stage) productCategoryTextField.getScene().getWindow();
        stage.close();
    }

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

    public void setProduct(Product selectedItem) {
        this.product = selectedItem;

        if (product != null) {
            this.productCodeTextField.setText(product.getProductCode());
            this.productNameTextField.setText(product.getProductName());
            this.productDescriptionTextArea.setText(product.getProductDescription());
            this.productPriceTextField.setText(String.valueOf(product.getProductPrice()));
            this.productQuantityTextField.setText(String.valueOf(product.getProductQuantity()));
            this.productCategoryTextField.setText(product.getProductCategory());
        }

    }

    public void setType(int type) {
        this.type = type;
    }
}
