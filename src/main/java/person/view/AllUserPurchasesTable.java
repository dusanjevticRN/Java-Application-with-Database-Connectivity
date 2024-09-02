package person.view;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import person.model.Purchase;

import java.util.List;

public class AllUserPurchasesTable extends VBox {
    private final TableView<Purchase> tableView;

    public AllUserPurchasesTable(List<Purchase> values) {
        tableView = new TableView<>(FXCollections.observableArrayList(values));

        TableColumn<Purchase, Integer> tcPurchaseId = new TableColumn<>("ID");
        TableColumn<Purchase, String> tcFirstName = new TableColumn<>("First Name");
        TableColumn<Purchase, String> tcLastName = new TableColumn<>("Last Name");
        TableColumn<Purchase, String> tcUsername = new TableColumn<>("Username");
        TableColumn<Purchase, String> tcBuildingName = new TableColumn<>("Building Name");
        TableColumn<Purchase, String> tcVehicle = new TableColumn<>("Vehicle");
        TableColumn<Purchase, String> tcPurchaseDate = new TableColumn<>("Purchase Date");
        TableColumn<Purchase, String> tcTravelDate = new TableColumn<>("Travel Date");
        TableColumn<Purchase, String> tcTravelTime = new TableColumn<>("Travel Time");

        tcPurchaseId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tcUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tcBuildingName.setCellValueFactory(new PropertyValueFactory<>("buildingName"));
        tcVehicle.setCellValueFactory(new PropertyValueFactory<>("vehicle"));
        tcPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        tcTravelDate.setCellValueFactory(new PropertyValueFactory<>("travelDate"));
        tcTravelTime.setCellValueFactory(new PropertyValueFactory<>("travelTime"));

        tableView.getColumns().addAll(tcPurchaseId, tcFirstName, tcLastName, tcUsername, tcBuildingName, tcVehicle, tcPurchaseDate, tcTravelDate, tcTravelTime);

        // Fit columns to text
        fitColumnsToText(tcPurchaseId, tcFirstName, tcLastName, tcUsername, tcBuildingName, tcVehicle, tcPurchaseDate, tcTravelDate, tcTravelTime);

        // Set the preferred height to show 4 rows
        tableView.setFixedCellSize(25); // Approximate row height in pixels
        tableView.setPrefHeight(4 * 25 + 28); // 4 rows + header height

        // Add title
        javafx.scene.control.Label label = new javafx.scene.control.Label("Made purchases for logged in user");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Add title and table to VBox
        this.getChildren().addAll(label, tableView);
    }

    private void fitColumnsToText(TableColumn<?, ?>... columns) {
        for (TableColumn<?, ?> column : columns) {
            column.setMinWidth(column.getText().length() * 8); // Approximate width
        }
    }

    public TableView<Purchase> getTableView() {
        return tableView;
    }

    public void clearPurchases() {
        tableView.getItems().clear();
    }
}
