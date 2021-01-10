package nikolay.romanov.accessors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nikolay.romanov.models.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonDataAccessor {
    private final Connection connection;

    public PersonDataAccessor(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<Person> getAllPeople() {
        List<Person> people = new ArrayList<>();
        ResultSet resultSet;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM People");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");

                people.add(new Person(id, firstName, lastName, age));
            }
        } catch (SQLException sqlException) {
            System.out.println("Error while getting all people.");
        }

        return FXCollections.observableList(people);
    }

    public void updatePersonFirstName(int id, String firstName) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE People SET first_name = \"" + firstName + "\" WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully updated person first name to " + firstName +  " for person with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while updating person first name to " + firstName +  " for person with id " + id);
        }
    }

    public void updatePersonLastName(int id, String lastName) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE People SET last_name = \"" + lastName + "\" WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully updated person last name to " + lastName +  " for person with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while updating person last name to " + lastName +  " for person with id " + id);
        }
    }

    public void updatePersonAge(int id, int age) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE People SET age = " + age + " WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully updated person age to " + age +  " for person with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while updating person age to " + age +  " for person with id " + id);
        }
    }

    public void deletePerson(int id) {
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM People WHERE id = " + id;

            statement.executeUpdate(query);
            System.out.println("Successfully deleted person with id " + id);
        } catch (SQLException sqlException) {
            System.out.println("Error while deleting person with id " + id);
        }
    }

    public void insertPerson(String firstName, String lastName, int age) {
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO People (first_name, last_name, age) VALUES (\"" + firstName + "\", \"" + lastName + "\", " + age + ")";

            statement.executeUpdate(query);
        } catch (SQLException sqlException) {
            System.out.println("Error while inserting new person.");
        }
    }
}
