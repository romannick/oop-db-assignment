package nikolay.romanov.accessors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nikolay.romanov.models.Car;
import nikolay.romanov.models.LicensePlate;
import nikolay.romanov.models.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarDataAccessor {
    private final Connection connection;

    public CarDataAccessor(Connection connection) {
        this.connection = connection;
    }

    private LicensePlate getLicensePlateByLicense(String license) {
        LicensePlate licensePlate = null;
        ResultSet resultSet;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM LicensePlates WHERE license = \"" + license + "\"");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String licenseStr = resultSet.getString("license");
                String country = resultSet.getString("country");
                String region = resultSet.getString("region");

                licensePlate = new LicensePlate(id, licenseStr, country, region);
            }
        } catch (SQLException sqlException) {
            System.out.println("Error while getting license plate with license " + license);
        }

        return licensePlate;
    }

    private Person getPersonByFirstLastName(String firstLastName) {
        Person person = null;
        ResultSet resultSet;
        String[] nameSplit = firstLastName.split(" ");

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM People WHERE first_name = \"" + nameSplit[0] + "\" AND last_name = \"" + nameSplit[1] + "\"");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");

                person = new Person(id, firstName, lastName, age);
            }
        } catch (SQLException sqlException) {
            System.out.println("Error while getting person with name " + firstLastName);
        }

        return person;
    }

    private Person getPersonById(int id) {
        Person person = null;
        ResultSet resultSet;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM People WHERE id = " + id);

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");

                person = new Person(id, firstName, lastName, age);
            }
        } catch (SQLException sqlException) {
            System.out.println("Error while getting person with id " + id);
        }

        return person;
    }

    private LicensePlate getLicensePlateById(int id) {
        LicensePlate licensePlate = null;
        ResultSet resultSet;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM LicensePlates WHERE id = " + id);

            while (resultSet.next()) {
                String license = resultSet.getString("license");
                String country = resultSet.getString("country");
                String region = resultSet.getString("region");

                licensePlate = new LicensePlate(id, license, country, region);
            }
        } catch (SQLException sqlException) {
            System.out.println("Error while getting license plate with id " + id);
        }

        return licensePlate;
    }

    public ObservableList<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        ResultSet resultSet;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Cars");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int ownerId = resultSet.getInt("owner_id");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                int horsePower = resultSet.getInt("horse_power");
                int licensePlateId = resultSet.getInt("license_plate_id");

                Person owner = getPersonById(ownerId);
                LicensePlate licensePlate = getLicensePlateById(licensePlateId);

                cars.add(new Car(id, owner, brand, model, year, horsePower, licensePlate));
            }
        } catch (SQLException sqlException) {
            System.out.println("Error while getting all cars.");
        }

        return FXCollections.observableList(cars);
    }

    public void updateCarOwner(int id, String owner) {
        Person ownerPerson = getPersonByFirstLastName(owner);

        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE Cars SET owner_id = " + ownerPerson.getId() + " WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully updated car owner to " + ownerPerson.getFirstName() + " " + ownerPerson.getLastName() + " for car with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while updating car owner to " + ownerPerson.getFirstName() + " " + ownerPerson.getLastName() + " for car with id " + id);
        }
    }

    public void updateCarBrand(int id, String brand) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE Cars SET brand = \"" + brand + "\" WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully updated car brand to " + brand + " for car with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while updating car brand to " + brand + " for car with id " + id);
        }
    }

    public void updateCarModel(int id, String model) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE Cars SET model = \"" + model + "\" WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully updated car model to " + model + " for car with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while updating car model to " + model + " for car with id " + id);
        }
    }

    public void updateCarYear(int id, int year) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE Cars SET year = \"" + year + "\" WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully updated car year to " + year + " for car with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while updating car year to " + year + " for car with id " + id);
        }
    }

    public void updateCarHorsePower(int id, int horsePower) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE Cars SET horse_power = \"" + horsePower + "\" WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully updated car horse power to " + horsePower + " for car with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while updating car horse power to " + horsePower + " for car with id " + id);
        }
    }

    public void updateCarLicensePlate(int id, String licensePlate) {
        LicensePlate carLicensePlate = getLicensePlateByLicense(licensePlate);

        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE Cars SET license_plate_id = " + carLicensePlate.getId() + " WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully updated car license plate to " + carLicensePlate.getLicense() + " for car with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while updating car license plate to " + carLicensePlate.getLicense() + " for car with id " + id);
        }
    }

    public void deleteCar(int id) {
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM Cars WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully deleted car with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while deleting car with id " + id);
        }
    }

    public void insertCar(String owner, String brand, String model, int year, int horsePower, String licensePlate) {
        try {
            Statement statement = connection.createStatement();
            Person ownerPerson = getPersonByFirstLastName(owner);
            LicensePlate licensePlateEntity = getLicensePlateByLicense(licensePlate);

            String query = "INSERT INTO Cars (owner_id, brand, model, year, horse_power, license_plate_id) VALUES (" + ownerPerson.getId() + ", \"" + brand + "\", \"" + model + "\", " + year + ", " + horsePower + ", " + licensePlateEntity.getId() + ")";

            statement.executeUpdate(query);
        } catch (SQLException sqlException) {
            System.out.println("Error while inserting new car.");
        }
    }
}
