package parser;

import lexical.Token;
import lexical.Type;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class NonTerminal {

    private Queue<Token> tokens = new LinkedList<>();

    private Token currentToken ;

    public NonTerminal(ArrayList<Token> tokens){

        for (Token token : tokens){
            this.tokens.add(token);
        }
    }

    // 	prog-decl --> heading  declarations  block  “.”
    public void progDecl(){
        currentToken = tokens.poll();
        heading();
        declarations();
        block();
        if(currentToken.getToken().equals(".")){
            System.out.println("Success");
        }else{
            System.out.println("Error");
        }
    }

    // heading --> program “program-name”  “;”
    public void heading(){
        if(currentToken.getToken().equals("program")){
             currentToken = tokens.poll();
        }else{
            System.out.println("Error");
            return;
        }

        if(currentToken.getTypeOfToken() == Type.USER_IDENTIFIER){
            currentToken = tokens.poll();
        }else{
            System.out.println("Error");
            return;
        }


        if(currentToken.getToken().equals(";")){
            currentToken = tokens.poll();
        }else{
            System.out.println("Error");
            return;
        }
    }

    // block --> begin   stmt-list  end
    public void block(){
        if(currentToken.getToken().equals("begin")){
            currentToken = tokens.poll();
        }else{
            System.out.println("Error");
            return;
        }

        stmtList();

        if(currentToken.getToken().equals("end")){
            currentToken = tokens.poll();
        }else{
            System.out.println("Error");
            return;
        }
    }

    // declarations --> const-decl  var-decl
    public void declarations(){
        constDecl();
        varDecl();
    }

    // const --> const  const-list  | lambda
    public void constDecl(){

        if(currentToken.getToken().equals("const")){

            currentToken = tokens.poll();
            constList();

        }else if(!currentToken.getToken().equals("var") && !currentToken.getToken().equals("begin")){
            System.out.println("Error");
            return;
        }
    }

    // const-list --> “const-name”  “=”  value  “;”  const-list  | lambda
    public void constList(){}

    public void varDecl(){}

    public void varList(){}

    public void varItem(){}

    public void nameList(){}

    public void moreNames(){}

    public void dataType(){}

    public void stmtList(){}

    public void statement(){}

    public void assStmt(){}

    public void exp(){}

    public void expPrime(){}

    public void term(){}

    public void termPrime(){}

    public void factor(){}

    public void addOper(){}

    public void mulOper(){}

    public void value(){}

    public void readStmt(){}

    public void writeStmt(){}

    public void ifStmt(){}

    public void elsePart(){}

    public void whileStmt (){}

    public void repeatStmt(){}

    public void condition(){}

    public void nameValue(){}

    public void relationalOper(){}

}
