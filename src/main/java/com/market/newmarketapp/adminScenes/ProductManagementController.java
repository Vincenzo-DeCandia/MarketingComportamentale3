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


    public ProductManagementController() {
        super();
    }

    @FXML
    public void initialize() {
        List<Product> listProducts = getProductList();
        productListView.setItems(products);
        if (listProducts == null || listProducts.isEmpty()) {
            System.out.println("No products found");
            return;
        }
        products.addAll(listProducts);
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
        productListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean productSelected = newSelection != null;
            modifyProductButton.setDisable(!productSelected);
            deleteProductButton.setDisable(!productSelected);

            clearProductDetails();
            if (productSelected) {
                showProductDetails(newSelection);
            }
        });

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

    @FXML
    protected void handleInsertButton(ActionEvent event) {
        openProductModal(0);
        productListView.refresh();
    }

    @FXML
    protected void handleModifyButton(ActionEvent event) {
        openProductModal(1);
        productListView.refresh();
    }

    @FXML
    protected void handleBackButton(ActionEvent event) {
        this.getSceneManager().switchScene("admin_dashboard");
    }

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


    @FXML
    protected void handleDeleteProduct(ActionEvent event) {
        IRepository<Product, Integer> productRepository = new ProductRepository();
        Product product = productListView.getSelectionModel().getSelectedItem();
        if (product != null) {
            productRepository.delete(product);
        }
        products.remove(product);
        clearProductDetails();
        productListView.getSelectionModel().clearSelection();
        productListView.refresh();
    }


    private List<Product> getProductList() {
        IRepository<Product, Integer> productRepository = new ProductRepository();
        return productRepository.findAll();
    }

    private List<Product> getProductListByCategory(String category) {
        IRepository<Product, Integer> productRepository = new ProductRepository();
        return null;
    }

    private List<Product> getProductListByName(String name) {
        IRepository<Product, Integer> productRepository = new ProductRepository();
        return null;
    }

    @FXML
    protected void openProductModal(int type) {
        try {
            FXMLLoader loader = new FXMLLoader(MarketApplication.class.getResource("modal-product-view.fxml"));
            VBox modalRoot = loader.load();

            ProductModalController productModalController = loader.getController();
            productModalController.setProduct(productListView.getSelectionModel().getSelectedItem());
            productModalController.setType(type);

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

    private void showProductDetails(Product product) {
        productIdField.setText(String.valueOf(product.getProductId()));
        productNameField.setText(product.getProductName());
        productPriceField.setText(String.format("%.2f", product.getProductPrice()));
        productDescriptionField.setText(product.getProductDescription());
    }

    private void clearProductDetails() {
        productIdField.clear();
        productNameField.clear();
        productPriceField.clear();
        productDescriptionField.clear();
    }

}