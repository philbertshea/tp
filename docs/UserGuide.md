---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TAssist User Guide

TAssist is a **desktop app designed for CS2106 Teaching Assistants (TAs) to manage student records, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface(GUI). It provides:
* a simple, intuitive UI
* quick access to crucial student records, such as their contact, attendance and current grades
* high efficiency for fast typers
* human editable file formats for data persistence

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103-F15-4/tp/releases/tag/v1.5).

1Copy the file to the folder you want to use as the _home folder_ for TAssist.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar TAssist.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add -n John Doe -p 98765432 -e johnd@example.com -m A0123456J -t T01` : Adds a contact named `John Doe` to TAssist.

   * `edit -i 1 -p 91234567 -e johndoe@example.com` : Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.

   * `tag -a -i 1 -tag lateStudent` : Adds a tag to the 1st student with the label `lateStudent`

   * `del -i 3` : Deletes the 3rd contact shown in the current list.

   * `export -f ./data/test.csv` Exports the current data as a CSV file into the path ./data/test.csv

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Tired of using the user guide to understand how to use? Just type the command name (e.g. `edit`) into the command box to know more about how to use the command!

1. Student particulars that you would not require on a regular basis can be hidden! Simply type `toggle` and they will disappear! Alternatively click on `view` → `Compact View`! <br>
   ![UiToggle](images/UiToggle.png)

1. To show more details about a particular contact, click on the contact itself!<br>
   ![UiExtend](images/UiExtend.png)


1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add -n NAME`, `NAME` is a parameter which can be used as `add -n John Doe`.

* Items in parentheses requires at least one of them to be supplied.<br>
  e.g `(-p PHONE_NUMBER -tg TELEGRAM_HANDLE)` means either `-p PHONE_NUMBER` or `-tg TELEGRAM_HANDLE` or both `-p PHONE_NUMBER -tg TELEGRAM HANDLE` are accepted.

* Items in square brackets are optional.<br>
  e.g `-n NAME [-tag TAG]` can be used as `-n John Doe -tag friend` or as `-n John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[-tag TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `-n NAME -p PHONE_NUMBER`, `-p PHONE_NUMBER -n NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Toggling view : `toggle`

Toggles the student record view to be more compact.
The following particulars of a student will be hidden if compact view is enabled where applicable: `PHONE_NUMBER`, `TELEGRAM_HANDLE`, `EMAIL`, `MATRICULATION_NUMBER`, `YEAR` and `FACULTY`.

<box type="tip" seamless>

**Tip:** `PHONE_NUMBER` AND `TELEGRAM_HANDLE` can be copied to your clipboard! Just click on it!
  </box>


### Adding a person: `add`

Adds a person to the address book.

Format: `add -n NAME (-p PHONE_NUMBER -tg TELEGRAM_HANDLE) -e EMAIL -m MATRICULATION_NUMBER (-t TUTORIAL_GROUP -b LAB_GROUP) [-f FACULTY] [-y YEAR_OF_STUDY] [-r REMARKS] [-tag TAG]…​`

<box type="tip" seamless>

**Tip:** Matriculation numbers follows a checksum rule!
See [here](https://nusmodifications.github.io/nus-matriculation-number-calculator/) for a matriculation number calculator.

Want to manually calculate the checksum?
1. Sum up only the **last 6 numbers** within the matriculation number.
1. Divide this sum by 13 and take the remainder.
1. Use the remainder as an index (0-based!) to select a character from the sequence `YXWURNMLJHEADB`.
- `A0000000Y` has a sum of `0` and thus ends with `Y`.
- `A4000049Y` has a sum of `13` and thus also ends with `Y`.
- `A0000001X` has a sum of `1` and thus ends with `X`.
</box>

<box type="tip" seamless>

**Tip:** Only the **matriculation number** of a record makes them unique!
</box>

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0).
Tags must be a single word consisting of alphanumeric characters only.
Tags also have a limit of 60 characters.
</box>

Examples:
* `add -n John -p 81234567 -tg @jornn -e e1234567@u.nus.edu -m A1234567X -t T02 -b B03 -f Computing -y 5 -r Likes to sing`
* `add -n Doe -tg @doe_a_deer -e e7654321@u.nus.edu -b B01 -m A7654321J`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a student : `edit`

