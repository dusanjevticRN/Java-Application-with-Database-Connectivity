package person.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import person.model.utility.JDBCUtils;

public class RegisterControl implements EventHandler<ActionEvent> {
    private TextField tfRegFirstName;
    private TextField tfRegLastName;
    private TextField tfRegUsername;
    private TextField tfRegPassword;

    public RegisterControl(TextField tfRegFirstName, TextField tfRegLastName, TextField tfRegUsername, TextField tfRegPassword) {
        this.tfRegFirstName = tfRegFirstName;
        this.tfRegLastName = tfRegLastName;
        this.tfRegUsername = tfRegUsername;
        this.tfRegPassword = tfRegPassword;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String firstName = this.tfRegFirstName.getText().trim();
        String lastName = this.tfRegLastName.getText().trim();
        String username = this.tfRegUsername.getText().trim();
        String password = this.tfRegPassword.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "All fields are required.");
            return;
        }

        try {
            JDBCUtils.insertIntoUsers(firstName, lastName, username, password);
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered successfully.");
            this.tfRegFirstName.clear();
            this.tfRegLastName.clear();
            this.tfRegUsername.clear();
            this.tfRegPassword.clear();
        } catch (RuntimeException e) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", e.getMessage());
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
