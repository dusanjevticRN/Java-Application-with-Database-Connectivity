package person.view;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import person.model.Mission;

import java.util.List;

public class MissionTable extends VBox {
    private final TableView<Mission> tableView;

    public MissionTable(List<Mission> values) {
        tableView = new TableView<>(FXCollections.observableArrayList(values));

        TableColumn<Mission, Integer> tcMissionId = new TableColumn<>("ID");
        TableColumn<Mission, String> tcStartDate = new TableColumn<>("Start Date");
        TableColumn<Mission, String> tcEndDate = new TableColumn<>("End Date");
        TableColumn<Mission, String> tcMissionStatus = new TableColumn<>("Mission Status");
        TableColumn<Mission, String> tcMissionName = new TableColumn<>("Mission Name");
        TableColumn<Mission, String> tcMissionLocation = new TableColumn<>("Mission Location");

        tcMissionId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        tcEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        tcMissionStatus.setCellValueFactory(new PropertyValueFactory<>("missionStatus"));
        tcMissionName.setCellValueFactory(new PropertyValueFactory<>("missionName"));
        tcMissionLocation.setCellValueFactory(new PropertyValueFactory<>("missionLocation"));

        tableView.getColumns().addAll(tcMissionId, tcStartDate, tcEndDate, tcMissionStatus, tcMissionName, tcMissionLocation);

        // Fit columns to text
        fitColumnsToText(tcMissionId, tcStartDate, tcEndDate, tcMissionStatus, tcMissionName, tcMissionLocation);

        // Set the preferred height to show 4 rows
        tableView.setFixedCellSize(25); // Approximate row height in pixels
        tableView.setPrefHeight(4 * 25 + 28); // 4 rows + header height

        // Add title
        javafx.scene.control.Label label = new javafx.scene.control.Label("Executed missions on selected planet/satellite");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Add title and table to VBox
        this.getChildren().addAll(label, tableView);
    }

    private void fitColumnsToText(TableColumn<?, ?>... columns) {
        for (TableColumn<?, ?> column : columns) {
            column.setMinWidth(column.getText().length() * 8); // Approximate width
        }
    }

    public TableView<Mission> getTableView() {
        return tableView;
    }

    public void refreshMissions(List<Mission> missions) {
        tableView.setItems(FXCollections.observableArrayList(missions));
    }
}
