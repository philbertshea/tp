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

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for TAssist.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar TAssist.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add -n John Doe -p 98765432 -e johnd@example.com -m A0123456J -t T01` : Adds a contact named `John Doe` to TAssist.

   * `del -i 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

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
  e.g `-n NAME [-tag ]` can be used as `-n John Doe -tag friend` or as `-n John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>

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

**Tip:** A person can have any number of tags (including 0).
Tags must be a single word consisting of alphanumeric characters only.
Tags must also be at most 60 characters.
</box>

<box type="tip" seamless>

**Tip:** Only the **matriculation number** of a record makes them unique!
</box>

<box type="tip" seamless>

**Note:** 
* If `TUTORIAL_GROUP` is provided as an input when adding a person, then the person is assigned 
the Default Attendance List (represented by the string `3300000000000`, which means No Tutorial for
Weeks 1 and 2, Not Attended for Weeks 3 to 13). 
* If no `TUTORIAL_GROUP` is provided as input when adding a person, then the person is assigned
an Empty Attendance List.
</box>

Examples:
* `add -n John -p 81234567 -tg @jornn -e e1234567@u.nus.edu -m A1234567X -t T02 -b B03 -f Computing -y 5 -r Likes to sing`
* `add -n Doe -tg @doe_a_deer -e e7654321@u.nus.edu -b B01 -m A7654321J`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* The behaviour of the edit command depends on the field being edited:
  * For mandatory fields of a person: `-n NAME`, `-e EMAIL`, `-m MATRICULATION_NUMBER`, a valid input value must be provided.
to replace the current value. 
    * E.g. `edit -i 1 -n Alex` is valid, editing the name of person of index 1 to Alex.
    * `edit -i 1 -n 123!#$` is invalid, because 123!#$ is not a valid name.
    * `edit -i 1 -n` is invalid, because a person must have a name, which is a mandatory field.
  * There are two sets of conditional fields: Set 1: `-p PHONE_NUMBER -tg TELEGRAM_HANDLE` and 
  * Set 2: `-t TUTORIAL_GROUP -b LAB_GROUP`.
These are conditional fields, whereby AT LEAST ONE or BOTH of the fields in EVERY SET must have a valid input.
    * Providing a valid input value for either or both fields will always be supported as a valid edit.
    * The validity of the edit command depends on whether the person fulfills these conditions after the edit:
      * After the edit, the person has AT LEAST a valid `PHONE_NUMBER` OR a valid `TELEGRAM_HANDLE`.
      * After the edit, the person has AT LEAST a valid `TUTORIAL_GROUP` OR a valid `LAB_GROUP`.
    * E.g. if the person of index 1 currently has a valid `PHONE_NUMBER` but no valid `TELEGRAM_HANDLE`:
      * `edit -i 1 -p 90001234` is valid, because he will still have a valid phone number after the edit.
      * `edit -i 1 -t @telehandle123` is valid, because he will have BOTH a valid phone number 
      AND a valid teleHandle after the edit.
      * `edit -i 1 -p` is INVALID, because the proposed edit would make the person have NEITHER a valid phone number,
      NOR a valid teleHandle after the edit.
  * Optional fields like `FACULTY`, `YEAR` and `REMARKS` can be edited to any valid input, or empty input.

Examples:
*  `edit 1 -p 91234567 -e johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 -n Betsy Crower` Edits the name of the 2nd person to be `Betsy Crower`.

<box type="tip" seamless>

**Note:**
* The behaviour of editing a `TUTORIAL_GROUP` on the person's AttendanceList depends on his original and updated
status (whether he had a tutorial group before the edit, and will have one after the edit).
  * Case 1: A person originally has a valid `TUTORIAL_GROUP` (and hence a valid AttendanceList),
    * Case 1.1: An edit command is given to edit his `TUTORIAL_GROUP` to another valid `TUTORIAL_GROUP`. 
    Then the person's AttendanceList is carried over (No change to the AttendanceList).
    * Case 1.2: An edit command is given to edit his `TUTORIAL_GROUP` to an empty input.
    Provided the aforementioned restrictions on the conditional parameters are fulfilled (i.e. the person has a valid `LAB_GROUP`),
    then the person's AttendanceList is cleared and replaced with the Empty AttendanceList.
  * Case 2: A person originally has NO valid `TUTORIAL_GROUP` (and hence an empty AttendanceList),
      * Case 2.1: An edit command is given to edit his `TUTORIAL_GROUP` to a valid `TUTORIAL_GROUP`.
        Then the person's AttendanceList is set to the Default AttendanceList 
        (represented by the string `3300000000000`, which means No Tutorial for
        Weeks 1 and 2, Not Attended for Weeks 3 to 13).
      * Case 2.2: An edit command is given to edit his `TUTORIAL_GROUP` to an empty input.
      Then the person's AttendanceList remains as an Empty AttendanceList.
