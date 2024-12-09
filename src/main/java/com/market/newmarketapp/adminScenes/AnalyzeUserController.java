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

/**
 * Controller class for analyzing users in the application.
 * This class manages user search functionality, displaying users in a table,
 * and allowing the creation of offers for selected users.
 */
public class AnalyzeUserController extends Controller {
    private final ObservableList<BaseUser> users = FXCollections.observableArrayList();  // Observable list to store users

    private Map<BaseUser, String> userCategory;  // Map to store user categories

    @FXML
    private ComboBox<String> boxSearchUser;  // ComboBox for selecting search criteria

    @FXML
    private TextField counterField;  // TextField for entering counter value for search

    @FXML
    private TableView<BaseUser> userTable;  // TableView for displaying users

    @FXML
    private TableColumn<BaseUser, Integer> idColumn;  // Column for user ID

    @FXML
    private TableColumn<BaseUser, String> nameColumn;  // Column for user name

    @FXML
    private TableColumn<BaseUser, String> surnameColumn;  // Column for user surname

    @FXML
    private TableColumn<BaseUser, String> categoryColumn;  // Column for user category

    @FXML
    private Button buttonAddOffer;  // Button to open the offer modal

    /**
     * Handles the click event of the "Add Offer" button.
     * Opens the offer modal for the selected user.
     */
    @FXML
    protected void handleButtonAddOffer(ActionEvent event) {
        openOfferModal();  // Call the method to open the offer modal
    }

    /**
     * Searches for random users up to the specified limit.
     *
     * @param limit The maximum number of users to return.
     * @return A list of random users.
     */
    private List<User> searchRandomUsers(int limit) {
        IUserAnalyzeRepository iUserAnalyzeRepository = new UserRepository();
        return iUserAnalyzeRepository.randomUser(limit);
    }

    /**
     * Searches for users with a minimum number of orders.
     *
     * @param min The minimum number of orders.
     * @return A list of users with at least the specified number of orders.
     */
    private List<User> searchByOrderUsersNumber(int min) {
        IUserAnalyzeRepository iUserAnalyzeRepository = new UserRepository();
        return iUserAnalyzeRepository.searchByOrderNumber(min);
    }

    /**
     * Searches for all users in the system.
     *
     * @return A list of all users.
     */
    private List<User> searchAll() {
        IRepository<User, Integer> userIRepository = new UserRepository();
        return userIRepository.findAll();
    }

    /**
     * Initializes the user analysis view, including setting up the table columns
     * and search criteria ComboBox, and disables the counter field initially.
     */
    @FXML
    public void initialize() {
        // Setting up table columns with data bindings
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdUser()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        categoryColumn.setCellValueFactory(cellData -> {
            String category = userCategory.get(cellData.getValue());  // Get the category for each user
            return new SimpleStringProperty(category);
        });
        userTable.setItems(users);  // Set the items for the user table
        userTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);  // Set selection mode to single

        // Setting up the ComboBox for selecting search criteria
        ObservableList<String> type = FXCollections.observableArrayList();
        type.add("Tutti");  // All users
        type.add("Numero di ordini");  // By number of orders
        type.add("Casuale");  // Random users
        boxSearchUser.setItems(type);  // Set the items for the search ComboBox

        // Disable the counter field at the start
        counterField.setDisable(true);

        // Add a listener to the ComboBox to enable or disable the counter field based on selection
        boxSearchUser.setOnAction(event -> {
            String selectedItem = boxSearchUser.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                return;  // Return if no item is selected
            }

            switch (selectedItem) {
                case "Tutti":  // All users - disable counter field
                    counterField.setDisable(true);
                    break;
                case "Numero di ordini":  // By number of orders - enable counter field
                case "Casuale":  // Random users - enable counter field
                    counterField.setDisable(false);
                    break;
                default:
                    break;  // Handle unexpected values if necessary
            }
        });

        // Analyze all users initially
        analyzeUser(searchAll());
    }

    /**
     * Analyzes the given list of users and categorizes them.
     * If the logged-in user is an admin, it updates the user list and category map.
     *
     * @param userList The list of users to analyze.
     */
    private void analyzeUser(List<User> userList) {
        if (LoggedUserSingleton.getInstance().getLoggedUser().getRole().equals("admin")) {
            users.clear();
            users.addAll(userList);  // Add all users to the observable list

            if (users.isEmpty()) {
                System.out.println("Utenti non trovati");  // Print message if no users found
            }

            // Create an instance of the user analysis service
            IUserAnalysisService userAnalysisService = new UserAnalysisService();
            userCategory = userAnalysisService.analyzeUser(users);  // Categorize users

            // Print each user ID and their corresponding category
            for (Map.Entry<BaseUser, String> entry : userCategory.entrySet()) {
                System.out.println(entry.getKey().getIdUser() + " " + entry.getValue());
            }
        }
    }

    /**
     * Opens the offer modal for the selected user.
     * Passes the selected user and their category to the modal controller.
     */
    @FXML
    protected void openOfferModal() {
        try {
            // Load the modal view for creating offers
            FXMLLoader loader = new FXMLLoader(MarketApplication.class.getResource("modal-offer-view.fxml"));
            VBox modalRoot = loader.load();

            // Get the controller for the modal
            OfferModalController offerModalController = loader.getController();
            BaseUser selectedUser = userTable.getSelectionModel().getSelectedItem();  // Get the selected user
            offerModalController.setBaseUser(selectedUser);  // Set the selected user
            offerModalController.setCategory(userCategory.get(selectedUser));  // Set the category for the user

            // Set up and display the modal window
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);  // Make it modal (blocks interaction with other windows)
            modalStage.initOwner(userTable.getScene().getWindow());  // Set the parent window
            modalStage.setScene(new Scene(modalRoot));  // Set the scene for the modal
            modalStage.setTitle("Gestione Offerte");  // Set the title of the modal
            modalStage.showAndWait();  // Show and wait for the modal to be closed
        } catch (Exception e) {
            e.printStackTrace();  // Print the stack trace if an error occurs
        }
    }

    /**
     * Handles the "Back" button click event to navigate back to the admin dashboard.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    @FXML
    protected void onBackButton(ActionEvent actionEvent) {
        this.getSceneManager().switchScene("admin_dashboard");  // Switch to the admin dashboard scene
    }

    /**
     * Handles the "Search User" button click event to search for users based on selected criteria.
     *
     * @param actionEvent The action event triggered by the button click.
     */
    @FXML
    protected void handleSearchUserButton(ActionEvent actionEvent) {
        if (boxSearchUser.getSelectionModel().getSelectedItem() != null) {
            List<User> userList = null;
            // Perform search based on selected criteria
            if (boxSearchUser.getSelectionModel().getSelectedItem().equals("Tutti")) {
                userList = searchAll();  // Search for all users
            } else if (boxSearchUser.getSelectionModel().getSelectedItem().equals("Numero di ordini")) {
                // Search for users with a minimum number of orders
                userList = searchByOrderUsersNumber(Integer.parseInt(counterField.getText()));
            } else if (boxSearchUser.getSelectionModel().getSelectedItem().equals("Casuale")) {
                // Search for random users
                userList = searchRandomUsers(Integer.parseInt(counterField.getText()));
            }
            analyzeUser(userList);  // Analyze and display the found users
        }
    }
}

