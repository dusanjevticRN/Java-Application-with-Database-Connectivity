package person.view;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import person.model.Planet;

import java.util.List;

public class HabitablePlanetTable extends VBox {
    private final TableView<Planet> tableView;

    public HabitablePlanetTable(List<Planet> values) {
        tableView = new TableView<>(FXCollections.observableArrayList(values));

        TableColumn<Planet, Integer> tcPlanetId = new TableColumn<>("ID");
        TableColumn<Planet, String> tcName = new TableColumn<>("Name");
        TableColumn<Planet, String> tcType = new TableColumn<>("Type");
        TableColumn<Planet, Float> tcDistance = new TableColumn<>("Distance from Star");
        TableColumn<Planet, Float> tcMinTemp = new TableColumn<>("Min Temperature");
        TableColumn<Planet, Float> tcMaxTemp = new TableColumn<>("Max Temperature");
        TableColumn<Planet, Float> tcOxygenLevel = new TableColumn<>("Oxygen Level");
        TableColumn<Planet, Float> tcOtherGasLevel = new TableColumn<>("Other Gas Level");
        TableColumn<Planet, Float> tcGravityHeight = new TableColumn<>("Gravity Height");
        TableColumn<Planet, Float> tcOrbitSpeed = new TableColumn<>("Orbit Speed");
        TableColumn<Planet, Integer> tcDeathCount = new TableColumn<>("Death Count");
        TableColumn<Planet, Integer> tcYearsSpent = new TableColumn<>("Years Spent");

        tcPlanetId.setPrefWidth(40);
        tcName.setPrefWidth(100);
        tcType.setPrefWidth(80);
        tcDistance.setPrefWidth(140);
        tcMinTemp.setPrefWidth(130);
        tcMaxTemp.setPrefWidth(130);
        tcOxygenLevel.setPrefWidth(130);
        tcOtherGasLevel.setPrefWidth(130);
        tcGravityHeight.setPrefWidth(130);
        tcOrbitSpeed.setPrefWidth(120);
        tcDeathCount.setPrefWidth(100);
        tcYearsSpent.setPrefWidth(100);

        tcPlanetId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tcDistance.setCellValueFactory(new PropertyValueFactory<>("distanceFromStar"));
        tcMinTemp.setCellValueFactory(new PropertyValueFactory<>("minTemperature"));
        tcMaxTemp.setCellValueFactory(new PropertyValueFactory<>("maxTemperature"));
        tcOxygenLevel.setCellValueFactory(new PropertyValueFactory<>("oxygenLevel"));
        tcOtherGasLevel.setCellValueFactory(new PropertyValueFactory<>("otherGasLevel"));
        tcGravityHeight.setCellValueFactory(new PropertyValueFactory<>("gravityHeight"));
        tcOrbitSpeed.setCellValueFactory(new PropertyValueFactory<>("orbitSpeed"));
        tcDeathCount.setCellValueFactory(new PropertyValueFactory<>("deathCount"));
        tcYearsSpent.setCellValueFactory(new PropertyValueFactory<>("yearsSpent"));

        tableView.getColumns().addAll(tcPlanetId, tcName, tcType, tcDistance, tcMinTemp, tcMaxTemp, tcOxygenLevel, tcOtherGasLevel, tcGravityHeight, tcOrbitSpeed, tcDeathCount, tcYearsSpent);

        // Set the preferred height to show 4 rows
        tableView.setFixedCellSize(25); // Approximate row height in pixels
        tableView.setPrefHeight(4 * 25 + 28); // 4 rows + header height

        // Add title
        Label label = new Label("Habitable Planets/Satellites");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Add title and table to VBox
        this.getChildren().addAll(label, tableView);
    }

    public TableView<Planet> getTableView() {
        return tableView;
    }

    public void setItems(List<Planet> items) {
        tableView.setItems(FXCollections.observableArrayList(items));
    }

    public void setRowSelectionHandler(PlanetRowSelectionHandler handler) {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handler.onRowSelected(newSelection);
            }
        });
    }

    public interface PlanetRowSelectionHandler {
        void onRowSelected(Planet planet);
    }
}
