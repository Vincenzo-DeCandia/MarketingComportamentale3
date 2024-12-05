package com.market.newmarketapp.userScenes;

import com.market.entity.Product;
import com.market.entity.ShoppingCart;
import com.market.repository.IRepository;
import com.market.repository.concreteRepository.ShoppingCartRepository;
import com.market.service.PaymentService;
import com.market.user.concreteuser.User;
import com.market.user.singletonloggeduser.LoggedUserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class PaymentModalController {
    private final PaymentService paymentService;

    @FXML
    private VBox dynamicFieldsContainer;

    @FXML
    private Button payButton;

    private VBox ccContainer;

    private VBox bancomatContainer;

    private VBox cashContainer;

    @FXML
    private TextField paymentAmount = new TextField();

    @FXML
    private DateCell paymentDate = new DateCell();

    @FXML
    private TextField bancomatNumberTextField = new TextField();

    @FXML
    private TextField bancomatNameTextField = new TextField();

    @FXML
    private TextField bancomatExpiryDateTextField = new TextField();

    @FXML
    private TextField bancomatCvvTextField = new TextField();

    @FXML
    private TextField addressTextField = new TextField();

    @FXML
    private TextField ccNumberTextField = new TextField();

    @FXML
    private TextField ccExpiryDateTextField = new TextField();

    @FXML
    private TextField ccCvvTextField = new TextField();

    @FXML
    private TextField ccAddressTextField = new TextField();

    @FXML
    private TextField cashNameTextField = new TextField();

    @FXML
    private TextField cashSurnameTextField = new TextField();

    @FXML
    private TextField cashAddressTextField = new TextField();

    @FXML
    private ChoiceBox<String> paymentTypeChoiceBox;

    public PaymentModalController() {
        paymentService = new PaymentService();
    }

    @FXML
    protected void initialize() {
        paymentAmount.setDisable(true);
        paymentDate.setDisable(true);

        addressTextField.setPromptText("Inserisci indirizzo");

        bancomatContainer = new VBox();
        bancomatContainer.setSpacing(10);
        bancomatNumberTextField.setPromptText("Inserisci numero carta");
        bancomatNameTextField.setPromptText("Inserisci Nome della carta");
        bancomatCvvTextField.setPromptText("Inserisci CVV");
        bancomatExpiryDateTextField.setPromptText("Inserisci Data di scadenza");
        bancomatContainer.getChildren().add(bancomatNameTextField);
        bancomatContainer.getChildren().add(bancomatExpiryDateTextField);
        bancomatContainer.getChildren().add(bancomatCvvTextField);
        bancomatContainer.getChildren().add(bancomatNumberTextField);
        bancomatContainer.getChildren().add(addressTextField);

        cashContainer = new VBox();
        cashContainer.setSpacing(10);
        cashNameTextField.setPromptText("Inserisci nome");
        cashSurnameTextField.setPromptText("Inserisci cognome");
        cashAddressTextField.setPromptText("Inserisci indirizzo");
        cashContainer.getChildren().add(cashNameTextField);
        cashContainer.getChildren().add(cashSurnameTextField);
        cashContainer.getChildren().add(cashAddressTextField);

        ccContainer = new VBox();
        ccContainer.setSpacing(10);
        ccNumberTextField.setPromptText("Inserisci numero");
        ccCvvTextField.setPromptText("Inserisci CVV");
        ccExpiryDateTextField.setPromptText("Inserisci data di scadenza");
        ccAddressTextField.setPromptText("Inserisci indirizzo");
        ccContainer.getChildren().add(ccNumberTextField);
        ccContainer.getChildren().add(ccExpiryDateTextField);
        ccContainer.getChildren().add(ccCvvTextField);
        ccContainer.getChildren().add(ccAddressTextField);

        payButton.setDisable(true);

        paymentDate.setItem(LocalDate.now());

        paymentTypeChoiceBox.setOnAction(event -> {
            payButton.setDisable(false);
            dynamicFieldsContainer.getChildren().clear();
            switch (paymentTypeChoiceBox.getSelectionModel().getSelectedItem()) {
                case "Carta di credito":
                    dynamicFieldsContainer.getChildren().add(ccContainer);
                    break;
                case "Bancomat":
                    dynamicFieldsContainer.getChildren().add(bancomatContainer);
                    break;
                case "Contanti":
                    dynamicFieldsContainer.getChildren().add(cashContainer);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        });
        List<String> list = new ArrayList<>();
        list.add("Carta di credito");
        list.add("Bancomat");
        list.add("Contanti");
        ObservableList<String> paymentTypes = FXCollections.observableArrayList();
        paymentTypes.addAll(list);
        paymentTypeChoiceBox.setItems(paymentTypes);
    }

    public void setPaymentAmount(float amount) {
        paymentAmount.setText(String.valueOf(amount));
    }

    @FXML
    protected void handlePayButton(ActionEvent event) {
        List<String> paymentData = new ArrayList<>();
        switch (paymentTypeChoiceBox.getSelectionModel().getSelectedItem()) {
            case "Carta di credito":
                paymentData.add(ccNumberTextField.getText());
                paymentData.add(ccExpiryDateTextField.getText());
                paymentData.add(ccCvvTextField.getText());
                paymentData.add(ccAddressTextField.getText());
                break;
            case "Bancomat":
                paymentData.add(bancomatNumberTextField.getText());
                paymentData.add(bancomatExpiryDateTextField.getText());
                paymentData.add(bancomatCvvTextField.getText());
                paymentData.add(bancomatNameTextField.getText());
                paymentData.add(addressTextField.getText());
                break;
            case "Contanti":
                paymentData.add(cashNameTextField.getText());
                paymentData.add(cashSurnameTextField.getText());
                paymentData.add(cashAddressTextField.getText());
                break;
            default:
                throw new IllegalArgumentException();
        }

        try {
            paymentService.executePayment(paymentTypeChoiceBox.getValue(), Float.parseFloat(paymentAmount.getText()), paymentData);
            IRepository<ShoppingCart, Pair<Integer, List<Integer>>> shoppingCartRepository = new ShoppingCartRepository();
            ShoppingCart shoppingCart = ((User) LoggedUserSingleton.getInstance().getLoggedUser()).getShoppingCart();
            for (Product product : shoppingCart.getProducts()) {
                product.setProductQuantity(0);
            }
            shoppingCartRepository.delete(shoppingCart);


        } catch (Exception e) {
            e.printStackTrace();
        }

        ((Stage)dynamicFieldsContainer.getScene().getWindow()).close();
    }
}
