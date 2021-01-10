package nikolay.romanov.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import nikolay.romanov.accessors.CarDataAccessor;
import nikolay.romanov.models.Car;
import nikolay.romanov.models.Person;

import java.util.Arrays;
import java.util.List;

public class CarsView {
    private final CarDataAccessor carDataAccessor;

    private final GridPane tableGrid = new GridPane();
    private final TableView<Car> table = new TableView<>();
    private final GridPane deleteButton = new GridPane();
    private final GridPane insertForm = new GridPane();

    public CarsView(CarDataAccessor carDataAccessor) {
        this.carDataAccessor = carDataAccessor;

        initializeCarTable();
        initializeDeleteButton();
        initializeInsertForm();
    }

    private void initializeCarTable() {
        tableGrid.setTranslateX(125);
        tableGrid.setHgap(10);
        tableGrid.setVgap(10);
        tableGrid.setPadding(new Insets(25, 25, 25, 25));

        Text formTitle = new Text("Cars table");
        formTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        tableGrid.add(formTitle, 0, 0, 2, 1);
        tableGrid.add(table, 0, 1);

        table.setMaxWidth(490);
        ObservableList<Car> carsTableData = carDataAccessor.getAllCars();

        TableColumn<Car, Integer> idColumn = new TableColumn<>("Id");
        TableColumn<Car, String> ownerColumn = new TableColumn<>("Owner");
        TableColumn<Car, String> brandColumn = new TableColumn<>("Brand");
        TableColumn<Car, String> modelColumn = new TableColumn<>("Model");
        TableColumn<Car, Integer> yearColumn = new TableColumn<>("Year");
        TableColumn<Car, Integer> horsePowerColumn = new TableColumn<>("Horse power");
        TableColumn<Car, String> licensePlateColumn = new TableColumn<>("License plate");

        ownerColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        brandColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        yearColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        horsePowerColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        licensePlateColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        ownerColumn.setOnEditCommit(value -> {
                    int id = value.getTableView().getItems().get(value.getTablePosition().getRow()).getId();
                    carDataAccessor.updateCarOwner(id, value.getNewValue());
                }
        );
        brandColumn.setOnEditCommit(value -> {
                    int id = value.getTableView().getItems().get(value.getTablePosition().getRow()).getId();
                    carDataAccessor.updateCarBrand(id, value.getNewValue());
                }
        );
        modelColumn.setOnEditCommit(value -> {
                    int id = value.getTableView().getItems().get(value.getTablePosition().getRow()).getId();
                    carDataAccessor.updateCarModel(id, value.getNewValue());
                }
        );
        yearColumn.setOnEditCommit(value -> {
                    int id = value.getTableView().getItems().get(value.getTablePosition().getRow()).getId();
                    carDataAccessor.updateCarYear(id, value.getNewValue());
                }
        );
        horsePowerColumn.setOnEditCommit(value -> {
                    int id = value.getTableView().getItems().get(value.getTablePosition().getRow()).getId();
                    carDataAccessor.updateCarHorsePower(id, value.getNewValue());
                }
        );
        licensePlateColumn.setOnEditCommit(value -> {
                    int id = value.getTableView().getItems().get(value.getTablePosition().getRow()).getId();
                    carDataAccessor.updateCarLicensePlate(id, value.getNewValue());
                }
        );

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ownerColumn.setCellValueFactory(cellData -> {
            Person owner = cellData.getValue().getOwner();

            return new SimpleStringProperty(owner.getFirstName() + " " + owner.getLastName());
        });
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        horsePowerColumn.setCellValueFactory(new PropertyValueFactory<>("horsePower"));
        licensePlateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLicensePlate().getLicense()));

        List<TableColumn<Car, ?>> tableColumns = Arrays.asList(idColumn, ownerColumn, brandColumn, modelColumn, yearColumn, horsePowerColumn, licensePlateColumn);

        table.setItems(carsTableData);
        table.getColumns().addAll(tableColumns);
        table.setEditable(true);
    }

    private void initializeDeleteButton() {
        Button button = new Button("Delete selected row");
        button.setTranslateX(340);
        Text deletedText = new Text("");

        deleteButton.add(button, 0, 1);
        deleteButton.add(deletedText, 0, 3);
        deletedText.setWrappingWidth(700);
        deletedText.setTranslateX(50);

        button.setOnAction(event -> {
            Car car = table.getSelectionModel().getSelectedItem();

            table.getItems().remove(car);
            carDataAccessor.deleteCar(car.getId());
            deletedText.setFill(Color.FIREBRICK);
            deletedText.setText("Successfully deleted car " + car);
        });
    }

    private void initializeInsertForm() {
        insertForm.setTranslateX(225);
        insertForm.setHgap(10);
        insertForm.setVgap(10);
        insertForm.setPadding(new Insets(25, 25, 25, 25));

        Text formTitle = new Text("Create а new car");
        formTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        insertForm.add(formTitle, 0, 0, 2, 1);

        Label ownerLabel = new Label("Owner:");
        insertForm.add(ownerLabel, 0, 1);

        TextField ownerTextField = new TextField();
        insertForm.add(ownerTextField, 1, 1);

        Label brandLabel = new Label("Brand:");
        insertForm.add(brandLabel, 0, 2);

        TextField brandTextField = new TextField();
        insertForm.add(brandTextField, 1, 2);

        Label modelLabel = new Label("Model:");
        insertForm.add(modelLabel, 0, 3);

        TextField modelTextField = new TextField();
        insertForm.add(modelTextField, 1, 3);

        Label yearLabel = new Label("Year:");
        insertForm.add(yearLabel, 0, 4);

        TextField yearTextField = new TextField();
        insertForm.add(yearTextField, 1, 4);

        Label horsePowerLabel = new Label("Horse power:");
        insertForm.add(horsePowerLabel, 0, 5);

        TextField horsePowerTextField = new TextField();
        insertForm.add(horsePowerTextField, 1, 5);

        Label licensePlateLabel = new Label("License plate:");
        insertForm.add(licensePlateLabel, 0, 6);

        TextField licensePlateTextField = new TextField();
        insertForm.add(licensePlateTextField, 1, 6);

        Button createButton = new Button("Create");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(createButton);
        insertForm.add(hbBtn, 1, 7);

        Text actionTargetButton = new Text();
        insertForm.add(actionTargetButton, 1, 8);

        createButton.setOnAction(event -> {
            carDataAccessor.insertCar(ownerTextField.getText(), brandTextField.getText(), modelTextField.getText(), Integer.parseInt(yearTextField.getText()), Integer.parseInt(horsePowerTextField.getText()), licensePlateTextField.getText());
            ObservableList<Car> carTableData = carDataAccessor.getAllCars();
            table.setItems(carTableData);

            actionTargetButton.setFill(Color.FIREBRICK);
            actionTargetButton.setText("Successfully created a new car.");
        });
    }

    public List<Node> getElements() {
        return Arrays.asList(tableGrid, deleteButton, insertForm);
    }
}
