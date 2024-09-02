package person.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import person.model.utility.JDBCUtils;
import person.view.MainView;

import java.time.LocalDate;

public class PurchaceControl implements EventHandler<ActionEvent> {

    private final TextField tfFirstName;
    private final TextField tfLastName;
    private final TextField tfUsername;
    private final DatePicker dpTravelDate;
    private final TextField tfTravelTime;
    private final TextField tfSelectedBuilding;
    private final ComboBox<String> cbVehicle;
    private final TextField tfPartnerFirstName;
    private final TextField tfPartnerLastName;
    private final TextField tfPartnerUsername;
    private final MainView mainView;

    public PurchaceControl(TextField tfFirstName, TextField tfLastName, TextField tfUsername, DatePicker dpTravelDate, TextField tfTravelTime, TextField tfSelectedBuilding, ComboBox<String> cbVehicle, TextField tfPartnerFirstName, TextField tfPartnerLastName, TextField tfPartnerUsername, MainView mainView) {
        this.tfFirstName = tfFirstName;
        this.tfLastName = tfLastName;
        this.tfUsername = tfUsername;
        this.dpTravelDate = dpTravelDate;
        this.tfTravelTime = tfTravelTime;
        this.tfSelectedBuilding = tfSelectedBuilding;
        this.cbVehicle = cbVehicle;
        this.tfPartnerFirstName = tfPartnerFirstName;
        this.tfPartnerLastName = tfPartnerLastName;
        this.tfPartnerUsername = tfPartnerUsername;
        this.mainView = mainView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String firstName = tfFirstName.getText().trim();
        String lastName = tfLastName.getText().trim();
        String username = tfUsername.getText().trim();
        LocalDate travelDate = dpTravelDate.getValue();
        String travelTime = tfTravelTime.getText().trim();
        String selectedBuilding = tfSelectedBuilding.getText().trim();
        String selectedVehicle = cbVehicle.getValue();
        String partnerFirstName = tfPartnerFirstName.getText().trim();
        String partnerLastName = tfPartnerLastName.getText().trim();
        String partnerUsername = tfPartnerUsername.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || travelDate == null || travelTime.isEmpty() || selectedBuilding.isEmpty() || selectedVehicle == null) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please fill out all mandatory fields.");
            return;
        }

        // Date and Time validation
        if (travelDate.isBefore(LocalDate.now())) {
            showAlert(Alert.AlertType.WARNING, "Invalid Date", "Travel date must be in the future.");
            return;
        }

        if (!travelTime.matches("\\d{2}:\\d{2}:\\d{2}")) {
            showAlert(Alert.AlertType.WARNING, "Invalid Time Format", "Time format must be hh:mm:ss.");
            return;
        }

        // Confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Purchase");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to purchase this ticket?");
        if (confirmationAlert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        // Set partner data to null if not filled
        if (partnerFirstName.isEmpty()) partnerFirstName = null;
        if (partnerLastName.isEmpty()) partnerLastName = null;
        if (partnerUsername.isEmpty()) partnerUsername = null;

        // Save purchase data to database
        try {
            JDBCUtils.purchaseTicket(username, firstName, lastName, partnerUsername, partnerFirstName, partnerLastName, selectedBuilding, selectedVehicle, travelDate, travelTime);
            showAlert(Alert.AlertType.INFORMATION, "Purchase Successful", "Your ticket has been purchased successfully.");
            mainView.refreshAvailableBuildings(); // Refresh available buildings
            mainView.refreshUserPurchases(username); // Refresh user purchases
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Purchase Failed", "An error occurred while purchasing the ticket: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
