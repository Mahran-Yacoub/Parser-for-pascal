package lexical;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tokenization {

    public Tokenization() {
        fillTables();
    }

    public ArrayList<Token> getTokens(File file) {

        ArrayList<Token> tokens = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);
            int counter = 1 ;
            while (scanner.hasNextLine()) {

                char[] line = scanner.nextLine().toCharArray();
                for (int i = 0; i < line.length; i++) {

                    char currentChar = line[i];
                    if (currentChar == CONSTANT.SPACE) {
                        continue;
                    }

                    if (Character.isLetter(currentChar)) {
                        i = getName(tokens, line, i);
                        continue;
                    }

                    if (Character.isDigit(currentChar)) {
                        i = getNumber(tokens, line, i);
                        continue;
                    }

                    ArrayList<Character> directSymbols = getDirectSymbols("src/programs/DirectSymbols.txt");

                    if (directSymbols.contains(currentChar)) {
                        tokens.add(new Token(currentChar + "", Type.SYMBOL));
                        continue;
                    }

                    ArrayList<Character> inDirectSymbols = getInDirectSymbols("src/programs/InDirectSymbols.txt");

                    if (inDirectSymbols.contains(currentChar)) {

                        String token = "";

                        if (currentChar == CONSTANT.DOT) {
                            if ((i + 1) < line.length && line[i + 1] == CONSTANT.DOT) {
                                token = "..";
                                i = i + 1;

                            } else {
                                token = ".";
                            }

                            tokens.add(new Token(token, Type.SYMBOL));
                            continue;
                        }


                        if (currentChar == CONSTANT.COLON) {

                            if ((i + 1) < line.length && line[i + 1] == '=') {
                                token = ":=";
                                i = i + 1;
                            } else {
                                token = ":";
                            }

                            tokens.add(new Token(token, Type.SYMBOL));
                            continue;
                        }


                        if (currentChar == '>') {

                            if ((i + 1) < line.length && line[i + 1] == '=') {
                                token = ">=";
                                i = i + 1;
                            } else {
                                token = ">";
                            }

                            tokens.add(new Token(token, Type.SYMBOL));
                            continue;

                        }


                        if (currentChar == '<') {

                            if ((i + 1) < line.length && line[i + 1] == '=') {
                                token = "<=";
                                i = i + 1;
                            } else if ((i + 1) < line.length && line[i + 1] == '>') {
                                token = "<>";
                                i = i + 1;
                            } else {
                                token = "<";
                            }

                            tokens.add(new Token(token, Type.SYMBOL));
                            continue;
                        }

                        if (currentChar == '(') {

                            if ((i + 1) < line.length && line[i + 1] == '*') {
                                CommentType commentEnd = getCommentEnd(i + 2, "(*", line, scanner);
                                if (commentEnd == null) {
                                    break;
                                }
                                i = commentEnd.getIndex();
                                line = commentEnd.getLine();
                            } else {
                                token = "(";
                                tokens.add(new Token(token, Type.SYMBOL));
                            }
                            continue;
                        }


                        if (currentChar == '{') {
                            CommentType commentEnd = getCommentEnd(i + 1, "{", line, scanner);
                            if (commentEnd == null) {
                                break;
                            }
                            i = commentEnd.getIndex();
                            line = commentEnd.getLine();
                            continue;
                        }

                    }

                    tokens.add(new Token(currentChar + "", Type.ERROR));
                }
                tokens.add(new Token(counter+"",Type.LINE_NUMBER,CONSTANT.NEW_LINE));
                counter++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return tokens;
    }

    private CommentType getCommentEnd(int j, String typeOfComment, char[] line, Scanner scanner) {

        if (typeOfComment.equals("{")) {

            for (int z = j; z < line.length; z++) {

                if (line[z] == '}') {
                    return new CommentType(line, z);
                }
            }

            while (scanner.hasNextLine()) {
                j = 0;
                char[] newLine = scanner.nextLine().toCharArray();
                for (int z = j; z < newLine.length; z++) {

                    if (newLine[z] == '}') {
                        return new CommentType(newLine, z);
                    }
                }
            }


        } else if (typeOfComment.equals("(*")) {

            for (int z = j; z < line.length - 1; z++) {

                if (line[z] == '*' && line[z + 1] == ')') {
                    return new CommentType(line, z + 1);
                }
            }

            while (scanner.hasNextLine()) {

                j = 0;
                char[] newLine = scanner.nextLine().toCharArray();
                for (int z = j; z < newLine.length - 1; z++) {

                    if (newLine[z] == '*' && newLine[z + 1] == ')') {
                        return new CommentType(newLine, z + 1);
                    }
                }
            }
        }

        return null;
    }

    private ArrayList<Character> getInDirectSymbols(String path) {
        return getDirectSymbols(path);
    }

    private ArrayList<Character> getDirectSymbols(String filePath) {

        ArrayList<Character> characters = new ArrayList<>();
        File file = new File(filePath);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                characters.add(line.charAt(0));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return characters;
    }

    private int getNumber(ArrayList<Token> tokens, char[] line, int i) {

        int j = i + 1;
        String number = line[i] + "";
        boolean isInteger = true;
        while (j < line.length) {

            if (Character.isDigit(line[j])) {
                number += line[j];
                j++;
                continue;
            }

            if (line[j] == '.') {
                if ((j + 1) < line.length && Character.isDigit(line[(j + 1)])) {
                    number += line[j];
                    j++;
                    isInteger = false;
                    continue;
                }
            }

            break;
        }

        Type type = (isInteger) ? Type.INTEGER : Type.FLOAT;
        tokens.add(new Token(number, type));
        return j - 1;
    }

    private int getName(ArrayList<Token> tokens, char[] line, int i) {

        int j = i + 1;
        String name = line[i] + "";
        while (j < line.length && Character.isLetterOrDigit(line[j])) {

            name += line[j];
            j++;
        }

        tokens.add(new Token(name));
        return j - 1;
    }

    private void fillTables() {
        ReadFile readFile = new ReadFile();
        CONSTANT.KEYWORDS_LIST = readFile.readFileAsList(CONSTANT.KEYWORDS_FILE);
        CONSTANT.STANDARD_IDENTIFIERS_LIST = readFile.readFileAsList(CONSTANT.STANDARD_IDENTIFIERS_FILE);
        CONSTANT.SYMBOLS_LIST = readFile.readFileAsList(CONSTANT.SYMBOLS_FILE);
    }
}
