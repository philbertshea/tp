package seedu.tassist.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tassist.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label teleHandle;
    @FXML
    private Label email;
    @FXML
    private Label matNum;
    @FXML
    private Label tutGroup;
    @FXML
    private Label labGroup;
    @FXML
    private Label faculty;
    @FXML
    private Label year;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane attendances;
    @FXML
    private FlowPane labScores;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        // todo:  zhenjie Change UI layout in future
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        teleHandle.setText(person.getTeleHandle().value);
        email.setText(person.getEmail().value);
        matNum.setText(person.getMatNum().value);
        tutGroup.setText(person.getTutGroup().value);
        labGroup.setText(person.getLabGroup().value);
        faculty.setText(person.getFaculty().value);
        year.setText(person.getYear().value);
        remark.setText(person.getRemark().value);
        labScores.getChildren().add(new Label("Lab grades:"));
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getAttendanceList().getAttendanceStream()
                .forEach(attendance
                        -> attendances.getChildren().add(new Label(attendance.tagName())));

        final int[] labCounter = {1};
        person.getLabScoreList().getLabScores().forEach(
                labScore -> {
                    Label newLabel = new Label(
                            String.format("Lab %d: %s", labCounter[0], labScore.toString()));
                    newLabel.getStyleClass().add("lab-score");
                    labScores.getChildren().add(newLabel);

                    labCounter[0]++;
                }
        );
    }
}
