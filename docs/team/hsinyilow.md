---
  layout: default.md
  title: "Low Hsin Yi's Project Portfolio Page"
---

### Project: TAssist

TAssist is a desktop application used by CS2106 Teaching Assistants (TAs) for tracking their students in different
tutorial and/or lab groups. It tracks students' essential information like names, numbers or telegram handles,
matriculation numbers, school/faculty, and other relevant information.

Given below are my contributions to the project.

* **New Feature**: Add the ability to update the students lab score. 
  (Pull requests [\#74](https://github.com/AY2425S2-CS2103-F15-4/tp/pull/74), [\#105](https://github.com/AY2425S2-CS2103-F15-4/tp/pull/105), 
  [\#202](https://github.com/AY2425S2-CS2103-F15-4/tp/pull/202))
    * What it does: allows the user to update a student's lab score for a specific lab one at a time. 
    Also allows the user to update the maximum score of the specified lab.
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

* **New Feature**: Added the ability to undo and redo unlimited number previous commands. 
(Pull requests [\#144](https://github.com/AY2425S2-CS2103-F15-4/tp/pull/144), [\#232](https://github.com/AY2425S2-CS2103-F15-4/tp/pull/232))
  * What it does: allows the user to undo all previous commands one at a time. 
  Preceding undo commands can be reversed by using the redo command.
  However, if any changes were made after a command was undone, there would be nothing to redo.
  * Justification: This feature improves the product because a user can make mistakes in commands 
  and the app should provide a convenient way to rectify them.
  * Credits: NIL

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s2.github.io/tp-dashboard/#/widget/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=hsinyilow&tabRepo=AY2425S2-CS2103-F15-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false&chartGroupIndex=15&chartIndex=2)

* **Project management**:
  * Helped to write the release notes for `v1.3` to `v1.5.1`

* **Enhancements to existing features**:
  * Updated the UI to include the lab score section for the students.

* **Documentation**: (Pull requests [\#158](https://github.com/AY2425S2-CS2103-F15-4/tp/pull/158), 
[\#226](https://github.com/AY2425S2-CS2103-F15-4/tp/pull/226), [\#237](https://github.com/AY2425S2-CS2103-F15-4/tp/pull/237))
  * Added documentation in the user guide for the updating of lab score.
  * Added documentation in the user guide for the undo and redo commands.
  * Updated the user stories to match the behaviour of undo and redo commands.
  * Added manual test cases for updating lab score, undo and redo commands in the developer guide.
    * Note: this was passed to zhen jie (chinzj) over telegram to add to developer guide to
    prevent multiple merge conflicts.
  * Created sequence diagram for lab score command.
    * Can be viewed in code repo but not added to developer guide.

* **Community**:
  * Reviewed 27 pull requests and left 18 review comments for my teammates.
    * For example, I gave suggestions to philbert (philbertshea) on changing the constructor 
    for easier checking of arguments for the batch attendance command.
  * Tested export command and tag command for v1.4 and created 6 alpha bug reports for errors found during testing.

* **Tools**:
  * NIL
