package com.market.newmarketapp.userScenes;

import com.market.entity.Offer;
import com.market.entity.Product;
import com.market.entity.ShoppingCart;
import com.market.newmarketapp.managerscene.Controller;
import com.market.repository.IOfferRepository;
import com.market.repository.IRepository;
import com.market.repository.concreteRepository.OfferRepository;
import com.market.repository.concreteRepository.ProductRepository;
import com.market.repository.concreteRepository.ShoppingCartRepository;
import com.market.user.singletonloggeduser.LoggedUserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HomeUserController extends Controller {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Product> productListView;

    @FXML
    private Button searchButton;

    @FXML
    private Button addShoppingCart;

    private ObservableList<Product> productObservableList;

    private ShoppingCart shoppingCart = new ShoppingCart();

    @FXML
    private Label offerBanner;


    public HomeUserController() {
        super();
    }

    @FXML
    private void initialize() {
        initListView();
        IOfferRepository offerRepository = new OfferRepository();
        IRepository<Product, Integer> productRepository = new ProductRepository();
        List<Offer> offerList = offerRepository.findByUserId(LoggedUserSingleton.getInstance().getLoggedUser().getIdUser());
        if (offerList != null && !offerList.isEmpty()) {
            // Ottieni la data corrente
            LocalDate currentDate = LocalDate.now();

            // Filtra le offerte valide: quelle in cui la data corrente è tra startDate e endDate
            List<Offer> validOffers = offerList.stream()
                    .filter(offer -> !currentDate.isBefore(offer.getStartDate().toLocalDate()) && !currentDate.isAfter(offer.getEndDate().toLocalDate()))
                    .toList();

            // Se ci sono offerte valide, trova quella con la data di inizio più recente
            if (!validOffers.isEmpty()) {
                Offer bestOffer = Collections.max(validOffers, Comparator.comparing(Offer::getStartDate));
                offerBanner.setText("Nuova offerta: " + bestOffer.getNameOffer() + "\n" +
                        "Sconto del " + bestOffer.getDiscount() + "% su " +
                        productRepository.findById(bestOffer.getIdProduct()).getProductName());
            } else {
                offerBanner.setText("Nessuna offerta valida al momento.");
            }
        }

    }

    @FXML
    protected void onProfileButton() {
        getSceneManager().switchScene("profile");
    }

    @FXML
    protected void onViewCartButton() {
        this.getSceneManager().switchScene("shoppingCart");
    }

    @FXML
    protected void onLogoutButton() {
        if (showConfirmationDialog("Logout", "Sei sicuro di voler uscire?")) {
            LoggedUserSingleton.getInstance().getLoggedUser().logout();
            LoggedUserSingleton.getInstance().setLoggedUser(null);
            System.exit(0);
        }
    }

    @FXML
    protected void onSearchButton() {
        if (!searchField.getText().isEmpty()) {
            ProductRepository productRepository = new ProductRepository();
            List<Product> products = productRepository.searchByName(searchField.getText());
            if (products != null) {
                productObservableList.clear();
                productObservableList.addAll(products);
            }
        }
    }

    @FXML
    protected void onAddShoppingCartButton() {
        Product product = productListView.getSelectionModel().getSelectedItem();

        // Creazione del nuovo Stage per il modal
        Stage stage = new Stage();
        stage.setTitle("Aggiungi al Carrello");

        // Layout principale del modal
        VBox modalLayout = new VBox(15);
        modalLayout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #f9f9f9; -fx-border-color: #dcdcdc; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Etichetta descrittiva
        Label instructionLabel = new Label("Seleziona il numero di oggetti da aggiungere al carrello:");
        instructionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");

        // Label per visualizzare la quantità selezionata
        Label quantityLabel = new Label("1");
        quantityLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333333;");

        // HBox per contenere i pulsanti "+" e "-" e la label della quantità
        HBox quantityControl = new HBox(15);
        quantityControl.setStyle("-fx-alignment: center;");

        // Pulsante per decrementare la quantità
        Button decreaseButton = new Button("-");
        decreaseButton.setStyle("-fx-font-size: 18px; -fx-width: 30px; -fx-height: 30px;");

        // Pulsante per incrementare la quantità
        Button increaseButton = new Button("+");
        increaseButton.setStyle("-fx-font-size: 18px; -fx-width: 30px; -fx-height: 30px;");

        // Logica per decrementare
        decreaseButton.setOnAction(event -> {
            int currentQuantity = Integer.parseInt(quantityLabel.getText());
            if (currentQuantity > 1) {
                quantityLabel.setText(String.valueOf(currentQuantity - 1));
            }
        });

        // Logica per incrementare
        increaseButton.setOnAction(event -> {
            int currentQuantity = Integer.parseInt(quantityLabel.getText());
            quantityLabel.setText(String.valueOf(currentQuantity + 1));
        });

        // Aggiungere i pulsanti e la label della quantità all'HBox
        quantityControl.getChildren().addAll(decreaseButton, quantityLabel, increaseButton);

        // Pulsante per confermare l'aggiunta al carrello
        Button confirmButton = new Button("Aggiungi al Carrello");
        confirmButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Azione del pulsante
        confirmButton.setOnAction(event -> {
            int selectedQuantity = Integer.parseInt(quantityLabel.getText());
            System.out.println("Hai selezionato " + selectedQuantity + " oggetti da aggiungere al carrello.");

            Product product1 = product.clone();

            product1.setProductQuantity(selectedQuantity);

            shoppingCart.addProduct(product1);

            ShoppingCartRepository shoppingCartRepository = new ShoppingCartRepository();
            if (!shoppingCartRepository.save(shoppingCart)) {
                showAlert("Attenzione", "Hai superato la massima quantità disponibile", Alert.AlertType.WARNING);
            }

            // Chiudi il modal
            stage.close();
        });

        // Aggiunta degli elementi al layout
        modalLayout.getChildren().addAll(instructionLabel, quantityControl, confirmButton);

        // Configurazione della scena del modal
        Scene scene = new Scene(modalLayout, 300, 200);
        stage.setScene(scene);

        // Imposta il modal come una finestra modale
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }


    private void initListView() {
        DecimalFormat df = new DecimalFormat("#.##");
        productObservableList = FXCollections.observableArrayList();
        productListView.setItems(productObservableList);
        OfferRepository offerRepository = new OfferRepository();
        List<Offer> offerList = offerRepository.findByUserId(LoggedUserSingleton.getInstance().getLoggedUser().getIdUser());
        productListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                }
                else {
                    Offer offerProduct = null;
                    float price = product.getProductPrice();
                    if (offerList != null) {
                        for (Offer offer : offerList) {
                            if (offer.getIdProduct() == product.getProductId()) {
                                offerProduct = offer;
                                price = price - offerProduct.getDiscount() * price / 100;
                            }
                        }
                    }
                    if (offerProduct != null) {
                        setText(product.getProductName() + " - Prezzo iniziale: " + product.getProductPrice() + " €" + " - Nuovo prezzo: " + (int)(price * 100) / 100.0f + " €");
                    } else {
                        setText(product.getProductName() + " - Prezzo: " + product.getProductPrice() + " €");
                    }

                }
            }
        });
        productListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void onOrderButton(ActionEvent actionEvent) {
        this.getSceneManager().switchScene("orderScene");
    }

    private boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }
}
