package nikolay.romanov.accessors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nikolay.romanov.models.LicensePlate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LicensePlateDataAccessor {
    private final Connection connection;

    public LicensePlateDataAccessor(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<LicensePlate> getAllLicensePlates() {
        List<LicensePlate> licensePlates = new ArrayList<>();
        ResultSet resultSet;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM LicensePlates");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String license = resultSet.getString("license");
                String country = resultSet.getString("country");
                String region = resultSet.getString("region");

                licensePlates.add(new LicensePlate(id, license, country, region));
            }
        } catch (SQLException sqlException) {
            System.out.println("Error while getting all license plates.");
        }

        return FXCollections.observableList(licensePlates);
    }

    public void updateLicensePlateLicense(int id, String license) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE LicensePlates SET license = \"" + license + "\" WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully updated license plate license to " + license +  " for license plate with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while updating license plate license to " + license +  " for license plate with id " + id);
        }
    }

    public void updateLicensePlateCountry(int id, String country) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE LicensePlates SET country = \"" + country + "\" WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully updated license plate country to " + country +  " for license plate with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while updating license plate country to " + country +  " for license plate with id " + id);
        }
    }

    public void updateLicensePlateRegion(int id, String region) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE LicensePlates SET region = \"" + region + "\" WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully updated license plate region to " + region +  " for license plate with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while updating license plate region to " + region +  " for license plate with id " + id);
        }
    }

    public void deleteLicensePlate(int id) {
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM LicensePlates WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully deleted license plate with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while deleting license plate with id " + id);
        }
    }

    public void insertLicensePlate(String license, String country, String region) {
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO LicensePlates (license, country, region) VALUES (\"" + license + "\", \"" + country + "\", \"" + region + "\")";

            statement.executeUpdate(query);
        } catch (SQLException sqlException) {
            System.out.println("Error while inserting new license plate.");
        }
    }
}
