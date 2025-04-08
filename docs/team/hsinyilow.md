---
  layout: default.md
  title: "Low Hsin Yi's Project Portfolio Page"
---

### Project: TAssist

TAssist is a desktop application used by CS2106 Teaching Assistants (TAs) for tracking their students in different
tutorial and/or lab groups. It tracks students' essential information like names, numbers or telegram handles,
matriculation numbers, school/faculty, and other relevant information.

Given below are my contributions to the project.

* **New Feature**: Add the ability to update the students lab score. (Pull requests [\#74](), [\#105](), [\#114]())
    * What it does: allows the user to update a student's lab score for a specific lab one at a time. 
    Also allows the user to update the maximum score of the specified lab to 
    * Justification: This feature improves the product because it provides an easy way to update and view each student
    lab score at a glance.
    * Enhancement: Added bulk command to allow user to update all the students maximum lab score for the specified lab
    at the same time.
      * Justification: For the same lab assignment, students will not have different maximum scores since they are doing
      the same assignment.
    * Enhancements: Added an extra command to allow user to update a specified student lab score and the maximum lab
    score at the same time.
      * Justification: It provides a shortcut for the user to update both at the same time instead of needing to run
      two different commands.
    * Credits: NIL

* **New Feature**: Added the ability to undo and redo unlimited number previous commands. (Pull requests [\#144](), [\#232]())
  * What it does: allows the user to undo all previous commands one at a time. 
  Preceding undo commands can be reversed by using the redo command.
  However, if any changes were made after a command was undone, there would be nothing to redo.
  * Justification: This feature improves the product significantly because a user can make mistakes in commands 
  and the app should provide a convenient way to rectify them.
  * Credits: NIL

* **Code contributed**: [RepoSense link]()

* **Project management**:
  * Helped to write the release notes for `v1.3` to `v1.5.1`

* **Enhancements to existing features**:
  * Updated the UI to include the lab score section for the students.

* **Documentation**: (Pull requests [\#158](), [\#226](), [\#237]())
  * Added documentation in the user guide for the updating of lab score.
  * Added documentation in the user guide for the undo and redo commands.
  * Updated the user stories to match the behaviour of undo and redo commands.
  * Added manual test cases for updating lab score, undo and redo commands in the developer guide.
    * Note: this was passed to chinzj over telegram to add to developer guide to
    prevent multiple merge conflicts.

* **Community**:
  * Reviewed 27 pull requests and left 18 review comments.
  * Tested export command and tag command for v1.4 and created 6 alpha bug reports for errors found during testing.

* **Tools**:
  * NIL
