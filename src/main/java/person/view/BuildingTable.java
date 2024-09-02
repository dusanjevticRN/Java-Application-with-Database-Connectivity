package person.view;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import person.model.Building;
import person.model.utility.JDBCUtils;

import java.util.List;

public class BuildingTable extends VBox {
    private final TableView<Building> tableView;

    public BuildingTable(List<Building> values) {
        tableView = new TableView<>(FXCollections.observableArrayList(values));

        TableColumn<Building, Integer> tcBuildingId = new TableColumn<>("ID");
        TableColumn<Building, String> tcName = new TableColumn<>("Name");
        TableColumn<Building, Integer> tcTotalUnits = new TableColumn<>("Total Units");
        TableColumn<Building, Integer> tcAvailableUnits = new TableColumn<>("Available Units");
        TableColumn<Building, String> tcPlanetOrSatellite = new TableColumn<>("Planet/Satellite");

        tcBuildingId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcTotalUnits.setCellValueFactory(new PropertyValueFactory<>("totalUnits"));
        tcAvailableUnits.setCellValueFactory(new PropertyValueFactory<>("availableUnits"));
        tcPlanetOrSatellite.setCellValueFactory(new PropertyValueFactory<>("planetOrSatellite"));

        tableView.getColumns().addAll(tcBuildingId, tcName, tcTotalUnits, tcAvailableUnits, tcPlanetOrSatellite);

        // Set the preferred height to show 4 rows
        tableView.setFixedCellSize(25); // Approximate row height in pixels
        tableView.setPrefHeight(4 * 25 + 28); // 4 rows + header height

        // Add title
        javafx.scene.control.Label label = new javafx.scene.control.Label("Available buildings on every planet/satellite");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Add title and table to VBox
        this.getChildren().addAll(label, tableView);
    }

    public TableView<Building> getTableView() {
        return tableView;
    }

    public void refreshBuildings() {
        List<Building> updatedBuildings = JDBCUtils.getAvailableBuildings();
        tableView.setItems(FXCollections.observableArrayList(updatedBuildings));
    }
}
