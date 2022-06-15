package parser;

import lexical.Token;

import java.util.LinkedList;
import java.util.Queue;

public class Line {

    private Queue<Token> line = new LinkedList<>();
    private int number = 0;

    public boolean isEmpty() {
        return (line.size() == 0 ? true : false);
    }

    public Token getTokenOfLine() {
        return line.poll();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void add(Token token) {
        line.add(token);
    }
}
