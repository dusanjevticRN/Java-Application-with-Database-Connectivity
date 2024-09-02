package person.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.Getter;
import lombok.Setter;
import person.controller.PurchaceControl;
import person.controller.RegisterControl;
import person.controller.LoginControl;
import person.model.Planet;
import person.model.Building;
import person.model.Purchase;
import person.model.Mission;
import person.model.utility.JDBCUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
public class MainView extends Stage
{

    //Main layout kontejner
    private final BorderPane root = new BorderPane();

    //Tabele za prikaz podataka
    private final PlanetTable planetTable = new PlanetTable(JDBCUtils.getPlanetsAndSatellites());
    private final HabitablePlanetTable habitablePlanetTable = new HabitablePlanetTable(List.of());
    private final BuildingTable buildingTable = new BuildingTable(JDBCUtils.getAvailableBuildings());
    private final BuildingsOnHabitablePlanetTable selectedBuildingTable = new BuildingsOnHabitablePlanetTable(List.of());
    private final MissionTable missionTable = new MissionTable(List.of());
    private final AllUserPurchasesTable userPurchasesTable = new AllUserPurchasesTable(List.of());

    //TextField u kome cuvamo selektovanu zgradu, to cemo koristiti u purchase ticket formi
    private final TextField tfSelectedBuilding = new TextField();

    // Delovi registration forme
    private final TextField tfRegFirstName = new TextField();
    private final TextField tfRegLastName = new TextField();
    private final TextField tfRegUsername = new TextField();
    private final TextField tfRegPassword = new TextField();
    private final Button btRegister = new Button("Register");

    //Delovi login forme
    private final TextField tfLoginUsername = new TextField();
    private final TextField tfLoginPassword = new TextField();
    private final Button btLogin = new Button("Login");
    private final Button btLogout = new Button("Logout");

    //Statusni label, koristimo ga za statusne poruke, login register...
    private final Label statusLabel = new Label();

    //Label koji koristimo za prikazivanje username-a logovanog korisnika
    private final Label loginStatusLabel = new Label("Not logged in");

    //Dugme kojim ucitavamo habitable planete/satelite
    private final Button btLoadHabitablePlanets = new Button("Load Habitable Planets/Satellites");

    //Polja za unoseje podataka o logovanom korisniku kada se kupuje tiket
    private final TextField tfFirstName = new TextField();
    private final TextField tfLastName = new TextField();
    private final TextField tfUsername = new TextField();
    private final DatePicker dpTravelDate = new DatePicker();
    private final TextField tfTravelTime = new TextField();

    //Polja za unosenje podataka o partneru koji putuje sa logovanim korisnikom
    private final TextField tfPartnerFirstName = new TextField();
    private final TextField tfPartnerLastName = new TextField();
    private final TextField tfPartnerUsername = new TextField();

    // ComboBox koji koristimo za vozila
    private final ComboBox<String> cbVehicle = new ComboBox<>();

    //Purchase button koji koristimo za kupovinu tiketa
    private final Button btPurchase = new Button("Purchase");

    public MainView()
    {
        super.setTitle("Baze podataka domaci 2");

        //Stavljamo listener na buttone, kada se ti buttoni kliknu rade se kontroleri
        this.btRegister.setOnAction(new RegisterControl(this.tfRegFirstName, this.tfRegLastName, this.tfRegUsername, this.tfRegPassword));
        this.btLogin.setOnAction(new LoginControl(this.tfLoginUsername, this.tfLoginPassword, this));
        this.btLogout.setOnAction(e -> logout());
        this.btLoadHabitablePlanets.setOnAction(e -> loadHabitablePlanets());

        //Odavde pa do dole je sve vezano za UI
        this.tfRegFirstName.setPrefWidth(150);
        this.tfRegLastName.setPrefWidth(150);
        this.tfRegUsername.setPrefWidth(150);
        this.tfRegPassword.setPrefWidth(150);

        this.tfLoginUsername.setPrefWidth(150);
        this.tfLoginPassword.setPrefWidth(150);

        this.btLogout.setDisable(true);

        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.TOP_CENTER);

        HBox habitableBox = new HBox(10, btLoadHabitablePlanets);
        habitableBox.setAlignment(Pos.CENTER_LEFT);
        habitableBox.setPadding(new Insets(0, 0, 10, 0));

        vbox.getChildren().addAll(
                planetTable,
                missionTable,  // Add the mission table here
                buildingTable,
                habitableBox,
                habitablePlanetTable,
                selectedBuildingTable,
                getUserAndPartnerForm(),
                userPurchasesTable
        );

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        this.root.setCenter(scrollPane);
        this.root.setTop(this.topBox());
        this.root.setRight(this.authBox());

