package lexical;

import java.io.File;
import java.util.ArrayList;

public class CONSTANT {

    public static final char SPACE = ' ';
    public static final char DOT = '.';
    public static final char COLON = ':';
    public static final int INTEGER_ID = 1000;
    public static final int FLOAT_ID = 2000;
    public static final int STRING_ID = 3000;
    public static final int USER_IDENTIFIERS_ID = 4000;
    public static final int NEW_LINE = 5000;
    public static final File SYMBOLS_FILE = new File("src/programs/symbols.txt");
    public static final File KEYWORDS_FILE = new File("src/programs/keywords.txt");
    public static final File STANDARD_IDENTIFIERS_FILE = new File("src/programs/standardIdentifiers.txt");
    public static ArrayList<String> SYMBOLS_LIST;
    public static ArrayList<String> STANDARD_IDENTIFIERS_LIST;
    public static ArrayList<String> KEYWORDS_LIST;


}
