---
  layout: default.md
  title: "Philbert Shea's Project Portfolio Page"
---

### Project: 

TAssist is a desktop application used by CS2106 Teaching Assistants (TAs) for tracking their students in different
tutorial and/or lab groups. It tracks students' essential information like names, numbers or telegram handles,
matriculation numbers, school/faculty, and other relevant information.

Given below are my contributions to the project.

* **New Feature**:
  * **Marking of attendance**: Supports the marking of attendance for students with a valid tutorial group.
    * **Enhancement: Marking on MC and No Tutorial**: Students may be on MC, or their tutorial may be cancelled due
    to public holidays, university holidays etc. I added support for marking attendance to these statuses.
    * **Enhancement: Defaulting Weeks 1 and 2 to No Tutorial**: Students in CS2106 generally do not have lessons
    in the weeks 1 and 2. Tutorials usually only start in week 3. I updated the default attendance list to support this.
    * **Enhancement: Marking attendance of a tutorial group**: It does not make sense for a student to have no tutorial
    himself, without tying it to the tutorial group. I added support to mark attendance for the tutorial group,
    and then restricted the use of the No Tutorial flag to the marking of tutorial groups only.
    * **Enhancement: Stricter restrictions on marking of attendance**: There are other restrictions you could think of.
    For instance, if a tutorial group T01 currently has No Tutorial, it does not make sense to mark only Alex of T01
    as having Attended the tutorial. (The tutorial is currently considered as cancelled! What about the rest?)
    Therefore, if tutorial group T01 has No Tutorial, you must mark the whole tutorial group as "Not Attended" first,
    before you can mark any individual in the group as Attended or on Mc or what not.
    * **Enhancement: Empty Attendance List for those without tutorial groups**: People with no tutorial groups should
    not have a non-empty attendance list. I created an empty attendance list instance that does not support marking of
    attendance.
    * **Enhancement: Linking it to the Edit and Add Command**: As such, if people added are not given tutorial groups,
    or people with an existing tutorial group are edited to have no tutorial group now (e.g. another tutor's group),
    I assign them the empty attendance list.
    * **Enhancement: Bulk marking of attendance**: I added support to bulk-mark attendance for multiple indexes or tutorial groups.
    The similar restrictions apply on each person in the range of indexes provided.

* **Code contributed**: [RepoSense link]()

* **Project management**:
  * Managed and published all the releases so far, from `v1.3` to `v1.5.1`, with the help of team members to write detailed
  release notes. Also helped to record videos showcasing the products in all releases.
  * Was sort of a timekeeper, trying to rush deliverables before the deadlines.

* **Enhancements to existing features**:
  * **Added support for Attendance List**: Users can now treat attendance list as an attribute, when loading or saving data.
  However, editing and adding commands will not support this. After a thorough discussion with the group, we believe the
  MarkAttendanceCommand should still be the main one responsible for attendance matters.
  * **Added support for parsing multiple tutorial groups**: When developing bulk marking of attendance, I developed methods
  that would be useful for future use, to parse a String of multiple tutorial groups. Thanks to Hannah for providing
  the base code for parsing multiple indexes.
  * **Updated UI to reduce flickering**: Due to the implementation of our PersonCard, the UI flickers every time
  we toggle to another contact. I used a FadeTransition to fade out and in, improving the user's experience.

* **Testing**:
  * Added (I think) very comprehensive testing for methods introduced from my MarkAttendanceCommand, MarkAttendanceCommandParser,
  Attendance and AttendanceList classes.

* **Documentation**:
  * Added comprehensive documentation in the user guide for the marking of attendance.
  * Updated the user stories to match the behaviour of attendance marking commands.

* **Community**:
  * Reviewed a total of 42 PRs from various team members. I believe reviewing PRs is critical, as it acts as
  an additional safeguard to potentially problematic code. For instance, I flagged a potentially problematic, long
  PR by Xuezhou, and offered an alternative that uses only 3 LoC while not breaking other parts of the code.

* **Tools**:
  * Nil.