There are 2 possible ways to edit the students in the list.

#### 1. Edit a single student

Edits an existing student in the address book.

Format: `edit -i INDEX [-n NAME] [-p PHONE_NUMBER] [-tg TELEGRAM_HANDLE] [-e EMAIL] [-m MATRICULATION_NUMBER] [-t TUTORIAL_GROUP] [-b LAB_GROUP] [-f FACULTY] [-y YEAR_OF_STUDY] [-r REMARKS]​`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* If the student only has a phone number (with no telegram handle), you will not be able to remove it. (vice versa)
  * This is the same for tutorial group and lab group
* To remove a value, just type the flag: `-p` removes the phone number.

Examples:
*  `edit -i 1 -p 91234567 -e johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit -i 2 -n Betsy Crower` Edits the name of the 2nd person to be `Betsy Crower`.

#### 2. Edit multiple students (Batch edit)

Edits several existing student in the address book in one go.

Format: `edit -i INDEX_RANGE [-t TUTORIAL_GROUP] [-b LAB_GROUP] [-f FACULTY] [-y YEAR_OF_STUDY]`
* Edits the student at the specified `INDEX_RANGE`. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided. (Only the 4 stated here can be edited. Any other fields will not be accepted.)
* Existing values will be updated to the input values.

Examples:
* `edit -i 1-3 -y 2` Edits the year of study for the 1st to 3rd students to be 2
* `edit -i 1, 4, 5 -y 2 -f SOC` Edits the year of study for the 1st, 4th and 5th students to be 2 and faculty to be SOC

### Tagging a student: `tag`

There are 3 ways to tag a student.

#### 1. Adding tags

Adds tag(s) to a student.

Format: `tag -a -i INDEX [-tag TAG_NAME]...`

