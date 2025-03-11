package seedu.tassist.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("-n");
    public static final Prefix PREFIX_PHONE = new Prefix("-p");
    public static final Prefix PREFIX_TELE_HANDLE = new Prefix("-tele");
    public static final Prefix PREFIX_EMAIL = new Prefix("-e");
    public static final Prefix PREFIX_MAT_NUM = new Prefix("-m");
    public static final Prefix PREFIX_TUT_GROUP = new Prefix("-tut"); // todo change flag in future
    public static final Prefix PREFIX_LAB_GROUP = new Prefix("-lab");
    public static final Prefix PREFIX_FACULTY = new Prefix("-fac");
    public static final Prefix PREFIX_YEAR = new Prefix("-yr");
    public static final Prefix PREFIX_REMARK = new Prefix("-r");
    public static final Prefix PREFIX_TAG = new Prefix("-tag"); // Note: may be removed in future
    public static final Prefix PREFIX_INDEX = new Prefix("-i");
    public static final Prefix PREFIX_WEEK = new Prefix("-w");
    public static final Prefix PREFIX_ATTENDANCE_LIST = new Prefix("-al");
}