        planetTable.getTableView().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> handlePlanetSelection(newSelection));
        habitablePlanetTable.setRowSelectionHandler(this::handleRowSelection);
        selectedBuildingTable.getTableView().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> handleBuildingSelection(newSelection));

        super.setScene(new Scene(this.root, 1200, 800));

        // Initialize user form fields based on login status
        initializeUserForm();

        // Initialize vehicle ComboBox
        initializeVehicleComboBox();

        // Set the action for the Purchase button
        btPurchase.setOnAction(new PurchaceControl(tfFirstName, tfLastName, tfUsername, dpTravelDate, tfTravelTime, tfSelectedBuilding, cbVehicle, tfPartnerFirstName, tfPartnerLastName, tfPartnerUsername, this));
    }

    private VBox topBox()
    {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.TOP_LEFT);

        // Login status label and logout button
        HBox loginStatusBox = new HBox(10, loginStatusLabel, btLogout);
        loginStatusBox.setAlignment(Pos.CENTER_LEFT);
        loginStatusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        vbox.getChildren().addAll(loginStatusBox);

        return vbox;
    }

    private GridPane authBox()
    {
        GridPane gridPane = new GridPane();

        // Registration fields
        Label regLabel = new Label("Register");
        regLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        gridPane.addRow(0, regLabel, new Label(""));

        gridPane.addRow(1, new Label("First name:"), this.tfRegFirstName);
        gridPane.addRow(2, new Label("Last name:"), this.tfRegLastName);
        gridPane.addRow(3, new Label("Username:"), this.tfRegUsername);
        gridPane.addRow(4, new Label("Password:"), this.tfRegPassword);
        gridPane.add(this.btRegister, 1, 5);

        // Login fields
        Label loginLabel = new Label("Login");
        loginLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        gridPane.addRow(6, loginLabel, new Label(""));

        gridPane.addRow(7, new Label("Username:"), this.tfLoginUsername);
        gridPane.addRow(8, new Label("Password:"), this.tfLoginPassword);
        gridPane.add(this.btLogin, 1, 9);

        // Status label
        gridPane.add(this.statusLabel, 0, 10, 2, 1);

        gridPane.setVgap(20);
        gridPane.setHgap(20);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);

        return gridPane;
    }

    //Funkcija kojom apdejtujemo UI na osnovu login statusa
    public void setLoginStatus(String username)
    {
        if (username == null || username.isEmpty())
        {
            loginStatusLabel.setText("Not logged in");
            btLogin.setDisable(false);
            btLogout.setDisable(true);
        }
        else
        {
            loginStatusLabel.setText("Logged in as " + username);
            btLogin.setDisable(true);
            btLogout.setDisable(false);
            refreshUserPurchases(username); // Refresh purchases on login
        }
        initializeUserForm(); // Update user form fields based on login status
    }

    //logout proces, stavljamo null i obavestavamo korisnika da smo se logoutovali
    private void logout()
    {
        setLoginStatus(null);
        showAlert(Alert.AlertType.INFORMATION, "Logout Successful", "Logged out successfully.");
        userPurchasesTable.clearPurchases(); // Clear the purchases table on logout
    }

    private void showAlert(Alert.AlertType alertType, String title, String message)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadHabitablePlanets()
    {
        List<Planet> habitablePlanets = JDBCUtils.getHabitablePlanetsAndSatellites();
        habitablePlanetTable.getTableView().setItems(FXCollections.observableArrayList(habitablePlanets));
    }

    private void handlePlanetSelection(Planet planet)
    {
        if (planet != null)
        {
            List<Mission> missions = JDBCUtils.getMissionsByLocation(planet.getName());
            missionTable.refreshMissions(missions);
        }
    }

    private void handleRowSelection(Planet planet)
    {
        if (planet != null)
        {
            List<Building> buildings = JDBCUtils.getBuildingsByPlanetOrSatellite(planet.getName());
            selectedBuildingTable.getTableView().setItems(FXCollections.observableArrayList(buildings));
        }
    }

    private void handleBuildingSelection(Building building)
    {
        if (building != null) {
            tfSelectedBuilding.setText(building.getName());
        }
    }

    public void refreshAvailableBuildings()
    {
        handleRowSelection(habitablePlanetTable.getTableView().getSelectionModel().getSelectedItem());
        List<Building> updatedBuildings = JDBCUtils.getAvailableBuildings();
        buildingTable.getTableView().setItems(FXCollections.observableArrayList(updatedBuildings));
    }

    public void refreshUserPurchases(String username)
    {
        List<Purchase> updatedPurchases = JDBCUtils.getUserPurchases(username);
        userPurchasesTable.getTableView().setItems(FXCollections.observableArrayList(updatedPurchases));
    }

    private VBox getUserAndPartnerForm()
    {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER_LEFT);

        // User form
        GridPane userGrid = new GridPane();
        userGrid.setVgap(10);
        userGrid.setHgap(10);

        // Set fields to be non-editable
        tfFirstName.setEditable(false);
        tfLastName.setEditable(false);
        tfUsername.setEditable(false);

        userGrid.add(new Label("First name:"), 0, 0);
        userGrid.add(tfFirstName, 1, 0);
        userGrid.add(new Label("Last name:"), 0, 1);
        userGrid.add(tfLastName, 1, 1);
        userGrid.add(new Label("Username:"), 0, 2);
        userGrid.add(tfUsername, 1, 2);
        userGrid.add(new Label("Travel Date:"), 0, 3);
        userGrid.add(dpTravelDate, 1, 3);
        userGrid.add(new Label("Travel Time:"), 0, 4);
        userGrid.add(tfTravelTime, 1, 4);
        userGrid.add(new Label("Time format: hh:mm:ss"), 2, 4);

        // Partner form
        GridPane partnerGrid = new GridPane();
        partnerGrid.setVgap(10);
        partnerGrid.setHgap(10);

        partnerGrid.add(new Label("First name:"), 0, 0);
        partnerGrid.add(tfPartnerFirstName, 1, 0);
        partnerGrid.add(new Label("Last name:"), 0, 1);
        partnerGrid.add(tfPartnerLastName, 1, 1);
        partnerGrid.add(new Label("Username:"), 0, 2);
        partnerGrid.add(tfPartnerUsername, 1, 2);

        // Labels with bold font
        Label purchaseStationLabel = new Label("Purchase Station");
        purchaseStationLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Label travelerLabel = new Label("Traveler");
        travelerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Label otherResidentLabel = new Label("Other Resident");
        otherResidentLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        HBox formBox = new HBox(70, new VBox(15, travelerLabel, userGrid), new VBox(15, otherResidentLabel, partnerGrid));
        formBox.setAlignment(Pos.CENTER_LEFT);

        // Selected building
        tfSelectedBuilding.setEditable(false);

        // Update VBox to include the purchase button
        VBox vehicleBox = new VBox(10, new Label("Select vehicle:"), cbVehicle);
        btPurchase.setPrefWidth(150); // Make the purchase button slightly bigger

        vbox.getChildren().addAll(purchaseStationLabel, formBox, new Label("Selected Building:"), tfSelectedBuilding, vehicleBox, btPurchase);

        // Add event handlers for partner fields
        tfPartnerFirstName.setEditable(false);
        tfPartnerLastName.setEditable(false);
        tfPartnerUsername.setEditable(false);

        tfPartnerFirstName.setOnMouseClicked(e -> showLoginPopup());
        tfPartnerLastName.setOnMouseClicked(e -> showLoginPopup());
        tfPartnerUsername.setOnMouseClicked(e -> showLoginPopup());

        return vbox;
    }

    private void showLoginPopup()
    {
        if ("Not logged in".equals(loginStatusLabel.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Required");
            alert.setHeaderText(null);
            alert.setContentText("Please log in to be able to fill this form.");
            alert.showAndWait();
        }
    }

    private void initializeUserForm()
    {
        String loggedInUsername = getLoggedInUsername();

        if (loggedInUsername != null && !loggedInUsername.isEmpty())
        {
            tfFirstName.setText(JDBCUtils.getFirstNameByUsername(loggedInUsername));
            tfLastName.setText(JDBCUtils.getKorisnikLastName(loggedInUsername));
            tfUsername.setText(loggedInUsername);

            tfFirstName.setEditable(false);
            tfLastName.setEditable(false);
            tfUsername.setEditable(false);

            tfFirstName.setOnMouseClicked(null);
            tfLastName.setOnMouseClicked(null);
            tfUsername.setOnMouseClicked(null);

            tfPartnerFirstName.setEditable(true);
            tfPartnerLastName.setEditable(true);
            tfPartnerUsername.setEditable(true);

            tfPartnerFirstName.setOnMouseClicked(null);
            tfPartnerLastName.setOnMouseClicked(null);
            tfPartnerUsername.setOnMouseClicked(null);
        }
        else
        {
            tfFirstName.setText("");
            tfLastName.setText("");
            tfUsername.setText("");

            tfFirstName.setEditable(false);
            tfLastName.setEditable(false);
            tfUsername.setEditable(false);

            setFieldClickHandler(tfFirstName);
            setFieldClickHandler(tfLastName);
            setFieldClickHandler(tfUsername);

            tfPartnerFirstName.setEditable(false);
            tfPartnerLastName.setEditable(false);
            tfPartnerUsername.setEditable(false);

            setFieldClickHandler(tfPartnerFirstName);
            setFieldClickHandler(tfPartnerLastName);
            setFieldClickHandler(tfPartnerUsername);
        }
    }

    private String getLoggedInUsername()
    {
        String loginStatus = loginStatusLabel.getText();

        if (loginStatus.startsWith("Logged in as "))
        {
            return loginStatus.substring(13);
        }
        return null;
    }

    private void setFieldClickHandler(TextField textField)
    {
        textField.setOnMouseClicked(event -> {
            if (getLoggedInUsername() == null) {
                showAlert(Alert.AlertType.WARNING, "Not Logged In", "Please log in to automatically fill the data.");
            }
        });
    }

    private void initializeVehicleComboBox()
    {
        List<String> vehicles = IntStream.rangeClosed(105, 300)
                .mapToObj(i -> "vx-" + i)
                .collect(Collectors.toList());
        cbVehicle.setItems(FXCollections.observableArrayList(vehicles));
        cbVehicle.setVisibleRowCount(5);
        cbVehicle.getSelectionModel().selectFirst(); // Automatically select the first item
    }
}
