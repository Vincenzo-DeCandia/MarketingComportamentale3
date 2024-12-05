package com.market.newmarketapp.adminScenes;

import com.market.newmarketapp.MarketApplication;
import com.market.newmarketapp.managerscene.Controller;
import com.market.repository.IRepository;
import com.market.repository.IUserAnalyzeRepository;
import com.market.repository.concreteRepository.UserRepository;
import com.market.service.UserAnalysisService;
import com.market.user.BaseUser;
import com.market.user.concreteuser.User;
import com.market.service.IUserAnalysisService;
import com.market.user.singletonloggeduser.LoggedUserSingleton;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class AnalyzeUserController extends Controller {
    private final ObservableList<BaseUser> users = FXCollections.observableArrayList();

    private Map<BaseUser, String> userCategory;

    @FXML
    private ComboBox<String> boxSearchUser;

    @FXML
    private TextField counterField;

    @FXML
    private TableView<BaseUser> userTable;

    @FXML
    private TableColumn<BaseUser, Integer> idColumn;

    @FXML
    private TableColumn<BaseUser, String> nameColumn;

    @FXML
    private TableColumn<BaseUser, String> surnameColumn;

    @FXML
    private TableColumn<BaseUser, String> categoryColumn;

    @FXML
    private Button buttonAddOffer;


    @FXML
    protected void handleButtonAddOffer(ActionEvent event) {
        openOfferModal();
    }

    private List<User> searchRandomUsers(int limit) {
        IUserAnalyzeRepository iUserAnalyzeRepository = new UserRepository();
        return iUserAnalyzeRepository.randomUser(limit);
    }

    private List<User> searchByOrderUsersNumber(int min) {
        IUserAnalyzeRepository iUserAnalyzeRepository = new UserRepository();
        return iUserAnalyzeRepository.searchByOrderNumber(min);
    }

    private List<User> searchAll() {
        IRepository<User, Integer> userIRepository = new UserRepository();
        return userIRepository.findAll();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdUser()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        categoryColumn.setCellValueFactory(cellData -> {
            String category = userCategory.get(cellData.getValue());
            return new SimpleStringProperty(category);
        });
        userTable.setItems(users);
        userTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ObservableList<String> type = FXCollections.observableArrayList();
        type.add("Tutti");
        type.add("Numero di ordini");
        type.add("Casuale");

        boxSearchUser.setItems(type);

// Disabilita il campo all'avvio
        counterField.setDisable(true);

// Aggiungi il listener
        boxSearchUser.setOnAction(event -> {
            String selectedItem = boxSearchUser.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                return; // Gestisci il caso in cui non ci sia nulla di selezionato
            }

            switch (selectedItem) {
                case "Tutti":
                    counterField.setDisable(true);
                    break;
                case "Numero di ordini":
                case "Casuale":
                    counterField.setDisable(false);
                    break;
                default:
                    // Puoi gestire valori imprevisti o lasciare vuoto
                    break;
            }
        });


        analyzeUser(searchAll());
    }

    private void analyzeUser(List<User> userList) {
        if (LoggedUserSingleton.getInstance().getLoggedUser().getRole().equals("admin")) {
            users.clear();
            users.addAll(userList);
            if (users.isEmpty()) {
                System.out.println("Utenti non trovati");
            }
            IUserAnalysisService userAnalysisService = new UserAnalysisService();
            for (BaseUser user : users) {
                System.out.println(user.getIdUser());
            }
            userCategory = userAnalysisService.analyzeUser(users);

            for (Map.Entry<BaseUser, String> entry : userCategory.entrySet()) {
                System.out.println(entry.getKey().getIdUser() + " " + entry.getValue());
            }
        }
    }

    @FXML
    protected void openOfferModal() {
        try {
            FXMLLoader loader = new FXMLLoader(MarketApplication.class.getResource("modal-offer-view.fxml"));
            VBox modalRoot = loader.load();

            OfferModalController offerModalController = loader.getController();
            offerModalController.setBaseUser(userTable.getSelectionModel().getSelectedItem());
            offerModalController.setCategory(userCategory.get(userTable.getSelectionModel().getSelectedItem()));

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(userTable.getScene().getWindow());
            modalStage.setScene(new Scene(modalRoot));
            modalStage.setTitle("Gestione Offerte");
            modalStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onBackButton(ActionEvent actionEvent) {
        this.getSceneManager().switchScene("admin_dashboard");
    }

    @FXML
    protected void handleSearchUserButton(ActionEvent actionEvent) {
        if (boxSearchUser.getSelectionModel().getSelectedItem() != null) {
            List<User> userList = null;
            if (boxSearchUser.getSelectionModel().getSelectedItem().equals("Tutti")) {
                userList = searchAll();
            } else if (boxSearchUser.getSelectionModel().getSelectedItem().equals("Numero di ordini")) {
                userList = searchByOrderUsersNumber(Integer.parseInt(counterField.getText()));
            } else if (boxSearchUser.getSelectionModel().getSelectedItem().equals("Casuale")) {
                userList = searchRandomUsers(Integer.parseInt(counterField.getText()));
            }
            analyzeUser(userList);
        }
    }
}
