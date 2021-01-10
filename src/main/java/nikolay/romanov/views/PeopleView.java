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
import javafx.util.converter.IntegerStringConverter;
import nikolay.romanov.accessors.PersonDataAccessor;
import nikolay.romanov.models.Person;

import java.util.Arrays;
import java.util.List;

public class PeopleView {
    private final PersonDataAccessor personDataAccessor;

    private final GridPane tableGrid = new GridPane();
    private final TableView<Person> table = new TableView<>();
    private final GridPane deleteButton = new GridPane();
    private final GridPane insertForm = new GridPane();

    public PeopleView(PersonDataAccessor personDataAccessor) {
        this.personDataAccessor = personDataAccessor;

        initializePeopleTable();
        initializeDeleteButton();
        initializeInsertForm();
    }

    private void initializePeopleTable() {
        tableGrid.setTranslateX(240);
        tableGrid.setHgap(10);
        tableGrid.setVgap(10);
        tableGrid.setPadding(new Insets(25, 25, 25, 25));

        Text formTitle = new Text("People table");
        formTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        tableGrid.add(formTitle, 0, 0, 2, 1);
        tableGrid.add(table, 0, 1);


        table.setMaxWidth(232);
        ObservableList<Person> peopleTableData = personDataAccessor.getAllPeople();

        TableColumn<Person, Integer> idColumn = new TableColumn<>("Id");
        TableColumn<Person, String> firstNameColumn = new TableColumn<>("First name");
        TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last name");
        TableColumn<Person, Integer> ageColumn = new TableColumn<>("Age");

        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ageColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        firstNameColumn.setOnEditCommit(value -> {
                    int id = value.getTableView().getItems().get(value.getTablePosition().getRow()).getId();
                    personDataAccessor.updatePersonFirstName(id, value.getNewValue());
                }
        );
        lastNameColumn.setOnEditCommit(value -> {
                    int id = value.getTableView().getItems().get(value.getTablePosition().getRow()).getId();
                    personDataAccessor.updatePersonLastName(id, value.getNewValue());
                }
        );
        ageColumn.setOnEditCommit(value -> {
                    int id = value.getTableView().getItems().get(value.getTablePosition().getRow()).getId();
                    personDataAccessor.updatePersonAge(id, value.getNewValue());
                }
        );

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        List<TableColumn<Person, ?>> tableColumns = Arrays.asList(idColumn, firstNameColumn, lastNameColumn, ageColumn);

        table.setItems(peopleTableData);
        table.getColumns().addAll(tableColumns);
        table.setEditable(true);
    }

    private void initializeDeleteButton() {
        Button button = new Button("Delete selected row");
        button.setTranslateX(200);
        Text deletedText = new Text("");

        deleteButton.add(button, 0, 1);
        deleteButton.add(deletedText, 0, 2);
        deleteButton.setTranslateX(125);

        button.setOnAction(event -> {
            Person person = table.getSelectionModel().getSelectedItem();

            table.getItems().remove(person);
            personDataAccessor.deletePerson(person.getId());
            deletedText.setFill(Color.FIREBRICK);
            deletedText.setText("Successfully deleted person " + person);
        });
    }

    private void initializeInsertForm() {
        insertForm.setTranslateX(215);
        insertForm.setHgap(10);
        insertForm.setVgap(10);
        insertForm.setPadding(new Insets(25, 25, 25, 25));

        Text formTitle = new Text("Create a new person");
        formTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        insertForm.add(formTitle, 0, 0, 2, 1);

        Label firstNameLabel = new Label("First Name:");
        insertForm.add(firstNameLabel, 0, 1);

        TextField firstNameTextField = new TextField();
        insertForm.add(firstNameTextField, 1, 1);

        Label lastNameLabel = new Label("Last Name:");
        insertForm.add(lastNameLabel, 0, 2);

        TextField lastNameTextField = new TextField();
        insertForm.add(lastNameTextField, 1, 2);

        Label ageLabel = new Label("Age:");
        insertForm.add(ageLabel, 0, 3);

        TextField ageTextField = new TextField();
        insertForm.add(ageTextField, 1, 3);

        Button createButton = new Button("Create");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(createButton);
        insertForm.add(hbBtn, 1, 4);

        Text actionTargetButton = new Text();
        insertForm.add(actionTargetButton, 1, 6);

        createButton.setOnAction(event -> {
            personDataAccessor.insertPerson(firstNameTextField.getText(), lastNameTextField.getText(), Integer.parseInt(ageTextField.getText()));
            ObservableList<Person> peopleTableData = personDataAccessor.getAllPeople();
            table.setItems(peopleTableData);

            actionTargetButton.setFill(Color.FIREBRICK);
            actionTargetButton.setText("Successfully created a new person.");
        });
    }

    public List<Node> getElements() {
        return Arrays.asList(tableGrid, deleteButton, insertForm);
    }
}
