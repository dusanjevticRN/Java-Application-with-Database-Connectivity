package person.model.utility;

import person.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCUtils
{

    public static Connection connection = null;

    public static void connect()
    {
        Properties properties = new Properties();
        properties.put("user", "root"); // Default credentials for phpMyAdmin
        properties.put("password", "");

        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/person", "root", ""); // 3306 phpMyAdmin, create person database
        }

        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    //Neparameterizovana query funkcija za dobijanje svih planeta/satelita
    public static List<Planet> getPlanetsAndSatellites()
    {
        List<Planet> planets = new ArrayList<>();
        String query = "SELECT * FROM planetesateliti";

        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                Planet planet = new Planet(
                        resultSet.getInt("id"),
                        resultSet.getString("ime"),
                        resultSet.getString("tip"),
                        resultSet.getFloat("srednja_udaljenost_od_zvezde"),
                        resultSet.getFloat("najniza_temperatura"),
                        resultSet.getFloat("najvisa_temperatura"),
                        resultSet.getFloat("nivo_kiseonika"),
                        resultSet.getFloat("nivo_drugih_gasova"),
                        resultSet.getFloat("visina_gravitacije"),
                        resultSet.getFloat("brzina_orbite"),
                        resultSet.getInt("broj_smrtnih_slucajeva"),
                        resultSet.getInt("provedene_godine")
                );
                planets.add(planet);
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error fetching planets and satellites", e);
        }
        return planets;
    }

    //Neparametrizovana query funkcija za dobijanje svih gradjevinana na svim planetama/satelitima
    public static List<Building> getAvailableBuildings()
    {
        List<Building> buildings = new ArrayList<>();
        String query = "SELECT * FROM stambenezgrade";

        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                Building building = new Building(
                        resultSet.getInt("id"),
                        resultSet.getString("ime_zgrade"),
                        resultSet.getInt("broj_jedinica"),
                        resultSet.getInt("slobodne_jedinice"),
                        resultSet.getString("planetasatelit")
                );
                buildings.add(building);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error fetching buildings data", e);
        }
        return buildings;
    }

    //Neparametrizovana query funkcija za dobijanje informacija o svim habitable planetama/satelitima
    public static List<Planet> getHabitablePlanetsAndSatellites()
    {
        List<Planet> planets = new ArrayList<>();
        String query = "SELECT * FROM planetesateliti WHERE " +
                "srednja_udaljenost_od_zvezde BETWEEN 100 AND 200 AND " +
                "najniza_temperatura BETWEEN 150 AND 250 AND " +
                "najvisa_temperatura BETWEEN 250 AND 350 AND " +
                "(najvisa_temperatura - najniza_temperatura) <= 120 AND " +
                "nivo_kiseonika BETWEEN 15 AND 25 AND " +
                "(nivo_kiseonika + nivo_drugih_gasova) BETWEEN 90 AND 99 AND " +
                "visina_gravitacije >= 1000 AND " +
                "brzina_orbite BETWEEN 25 AND 35 AND " +
                "broj_smrtnih_slucajeva <= 20 AND " +
                "provedene_godine <= 1";

        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                Planet planet = new Planet(
                        resultSet.getInt("id"),
                        resultSet.getString("ime"),
                        resultSet.getString("tip"),
                        resultSet.getFloat("srednja_udaljenost_od_zvezde"),
                        resultSet.getFloat("najniza_temperatura"),
                        resultSet.getFloat("najvisa_temperatura"),
                        resultSet.getFloat("nivo_kiseonika"),
                        resultSet.getFloat("nivo_drugih_gasova"),
                        resultSet.getFloat("visina_gravitacije"),
                        resultSet.getFloat("brzina_orbite"),
                        resultSet.getInt("broj_smrtnih_slucajeva"),
                        resultSet.getInt("provedene_godine")
                );
                planets.add(planet);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error fetching habitable planets and satellites", e);
        }
        return planets;
    }

    //Parametrizovana query funkcija za dobijanje svih misija na osnovu selektovane planete/satelita
    public static List<Mission> getMissionsByLocation(String location)
    {
        List<Mission> missions = new ArrayList<>();
        String query = "SELECT * FROM misije WHERE lokacijamisije = ?";

        try
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, location);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                Mission mission = new Mission(
                        resultSet.getInt("id"),
                        resultSet.getDate("datum_pocetka").toString(),
                        resultSet.getDate("datum_zavrsetka").toString(),
                        resultSet.getString("status_misije"),
                        resultSet.getString("ime_misije"),
                        resultSet.getString("lokacijamisije")
                );
                missions.add(mission);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error fetching missions by location", e);
        }

        return missions;
    }

    //Parametrizovana query funkcija za dobijanje gradjevina na osnovu selektovane plaete/satelita
    public static List<Building> getBuildingsByPlanetOrSatellite(String name)
    {
        System.out.println(name);
        List<Building> buildings = new ArrayList<>();
        String query = "SELECT * FROM stambenezgrade WHERE planetasatelit = ?";

        try
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                Building building = new Building(
                        resultSet.getInt("id"),
                        resultSet.getString("ime_zgrade"),
                        resultSet.getInt("broj_jedinica"),
                        resultSet.getInt("slobodne_jedinice"),
                        resultSet.getString("planetasatelit")
                );
                buildings.add(building);
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error fetching buildings for planet/satellite", e);
        }
        return buildings;
    }

    //Parametrizovana funkcija za kupovanje travel tiketa
    public static void purchaseTicket(String username, String firstName, String lastName, String partnerUsername, String partnerFirstName, String partnerLastName, String building, String vehicle, LocalDate travelDate, String travelTime) {
        String insertQueryPutovanja = "INSERT INTO putovanja (kod_vozila, datum_polaska, traveler_ime, traveler_prezime, traveler_username, drugistanovnik_ime, drugistanovnik_prezime, zgrada, drugistanovnik_username, vreme_polaska) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String insertQueryKupovine = "INSERT INTO kupovine (ime_korisnika, prezime_korisnika, username, ime_zgrade, vozilo, datum_kupovine, datum_polaska, vreme_polaska) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String updateQuery = "UPDATE stambenezgrade SET slobodne_jedinice = slobodne_jedinice - 1 WHERE ime_zgrade = ?";
        String checkQuery = "SELECT slobodne_jedinice FROM stambenezgrade WHERE ime_zgrade = ?";
        String deleteQuery = "DELETE FROM stambenezgrade WHERE ime_zgrade = ?";

        try {
            // Insertujemo purchase Ticket u putovanja tabelu
            PreparedStatement insertStatementPutovanja = connection.prepareStatement(insertQueryPutovanja);
            insertStatementPutovanja.setString(1, vehicle);
            insertStatementPutovanja.setDate(2, Date.valueOf(travelDate));
            insertStatementPutovanja.setString(3, firstName);
            insertStatementPutovanja.setString(4, lastName);
            insertStatementPutovanja.setString(5, username);
            insertStatementPutovanja.setString(6, partnerFirstName);
            insertStatementPutovanja.setString(7, partnerLastName);
            insertStatementPutovanja.setString(8, building);
            insertStatementPutovanja.setString(9, partnerUsername);
            insertStatementPutovanja.setString(10, travelTime);
            insertStatementPutovanja.executeUpdate();

            // Insertujemo podatke o kupovini u kupovine tabelu
            PreparedStatement insertStatementKupovine = connection.prepareStatement(insertQueryKupovine);
            insertStatementKupovine.setString(1, firstName);
            insertStatementKupovine.setString(2, lastName);
            insertStatementKupovine.setString(3, username);
            insertStatementKupovine.setString(4, building);
            insertStatementKupovine.setString(5, vehicle);
            insertStatementKupovine.setDate(6, Date.valueOf(LocalDate.now())); // current date
            insertStatementKupovine.setDate(7, Date.valueOf(travelDate));
            insertStatementKupovine.setString(8, travelTime);
            insertStatementKupovine.executeUpdate();

            // Azuriramo broj stambenih jedinica, jer smo upravo kupili(zauzeli) jednu
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, building);
            updateStatement.executeUpdate();

            // Proveravamo broj slobodnih jedinica
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, building);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt("slobodne_jedinice") == 0) {
                // Brisemo entry ako je broj slobodnih jedinica 0
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                deleteStatement.setString(1, building);
                deleteStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error purchasing ticket", e);
        }
    }

    //Parametrizovana query funkcija za dobijanje informacija o istoriji kupovina za prijavljenog korisnika
    public static List<Purchase> getUserPurchases(String username)
    {
        List<Purchase> purchases = new ArrayList<>();
        String query = "SELECT * FROM kupovine WHERE username = ?";

        try
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Purchase purchase = new Purchase(
                        resultSet.getInt("id"),
                        resultSet.getString("ime_korisnika"),
                        resultSet.getString("prezime_korisnika"),
                        resultSet.getString("username"),
                        resultSet.getString("ime_zgrade"),
                        resultSet.getString("vozilo"),
                        resultSet.getDate("datum_kupovine").toString(),
                        resultSet.getDate("datum_polaska").toString(),
                        resultSet.getString("vreme_polaska")
                );
                purchases.add(purchase);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error fetching user purchases", e);
        }
        return purchases;
    }

    // Parametrizovana query funkcija za registrovanje novog korisnika ako je to moguce
    public static void insertIntoUsers(String firstName, String lastName, String username, String password)
    {
        String checkQuery = "SELECT 1 FROM korisnici WHERE username = ?";
        String insertQuery = "INSERT INTO korisnici (ime, prezime, username, password) VALUES (?, ?, ?, ?)";

        try
        {
            // Check if username already exists
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next())
            {
                throw new RuntimeException("Username already exists. Please choose a different username.");
            }

            // Insert the new user
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            connection.setAutoCommit(false); // Manually handle transactions
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.executeUpdate();
            connection.commit();
        }

        catch (SQLException e)
        {
            try
            {
                connection.rollback(); // Rollback in case of error
            }

            catch (SQLException rollbackException)
            {
                throw new RuntimeException("Error rolling back transaction", rollbackException);
            }
            throw new RuntimeException(e);
        }
    }

    //Parametrizovana query funkcija za proveravanje validnosti osobe koja se loginuje
    public static boolean validateUser(String username, String password)
    {
        String query = "SELECT 1 FROM korisnici WHERE username = ? AND password = ?";

        try
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();  // returns true if there's at least one result
        }

        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error validating user", e);
        }
    }

    //Parametrizovana query funkcija koja uzima ime na osnovu username-a
    public static String getFirstNameByUsername(String username)
    {
        String query = "SELECT ime FROM korisnici WHERE username = ?";

        try
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                return resultSet.getString("ime");
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error fetching first name by username", e);
        }
        return null;
    }

    //Parametrizovana query funkcija koja uzima username na osnovu prezimena
    public static String getKorisnikLastName(String username)
    {
        String query = "SELECT prezime FROM korisnici WHERE username = ?";
        try
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                return resultSet.getString("prezime");
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error fetching last name by username", e);
        }
        return null;
    }


    private JDBCUtils() {
    }
}
