package parser;

import lexical.Token;
import lexical.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SelectToken {

    Queue<Line> lines = new LinkedList<>();
    private Line currentLine ;

    public SelectToken(ArrayList<Token> tokens){
        Line line = new Line();
        for(Token token : tokens){
            if(token.getTypeOfToken() != Type.LINE_NUMBER){
                line.add(token);
            }else {
                if(line.isEmpty()){
                    continue;
                }
                line.setNumber(Integer.parseInt(token.getToken()));
                lines.add(line);
                line = new Line();
            }
        }
    }

    public Token poll(){
        if(currentLine == null || currentLine.isEmpty()){
            currentLine = lines.poll();
        }
        return (currentLine != null)?currentLine.getTokenOfLine():null;
    }

    public int numberOfLine(){
        return (currentLine ==null)?-1:currentLine.getNumber();
    }
}
