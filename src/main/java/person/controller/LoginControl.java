package person.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import person.model.utility.JDBCUtils;
import person.view.MainView;

public class LoginControl implements EventHandler<ActionEvent>
{
    private TextField tfLoginUsername;
    private TextField tfLoginPassword;
    private MainView mainView;

    public LoginControl(TextField tfLoginUsername, TextField tfLoginPassword, MainView mainView)
    {
        this.tfLoginUsername = tfLoginUsername;
        this.tfLoginPassword = tfLoginPassword;
        this.mainView = mainView;
    }

    @Override
    public void handle(ActionEvent actionEvent)
    {
        String username = this.tfLoginUsername.getText().trim();
        String password = this.tfLoginPassword.getText().trim();

        boolean isValidUser = JDBCUtils.validateUser(username, password);
        if (isValidUser)
        {
            String usernameToShow = username;
            mainView.setLoginStatus(usernameToShow);
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Successfully logged in as " + usernameToShow);
        }

        else
        {
            mainView.setLoginStatus(null);
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
