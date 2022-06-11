package lexical;

public class CommentType {

    private char[] line;
    private int index;

    public CommentType(char[] line, int index) {
        this.line = line;
        this.index = index;
    }

    public char[] getLine() {
        return line;
    }

    public void setLine(char[] line) {
        this.line = line;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
