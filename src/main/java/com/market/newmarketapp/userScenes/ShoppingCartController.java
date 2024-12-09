package com.market.newmarketapp.userScenes;

import com.market.entity.Offer;
import com.market.entity.Product;
import com.market.entity.ShoppingCart;
import com.market.newmarketapp.MarketApplication;
import com.market.newmarketapp.managerscene.Controller;
import com.market.repository.concreteRepository.OfferRepository;
import com.market.repository.concreteRepository.ShoppingCartRepository;
import com.market.user.concreteuser.User;
import com.market.user.singletonloggeduser.LoggedUserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.List;

public class ShoppingCartController extends Controller {
    @FXML
    private ListView<Product> shoppingCartList;

    private ShoppingCart shoppingCart;

    private final ObservableList<Product> shoppingCartObservableList = FXCollections.observableArrayList();

    public ShoppingCartController() {}

    @FXML
    public void initialize() {
        initListView();
        refreshShoppingCart();
    }

    @FXML
    protected void onBackButton(ActionEvent event) {
        this.getSceneManager().switchScene("userHome");
    }

    @FXML
    protected void onCheckout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MarketApplication.class.getResource("modal-payment-view.fxml"));
            Parent modalRoot = loader.load();

            PaymentModalController paymentModalController = loader.getController();
            paymentModalController.setPaymentAmount(shoppingCart.calculateTotal());
            System.out.println(shoppingCart.calculateTotal());

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(shoppingCartList.getScene().getWindow());
            modalStage.setScene(new Scene(modalRoot));
            modalStage.setTitle("Pagamento");
            modalStage.showAndWait();
            refreshShoppingCart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onAddQuantity(ActionEvent event) {
        Product product = shoppingCartList.getSelectionModel().getSelectedItem();
        shoppingCart.updateProductQuantity(product, product.getProductQuantity() + 1);
        shoppingCartList.getSelectionModel().clearSelection();
        refreshShoppingCartRepository();
        refreshShoppingCart();
    }

    private void initListView() {
        OfferRepository offerRepo = new OfferRepository();
        List<Offer> offerList = offerRepo.findByUserId(LoggedUserSingleton.getInstance().getLoggedUser().getIdUser());

        shoppingCartList.setCellFactory(lv -> new ListCell<>() {

            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                }
                else {
                    Offer offerProduct;
                    float price = product.getProductPrice();
                    if (offerList != null && !offerList.isEmpty()) {
                        for (Offer offer : offerList) {
                            if (offer.getIdProduct() == product.getProductId()) {
                                offerProduct = offer;
                                price = price - offerProduct.getDiscount() * price / 100;
                            }
                        }
                    }
                    setText(product.getProductName() + " - " + "Quantità: " + product.getProductQuantity() + " - Prezzo: " + ((int)(price * 100) / 100.0f)*(product.getProductQuantity()) + " €");
                }
            }
        });

    }

    @FXML
    protected void onRemoveSelected(ActionEvent event) {
        Product product = shoppingCartList.getSelectionModel().getSelectedItem();
        product.setProductQuantity(0);
        ShoppingCartRepository shoppingCartRepository = new ShoppingCartRepository();
        shoppingCartRepository.delete(shoppingCart);
        shoppingCart.removeProduct(product);
        shoppingCartList.getSelectionModel().clearSelection();
        refreshShoppingCartRepository();
        refreshShoppingCart();
    }

    @FXML
    protected void onDecrementQuantity(ActionEvent event) {
        ShoppingCartRepository shoppingCartRepository = new ShoppingCartRepository();
        Product product = shoppingCartList.getSelectionModel().getSelectedItem();
        shoppingCart.updateProductQuantity(product, product.getProductQuantity() - 1);
        if (product.getProductQuantity() == 0) {
            shoppingCartRepository.delete(shoppingCart);
            shoppingCart.removeProduct(product);
        }
        shoppingCartList.getSelectionModel().clearSelection();
        refreshShoppingCartRepository();
        refreshShoppingCart();
    }

    private void refreshShoppingCartRepository() {
        ShoppingCartRepository shoppingCartRepository = new ShoppingCartRepository();
        if (!shoppingCartRepository.save(shoppingCart)) {
            showAlert("Attenzione", "Hai superato la massima quantità disponibile", Alert.AlertType.WARNING);
        }
    }

    private void refreshShoppingCart() {
        shoppingCartObservableList.clear();
        ShoppingCartRepository repo = new ShoppingCartRepository();
        shoppingCartList.setItems(shoppingCartObservableList);
        shoppingCart = repo.findById(new Pair<>(LoggedUserSingleton.getInstance().getLoggedUser().getIdUser(), null));
        if (shoppingCart != null) {
            shoppingCartObservableList.addAll(shoppingCart.getProducts());
        }
        ((User) LoggedUserSingleton.getInstance().getLoggedUser()).setShoppingCart(shoppingCart);
    }
}
