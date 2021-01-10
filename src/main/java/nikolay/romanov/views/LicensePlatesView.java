package nikolay.romanov.views;

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
import nikolay.romanov.accessors.LicensePlateDataAccessor;
import nikolay.romanov.models.LicensePlate;

import java.util.Arrays;
import java.util.List;

public class LicensePlatesView {
    private final LicensePlateDataAccessor licensePlateDataAccessor;

    private final GridPane tableGrid = new GridPane();
    private final TableView<LicensePlate> table = new TableView<>();
    private final GridPane deleteButton = new GridPane();
    private final GridPane insertForm = new GridPane();

    public LicensePlatesView(LicensePlateDataAccessor licensePlateDataAccessor) {
        this.licensePlateDataAccessor = licensePlateDataAccessor;

        initializeLicensePlatesTable(licensePlateDataAccessor);
        initializeDeleteButton();
        initializeInsertForm();
    }

    private void initializeLicensePlatesTable(LicensePlateDataAccessor licensePlateDataAccessor) {
        tableGrid.setTranslateX(240);
        tableGrid.setHgap(10);
        tableGrid.setVgap(10);
        tableGrid.setPadding(new Insets(25, 25, 25, 25));

        Text formTitle = new Text("License plates table");
        formTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        tableGrid.add(formTitle, 0, 0, 2, 1);
        tableGrid.add(table, 0, 1);


        table.setMaxWidth(240);
        ObservableList<LicensePlate> licensePlatesData = licensePlateDataAccessor.getAllLicensePlates();

        TableColumn<LicensePlate, Integer> idColumn = new TableColumn<>("Id");
        TableColumn<LicensePlate, String> licenseColumn = new TableColumn<>("License");
        TableColumn<LicensePlate, String> countryColumn = new TableColumn<>("Country");
        TableColumn<LicensePlate, String> regionColumn = new TableColumn<>("Region");

        licenseColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        countryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        regionColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        licenseColumn.setOnEditCommit(value -> {
                    int id = value.getTableView().getItems().get(value.getTablePosition().getRow()).getId();
                    licensePlateDataAccessor.updateLicensePlateLicense(id, value.getNewValue());
                }
        );
        countryColumn.setOnEditCommit(value -> {
                    int id = value.getTableView().getItems().get(value.getTablePosition().getRow()).getId();
                    licensePlateDataAccessor.updateLicensePlateCountry(id, value.getNewValue());
                }
        );
        regionColumn.setOnEditCommit(value -> {
                    int id = value.getTableView().getItems().get(value.getTablePosition().getRow()).getId();
                    licensePlateDataAccessor.updateLicensePlateRegion(id, value.getNewValue());
                }
        );

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        licenseColumn.setCellValueFactory(new PropertyValueFactory<>("license"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));

        List<TableColumn<LicensePlate, ?>> tableColumns = Arrays.asList(idColumn, licenseColumn, countryColumn, regionColumn);

        table.setItems(licensePlatesData);
        table.getColumns().addAll(tableColumns);
        table.setEditable(true);
    }

    private void initializeDeleteButton() {
        Button button = new Button("Delete selected row");
        button.setTranslateX(190);
        Text deletedText = new Text("");

        deleteButton.add(button, 0, 1);
        deleteButton.add(deletedText, 0, 2);
        deleteButton.setTranslateX(125);

        button.setOnAction(event -> {
            LicensePlate licensePlate = table.getSelectionModel().getSelectedItem();

            table.getItems().remove(licensePlate);
            licensePlateDataAccessor.deleteLicensePlate(licensePlate.getId());
            deletedText.setFill(Color.FIREBRICK);
            deletedText.setText("Successfully deleted licensePlate " + licensePlate);
        });
    }

    private void initializeInsertForm() {
        insertForm.setTranslateX(225);
        insertForm.setHgap(10);
        insertForm.setVgap(10);
        insertForm.setPadding(new Insets(25, 25, 25, 25));

        Text formTitle = new Text("Create a new license plate");
        formTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        insertForm.add(formTitle, 0, 0, 2, 1);

        Label licenseLabel = new Label("License:");
        insertForm.add(licenseLabel, 0, 1);

        TextField licenseTextField = new TextField();
        insertForm.add(licenseTextField, 1, 1);

        Label countryLabel = new Label("Country:");
        insertForm.add(countryLabel, 0, 2);

        TextField countryTextField = new TextField();
        insertForm.add(countryTextField, 1, 2);

        Label regionLabel = new Label("Region:");
        insertForm.add(regionLabel, 0, 3);

        TextField regionTextField = new TextField();
        insertForm.add(regionTextField, 1, 3);

        Button createButton = new Button("Create");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(createButton);
        insertForm.add(hbBtn, 1, 4);

        Text actionTargetText = new Text();
        insertForm.add(actionTargetText, 1, 6);

        createButton.setOnAction(event -> {
            licensePlateDataAccessor.insertLicensePlate(licenseTextField.getText(), countryTextField.getText(), regionTextField.getText());
            ObservableList<LicensePlate> licensePlatesTableData = licensePlateDataAccessor.getAllLicensePlates();
            table.setItems(licensePlatesTableData);

            actionTargetText.setFill(Color.FIREBRICK);
            actionTargetText.setText("Successfully created a new license plate.");
        });
    }

    public List<Node> getElements() {
        return Arrays.asList(tableGrid, deleteButton, insertForm);
    }
}
