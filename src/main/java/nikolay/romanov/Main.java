package nikolay.romanov;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nikolay.romanov.accessors.CarDataAccessor;
import nikolay.romanov.accessors.LicensePlateDataAccessor;
import nikolay.romanov.accessors.PersonDataAccessor;
import nikolay.romanov.models.DatabaseAccess;
import nikolay.romanov.models.ViewTitle;
import nikolay.romanov.views.CarsView;
import nikolay.romanov.views.LicensePlatesView;
import nikolay.romanov.views.PeopleView;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main extends Application {
    private final Properties properties = new Properties();
    private Connection connection;
    private PersonDataAccessor personDataAccessor;
    private LicensePlateDataAccessor licensePlateDataAccessor;
    private CarDataAccessor carDataAccessor;

    @Override
    public void start(Stage stage) {
        loadApplicationProperties();
        openMySQLConnection();
        initializeDataAccessors();
        initializeUIElements(stage);
    }

    private void loadApplicationProperties() {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream applicationPropertiesFile = loader.getResourceAsStream("application.properties");
            properties.load(applicationPropertiesFile);
        } catch (IOException ioException) {
            System.out.println("Error: Cannot read application.properties file. Cannot start the project without application.properties file. Exiting...");
            System.exit(-1);
        }
    }

    private void openMySQLConnection() {
        DatabaseAccess databaseAccess = new DatabaseAccess(properties.getProperty("db_url"), properties.getProperty("db_user"), properties.getProperty("db_password"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(databaseAccess.getUrl(), databaseAccess.getUser(), databaseAccess.getPassword());
        } catch (SQLException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }

        initializeDataAccessors();
    }

    private void initializeDataAccessors() {
        this.personDataAccessor = new PersonDataAccessor(connection);
        this.licensePlateDataAccessor = new LicensePlateDataAccessor(connection);
        this.carDataAccessor = new CarDataAccessor(connection);
    }

    private void switchScreen(VBox vbox, List<Node> currentNodes, List<Node> elements, Class<?> viewClass, Stage stage, String stageTitle) {
        stage.setTitle(stageTitle);
        vbox.getChildren().removeAll(currentNodes);
        currentNodes.clear();
        currentNodes.addAll(elements);
        vbox.getChildren().addAll(currentNodes);

        System.out.println("Switched to view " + viewClass.getSimpleName());
    }

    private void initializeUIElements(Stage stage) {
        GridPane screenSelectorButtons = new GridPane();
        PeopleView peopleView = new PeopleView(personDataAccessor);
        LicensePlatesView licensePlatesView = new LicensePlatesView(licensePlateDataAccessor);
        CarsView carsView = new CarsView(carDataAccessor);
        VBox vbox = new VBox();
        List<Node> currentNodes = new ArrayList<>();
        Scene scene = new Scene(new Group());

        stage.setTitle("Nikolay Romanov's oopdb assigment work");
        stage.setWidth(768);
        stage.setHeight(1024);

        Button peopleScreenButton = new Button("People screen");
        peopleScreenButton.setOnAction(event -> switchScreen(vbox, currentNodes, peopleView.getElements(), PeopleView.class, stage, ViewTitle.PEOPLE_VIEW.getValue()));

        Button licensePlatesScreenButton = new Button("License plates screen");
        licensePlatesScreenButton.setOnAction(event -> switchScreen(vbox, currentNodes, licensePlatesView.getElements(), LicensePlatesView.class, stage, ViewTitle.LICENSE_PLATES_VIEW.getValue()));

        Button carsScreenButton = new Button("Cars screen");
        carsScreenButton.setOnAction(event -> switchScreen(vbox, currentNodes, carsView.getElements(), CarsView.class, stage, ViewTitle.CARS_VIEW.getValue()));

        screenSelectorButtons.add(peopleScreenButton, 0, 1);
        screenSelectorButtons.add(licensePlatesScreenButton, 1, 1);
        screenSelectorButtons.add(carsScreenButton, 2, 1);

        screenSelectorButtons.setTranslateX(200);
        screenSelectorButtons.setHgap(10);
        screenSelectorButtons.setVgap(10);
        screenSelectorButtons.setPadding(new Insets(25, 25, 25, 25));

        vbox.getChildren().add(screenSelectorButtons);

        ((Group) scene.getRoot()).getChildren().add(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
