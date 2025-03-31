package seedu.tassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TELE_HANDLE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.tassist.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.commons.util.CollectionUtil;
import seedu.tassist.commons.util.ToStringBuilder;
import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.person.AttendanceList;
import seedu.tassist.model.person.Email;
import seedu.tassist.model.person.Faculty;
import seedu.tassist.model.person.LabGroup;
import seedu.tassist.model.person.LabScoreList;
import seedu.tassist.model.person.MatNum;
import seedu.tassist.model.person.Name;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.person.Phone;
import seedu.tassist.model.person.Remark;
import seedu.tassist.model.person.TeleHandle;
import seedu.tassist.model.person.TutGroup;
import seedu.tassist.model.person.Year;
import seedu.tassist.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = String.format(
            "Usage: edit %s INDEX [OPTIONS]...\n\n"
                    + "Edits the details of a person identified by INDEX in the displayed list.\n"
                    + "Existing values will be overwritten.\n\n"
                    + "Index:\n"
                    + "  <number>          Single index (e.g., 1)\n"
                    + "  <range>           Multiple indices (e.g., 1-3, 5, 7)\n\n"
                    + "Options:\n"
                    + "  %-7s NAME      Update name (single index only)\n"
                    + "  %-7s PHONE     Update phone number (single index only)\n"
                    + "  %-7s HANDLE    Update telegram handle (single index only)\n"
                    + "  %-7s EMAIL     Update email (single index only)\n"
                    + "  %-7s MATRIC    Update matriculation number (single index only)\n"
                    + "  %-7s GROUP     Update tutorial group\n"
                    + "  %-7s GROUP     Update lab group\n"
                    + "  %-7s FACULTY   Update faculty\n"
                    + "  %-7s YEAR      Update academic year\n"
                    + "  %-7s REMARKS   Update remarks (single index only)\n",
            PREFIX_INDEX, PREFIX_NAME, PREFIX_PHONE, PREFIX_TELE_HANDLE, PREFIX_EMAIL, PREFIX_MAT_NUM,
            PREFIX_TUT_GROUP, PREFIX_LAB_GROUP, PREFIX_FACULTY, PREFIX_YEAR, PREFIX_REMARK
            )
            + "\nExample:  \n"
            + "  # Update some fields for a single person\n"
            + "  edit "
            + PREFIX_INDEX + " 2 " + PREFIX_NAME + " John "
            + PREFIX_TELE_HANDLE + " @johnDoe " + PREFIX_EMAIL + " john@example.com "
            + PREFIX_MAT_NUM + " A0123456J \\\n" + "\t" + PREFIX_TUT_GROUP + " T01 "
            + PREFIX_LAB_GROUP + " B02 " + PREFIX_FACULTY + " SOC "
            + PREFIX_REMARK + " \"TA candidate\"\n\n"
            + "  # Update tutorial group, lab group, faculty, and year for multiple people\n"
            + "  edit "
            + PREFIX_INDEX + "1-3,5"
            + PREFIX_TUT_GROUP + " T02 "
            + PREFIX_LAB_GROUP + " B03 "
            + PREFIX_FACULTY + " SOC "
            + PREFIX_YEAR + " 2";

    public static final String MESSAGE_EDIT_SINGLE_PERSON_SUCCESS = "Edited Person: \n%1$s";
    public static final String MESSAGE_EDIT_MULTIPLE_PERSON_SUCCESS = "Summary of edited people: \n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "This person already exists in the address book.";
    public static final String MESSAGE_TELEHANDLE_REQUIRED = "You cannot remove the Telegram handle!";
    public static final String MESSAGE_PHONE_REQUIRED = "You cannot remove the Phone Number!";
    public static final String MESSAGE_LAB_GROUP_REQUIRED = "You cannot remove the Lab Group!";
    public static final String MESSAGE_TUT_GROUP_REQUIRED = "You cannot remove the Tutorial Group!";

    private final List<Index> indexList;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * Creates an EditCommand to edit a {@code Person} at the specific {@code indexList}.
     *
     * @param indexList of the people in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(List<Index> indexList, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(indexList);
        requireNonNull(editPersonDescriptor);

        this.indexList = indexList;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> updatedPeople = new ArrayList<>();
        for (Index index : indexList) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(String.format(
                        Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        lastShownList.size()));
            }
            Person personToEdit = lastShownList.get(index.getZeroBased());
            // To prevent optional items from getting deleted
            if (!editPersonDescriptor.getTeleHandle().isEmpty()) {
                boolean isTeleAvailable = personToEdit.getPhone().isEmpty()
                        && editPersonDescriptor.getTeleHandle().get().value.isBlank();
                if (isTeleAvailable) {
                    throw new CommandException(MESSAGE_TELEHANDLE_REQUIRED);
                }
            }
            if (!editPersonDescriptor.getPhone().isEmpty()) {
                boolean isPhoneAvailable = personToEdit.getTeleHandle().isEmpty()
                        && editPersonDescriptor.getPhone().get().value.isBlank();
                if (isPhoneAvailable) {
                    throw new CommandException(MESSAGE_PHONE_REQUIRED);
                }
            }

            if (!editPersonDescriptor.getTutGroup().isEmpty()) {
                boolean isTutGrpAvailable = personToEdit.getLabGroup().isEmpty()
                        && editPersonDescriptor.getTutGroup().get().value.isBlank();
                if (isTutGrpAvailable) {
                    throw new CommandException(MESSAGE_TUT_GROUP_REQUIRED);
                }
            }
            if (!editPersonDescriptor.getLabGroup().isEmpty()) {
                boolean isLabGrpAvailable = personToEdit.getTutGroup().isEmpty()
                        && editPersonDescriptor.getLabGroup().get().value.isBlank();
                if (isLabGrpAvailable) {
                    throw new CommandException(MESSAGE_LAB_GROUP_REQUIRED);
                }
            }

            Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

            if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }
            updatedPeople.add(editedPerson);
            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        }
        if (indexList.size() == 1) {
            return new CommandResult(String.format(MESSAGE_EDIT_SINGLE_PERSON_SUCCESS,
                    Messages.getFormattedPersonAttributesForDisplay(updatedPeople.get(0))));
        } else {
            return new CommandResult(String.format(MESSAGE_EDIT_MULTIPLE_PERSON_SUCCESS,
                    getEditedStudentsSummary(updatedPeople)));
        }
    }

    /**
     * Generates a short summary of bulk edited students.
     */
    public static String getEditedStudentsSummary(List<Person> students) {
        StringBuilder sb = new StringBuilder();
        for (Person p : students) {
            sb.append(String.format("%s (%s) - Year: %s, Faculty: %s, Tutorial Grp: %s, Lab Grp: %s\n",
                    p.getName().fullName,
                    p.getMatNum().value,
                    p.getYear().value,
                    p.getFaculty().value,
                    p.getTutGroup().value,
                    p.getLabGroup().value)
            );
        }
        return sb.toString().trim();
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    public static Person createEditedPerson(Person personToEdit,
                EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        TeleHandle updatedTeleHandle = editPersonDescriptor.getTeleHandle().orElse(personToEdit.getTeleHandle());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        MatNum updatedMatNum = editPersonDescriptor.getMatNum().orElse(personToEdit.getMatNum());
        TutGroup updatedTutGroup = editPersonDescriptor.getTutGroup().orElse(personToEdit.getTutGroup());
        LabGroup updatedLabGroup = editPersonDescriptor.getLabGroup().orElse(personToEdit.getLabGroup());
        Faculty updatedFaculty = editPersonDescriptor.getFaculty().orElse(personToEdit.getFaculty());
        Year updatedYear = editPersonDescriptor.getYear().orElse(personToEdit.getYear());
        Remark updatedRemark = editPersonDescriptor.getRemark().orElse(personToEdit.getRemark());

        AttendanceList updatedAttendanceList = personToEdit.getAttendanceList();
        if (!personToEdit.getAttendanceList().isEmpty() && updatedTutGroup.isEmpty()) {
            // If the origin attendanceList is not empty, but the updatedTutGroup is empty,
            // Assign the Empty AttendanceList.
            updatedAttendanceList = AttendanceList.EMPTY_ATTENDANCE_LIST;
        } else if (personToEdit.getAttendanceList().isEmpty() && !updatedTutGroup.isEmpty()) {
            // Else if the origin attendanceList is empty, but the updatedTutGroup is not empty,
            // Generate an attendanceList with the Default AttendanceString.
            updatedAttendanceList = AttendanceList.generateAttendanceList(AttendanceList.DEFAULT_ATTENDANCE_STRING);
        }

        // LabScoreList are designed not to be edited via the EditCommand.
        LabScoreList updatedLabScoreList = personToEdit.getLabScoreList();
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedTeleHandle, updatedEmail,
                updatedMatNum, updatedTutGroup, updatedLabGroup, updatedFaculty, updatedYear,
                updatedRemark, updatedAttendanceList, updatedLabScoreList, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return indexList.equals(otherEditCommand.indexList)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("indexList", indexList)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private TeleHandle teleHandle;
        private Email email;
        private MatNum matNum;
        private TutGroup tutGroup;
        private LabGroup labGroup;
        private Faculty faculty;
        private Year year;
        private Remark remark;
        private Set<Tag> tags;

        public EditPersonDescriptor() {

        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setTeleHandle(toCopy.teleHandle);
            setEmail(toCopy.email);
            setMatNum(toCopy.matNum);
            setTutGroup(toCopy.tutGroup);
            setLabGroup(toCopy.labGroup);
            setFaculty(toCopy.faculty);
            setYear(toCopy.year);
            setRemark(toCopy.remark);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(
                    name, phone, teleHandle, email, matNum, tutGroup,
                    labGroup, faculty, year, remark, tags
            );
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public Optional<TeleHandle> getTeleHandle() {
            return Optional.ofNullable(teleHandle);
        }

        public void setTeleHandle(TeleHandle teleHandle) {
            this.teleHandle = teleHandle;
        }

        public Optional<MatNum> getMatNum() {
            return Optional.ofNullable(matNum);
        }

        public void setMatNum(MatNum matNum) {
            this.matNum = matNum;
        }

        public Optional<TutGroup> getTutGroup() {
            return Optional.ofNullable(tutGroup);
        }

        public void setTutGroup(TutGroup tutGroup) {
            this.tutGroup = tutGroup;
        }

        public Optional<LabGroup> getLabGroup() {
            return Optional.ofNullable(labGroup);
        }

        public void setLabGroup(LabGroup labGroup) {
            this.labGroup = labGroup;
        }

        public Optional<Faculty> getFaculty() {
            return Optional.ofNullable(faculty);
        }

        public void setFaculty(Faculty faculty) {
            this.faculty = faculty;
        }

        public Optional<Year> getYear() {
            return Optional.ofNullable(year);
        }

        public void setYear(Year year) {
            this.year = year;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null)
                    ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;

            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(teleHandle, otherEditPersonDescriptor.teleHandle)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(matNum, otherEditPersonDescriptor.matNum)
                    && Objects.equals(tutGroup, otherEditPersonDescriptor.tutGroup)
                    && Objects.equals(labGroup, otherEditPersonDescriptor.labGroup)
                    && Objects.equals(faculty, otherEditPersonDescriptor.faculty)
                    && Objects.equals(year, otherEditPersonDescriptor.year)
                    && Objects.equals(remark, otherEditPersonDescriptor.remark)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("teleHandle", teleHandle)
                    .add("email", email)
                    .add("matNum", matNum)
                    .add("tutGroup", tutGroup)
                    .add("labGroup", labGroup)
                    .add("faculty", faculty)
                    .add("year", year)
                    .add("remark", remark)
                    .add("tags", tags)
                    .toString();
        }
    }
}