* Adds tags to the student at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* You need to add at least 1 tag. The tag will be added on top of the current tags the student has.
* The `TAG_NAME` must be alphanumeric and have a maximum of 60 characters.
* This is purely for adding. To edit and delete tags, look at [Edit Tags](#2-editing-a-tag) and [Delete Tags](#3-deleting-tags)

Examples:
* `tag -a -i 1 -tag lateStudent` Adds a tag to the 1st student with the label `lateStudent`
* `tag -a -i 1 -tag NeedHelp -tag AbleToCompete` Adds a tag to the 1st student with the labels `NeedHelp` and `AbleToCompete`
  * The resultant tags will be sorted alphabetically.

#### 2. Editing a tag

Edits a current tag.

Format: `tag -m -i INDEX -tag OLD_TAG_NAME -tag NEW_TAG_NAME`

* Edits the tag `OLD_TAG_NAME` and replaces the tags value with `NEW_TAG_NAME` of the student at the specified INDEX. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* You can only edit 1 tag at a time. The OLD_TAG_NAME must exist for you to edit and replace its value.
* The `OLD_TAG_NAME` and `NEW_TAG_NAME` must be alphanumeric and have a maximum of 60 characters.

Examples:
* `tag -m -i 1 -tag lastStudent -tag earlyStudent` Replaces the value of the `lateStudent` tag, of the 1st student, with `earlyStudent`
* `tag -m -i 1 -tag NeedHelp -tag CanSurvive` Replaces the value of the `NeedHelp` tag, of the 1st student, with `CanSurvive`

#### 3. Deleting tags

Removes tag(s) from a student.

Format `tag -d -i INDEX [-tag TAG_NAME]...`

* Deletes the specified tags from the student at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* You must specify at least 1 tag when using this command. `TAG_NAME` is case sensitive and will only delete exact matches.
* The `TAG_NAME` must be alphanumeric and have a maximum of 60 characters.

Examples:
* `tag -d -i 1 -tag earlyStudent` Deletes the tag `earlyStudent` from student 1
* `tag -d -i 1 -tag tag1 -tag tag2` Deletes the tags `tag1` and `tag2` from student 1

### Marks attendance: `att`

Marks the attendance of an individual student, or a tutorial group.

Format: `att (-i INDEX -t TUTORIAL_GROUP) -w WEEK [-mc] [-u] [-nt]`

* Conditional parameters: EITHER `-i INDEX` OR `-t TUTORIAL_GROUP`
  * Not accepted: NEITHER or BOTH FLAGS TOGETHER
* Mandatory parameter: `-w WEEK`
  * Not accepted: MISSING week flag
* Optional parameters: EITHER ONE OF `-mc`, `-u`, OR `-nt`
  * Not accepted: TWO OR MORE OF THE ABOVE FLAGS
* Additional restriction: `-nt` CANNOT be together with `-i`.

Assuming the above restrictions are satisfied,
* Marks the attendance of a student (if `-i INDEX` is provided)
  OR all students in a tutorial group (if `-t TUTORIAL_GROUP` is provided).
* The new attendance status is ATTENDED by default. However:
  * If `-mc` is provided, new attendance status is ON MC.
  * If `-u` is provided, new attendance status is NOT ATTENDED.
  * If `-nt` is provided, new attendance status is NO TUTORIAL.

Examples:
* `att -i 1 -w 3` marks the first student as attended Tutorial Week 3.
* `att -i 2 -w 10 -mc` marks the second student as on MC for Tutorial Week 10.
* `att -t T01 -w 1 -nt` marks the whole tutorial group T01 as No Tutorial for Tutorial Week 1.
  * This means each student in tutorial group T01 has his attendance updated to No Tutorial.


### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `del`

Deletes the specified person from the address book.

Format: `del -i INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* To specify multiple indexes, input indexes separated by comma (e.g. 1,2,3) or a range (e.g. 1-5)

Examples:
* `del -i 2` deletes the 2nd person in the address book.
* `del -i 1,4-6` deletes the 1st, 4th, 5th and 6th person in the address book.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Export Data : `export`

Exports all the student data as CSV or JSON file.
There are 2 ways to export data

#### 1. Using the CLI:

Format: `export -f FILE_PATH`

* Exports the data to the `FILE_PATH` specified in this command.
* The `FILE_PATH` can either be relative to the current application OR be a full file path.
* The FILE_PATH must end either in a `<filename>.csv` or `<filename>.json`

Examples:
* `export -f ./data/test.csv` Exports the current data as a CSV file into the path ./data/test.csv
* `export -f /Users/user_name/Desktop/data.json` Exports the current data as a JSON file into the Desktop under data.json

#### 2. Using the UI:

1. On the toolbar, go to Files > Export Data...
2. Select the file type (either JSON or CSV)
3. Select where you want to save your file at

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add -n NAME (-p PHONE_NUMBER -tg TELEGRAM_HANDLE) -e EMAIL -m MATRICULATION_NUMBER (-t TUTORIAL_GROUP -b LAB_GROUP) [-f FACULTY] [-y YEAR_OF_STUDY] [-r REMARKS] [-tag TAG]…​` <br> e.g., `add -n John -p 81234567 -tg @jornn -e e1234567@u.nus.edu -m A1234567X -t T02 -b B03 -f Computing -y 5 -r Likes to sing`
**Clear**  | `clear`
**Delete** | `del -i INDEX`<br> e.g., `del -i 3`
**Edit**   | `edit -i INDEX [-n NAME] [-p PHONE_NUMBER] [-tg TELEGRAM_HANDLE] [-e EMAIL] [-m MATRICULATION_NUMBER] [-t TUTORIAL_GROUP] [-b LAB_GROUP] [-f FACULTY] [-y YEAR_OF_STUDY] [-r REMARKS]`<br> e.g.,`edit -i 2 -n James Lee -e jameslee@example.com`
**Tag**    | Add: `tag -a -i INDEX [-tag TAG_NAME]...`<br> e.g., `tag -a -i 1 -tag lateStudent`<br><br> Edit: `tag -m -i INDEX -tag OLD_TAG_NAME -tag NEW_TAG_NAME`<br> e.g., `tag -m -i 1 -tag lastStudent -tag earlyStudent`<br><br> Delete: `tag -d -i INDEX [-tag TAG_NAME]...`<br> e.g., `tag -d -i 1 -tag earlyStudent`
**Mark Attendance**   | `att (-i INDEX -t [TUTORIAL GROUP]) [-mc] [-u] [-nt]`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Export** | `export -f FILE_PATH`<br> e.g., `export -f ./data/test.csv`
**Help**   | `help`