</box>

### Marks attendance: `att`

Marks the attendance of an individual student, or a tutorial group.
**Tip**: With the new release, you can now mark the attendance of multiple students or tutorial groups.

Format: `att (-i INDEX -t TUTORIAL_GROUP) -w WEEK [-mc] [-u] [-nt]`

* Conditional parameters: EITHER `-i INDEX` OR `-t TUTORIAL_GROUP`
  * Not accepted: NEITHER of the flags provided, or BOTH flags provided together.
* Mandatory parameter: `-w WEEK`
  * Not accepted: MISSING week flag
* Optional parameters: EITHER ONE OF `-mc`, `-u`, OR `-nt`
  * Not accepted: TWO or more of the above flags.

Assuming the restrictions are satisfied,
* Marks the attendance of a student (if `-i INDEX` is provided)
  OR all students in a tutorial group (if `-t TUTORIAL_GROUP` is provided).
* The new attendance status is ATTENDED by default. However:
    * If `-mc` is provided, new attendance status is ON MC.
    * If `-u` is provided, new attendance status is NOT ATTENDED.
    * If `-nt` is provided, new attendance status is NO TUTORIAL.

* **Note**: Additional restrictions apply on the marking attendance command.
  * Commands using the `-i` flag have additional restrictions.
    1. `-nt` flag cannot be used on a command with the `-i` flag. This means you cannot mark an individual
    as having No Tutorial.
       * If Alex of index 1 didn't attend Week 5 tutorial, mark him as Not Attended (or Unattended) using `att -i -w 5 -u`.
       * If Alex's tutorial group T01 falls on a public holiday, such that the Week 5 tutorial gets cancelled,
       mark the whole tutorial group as No Tutorial. Use the command `att -t T01 -w 5 -nt`.
    2. If the person specified by the `INDEX` has No Tutorial in the given week, the command is invalid. 
    This means you cannot mark an individual to any attendance status, if he currently has No Tutorial.
       * Assume Alex of tutorial group T01 originally had their tutorial cancelled due to the public holiday.
       Now, the profs announce a make-up tutorial for T01 that everyone must attend (like normal tutorials).
           * First, mark the whole tutorial group as Not Attended, using `att -t T01 -w 5 -u`.
           * On the day of the makeup tutorial, you find that Alex (index 1) attended.
           * Then, mark Alex as having attended the tutorial, `att -i 1 -w 5`.
    3. If the person has an Empty AttendanceList, any command on the person will be invalid.
       * Alex of index 1 has no `TUTORIAL_GROUP` (e.g. he is not in your tutorial group).
       Then it doesn't make sense to mark his attendance for any week.
       * If you realise Alex is actually in tutorial group T01, use the `edit` command to edit
       his `TUTORIAL_GROUP` to T01 first. Then you can use the mark attendance command on him.

* **Tip**: You can now mark the attendance of multiple persons and tutorial groups as valid.
  However, do note that if you are using the `-i` flag, to mark attendance of persons by index,
  the restrictions aforementioned apply to EVERY person listed.
    * For example, you want to mark persons of index 1 to 10 (inclusive) as attended for week 3.
        * You realise that person 3 has no tutorial group, and persons 4,5 are in tutorial group T03,
          and group T03's tutorial has been cancelled due to the clashing holiday. They are currently
          marked as No Tutorial for week 3, which is appropriate given their tutorial is cancelled.
        * `att -i 1-10 -w 3` gives you an error because persons 3, 4, 5 do not fulfill the restrictions.
        * You will need to mark attendance for the other people using `att -i 1-2,6-10 -w 3`.


Examples:
* `att -i 1 -w 3` marks the first student as attended Tutorial Week 3.
* `att -i 2 -w 10 -mc` marks the second student as on MC for Tutorial Week 10.
* `att -t T01 -w 1 -nt` marks the whole tutorial group T01 as No Tutorial for Tutorial Week 1.
    * This means each student in tutorial group T01 has his attendance updated to No Tutorial.
* `att -i 1-5 -w 3` marks the persons of indexes 1 to 5 (inclusive) of week 3 as Attended

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

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/ddressbook.json`. Advanced users are welcome to update data directly by editing that data file.

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
**Edit**   | `edit INDEX [-n NAME] [-p PHONE_NUMBER] [-e EMAIL] `<br> e.g.,`edit 2 -n James Lee -e jameslee@example.com`
**Mark Attendance**   | `att (-i INDEX -t [TUTORIAL GROUP]) [-mc] [-u] [-nt]`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
