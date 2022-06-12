package parser;

import lexical.CONSTANT;
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

        if(currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID){
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

    // const-decl  --> const  const-list  | lambda
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
    public void constList(){
        if(currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID){

            currentToken = tokens.poll();

            if(currentToken.getToken().equals("=")){
                currentToken = tokens.poll();
            }else {
                System.out.println("Error");
                return;
            }

            value();

            if(currentToken.getToken().equals(";")){
                currentToken = tokens.poll();
            }else {
                System.out.println("Error");
                return;
            }

            constList();

        }else if(!currentToken.getToken().equals("var") && !currentToken.getToken().equals("begin")){
            System.out.println("Error");
            return;
        }
    }

    // var-decl --> var var-list  | lambda
    public void varDecl(){
        if(currentToken.getToken().equals("var")){

            currentToken = tokens.poll();
            varList();

        }else if(!currentToken.getToken().equals("begin")){
            System.out.println("Error");
            return;
        }
    }

    // var-list --> var-item  “;”  var-list  | lambda
    public void varList(){
        if(currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID){
            varItem();

            if(currentToken.getToken().equals(";")){
                currentToken = tokens.poll();
            }else{
                System.out.println("Error");
                return;
            }

            varList();

        }else if(!currentToken.getToken().equals("begin")){
            System.out.println("Error");
            return;
        }

    }

    // var-item --> name-list  “:”  data-type
    public void varItem(){
        nameList();
        if(currentToken.getToken().equals(":")){
            currentToken = tokens.poll();
        }else {
            System.out.println("Error");
            return;
        }
        dataType();
    }

    // name-list --> “var-name”  more-names
    public void nameList(){
        if(currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID){
            currentToken = tokens.poll();
        }else{
            System.out.println("Error");
            return;
        }

        moreNames();
    }

    // more-names --> “,” name-list | lambda
    public void moreNames(){
        if(currentToken.getToken().equals(",")){
            currentToken = tokens.poll();
            nameList();
        }else if (!currentToken.getToken().equals(":")){
            System.out.println("Error");
            return;
        }
    }

    //data-type --> integer | real |  char
    public void dataType(){
        if(currentToken.getToken().equals("integer")){

        }else if(currentToken.getToken().equals("real")){

        }else if(currentToken.getToken().equals("char")){

        }else {
            System.out.println("Error");
            return;
        }
    }

    // stmt-list --> statement  “;” stmt-list  | lambda
    public void stmtList(){

        if(currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID || currentToken.getToken().equals("read")
        || currentToken.getToken().equals("write") || currentToken.getToken().equals("if") ||
                currentToken.getToken().equals("while") || currentToken.getToken().equals("repeat") ||
                currentToken.getToken().equals("begin")){

            statement();

            if(currentToken.getToken().equals(";")){
                currentToken = tokens.poll();
            }else{
                System.out.println("Error");
                return;
            }

            stmtList();

        }else if (!currentToken.getToken().equals("end")){
            System.out.println("Error");
            return;
        }
    }

    // statement --> ass-stmt  | read-stmt | write-stmt | if-stmt | while-stmt | repeat-stmt  | block
    public void statement(){

                if(currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID){
                    assStmt();
                }else if(currentToken.getToken().equals("read")){
                    readStmt();
                }else if(currentToken.getToken().equals("write")){
                    writeStmt();
                }else if (currentToken.getToken().equals("if")){
                    ifStmt();
                }else if (currentToken.getToken().equals("while")){
                    whileStmt();
                }else if (currentToken.getToken().equals("repeat")){
                    readStmt();
                }else if (currentToken.getToken().equals("begin")){
                    block();
                }else {
                    System.out.println("Error");
                    return;
                }
    }

    // ass-stmt --> “var-name”  “:=”  exp
    public void assStmt(){
        if(currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID){
            currentToken = tokens.poll();
        }else {
            System.out.println("Error");
            return;
        }

        if(currentToken.getToken().equals(":=")){
            currentToken = tokens.poll();
        }else {
            System.out.println("Error");
            return;
        }

        exp();
    }

    // exp --> term  exp-prime
    public void exp(){
        term();
        expPrime();
    }

    // exp-prime -->  add-oper term  exp-prime | lambda
    public void expPrime(){
        if(currentToken.getToken().equals("+") || currentToken.getToken().equals("-") ){
            addOper();
            term();
            expPrime();
        }else if (!currentToken.getToken().equals("end")){
            System.out.println("Error");
            return;
        }
    }

    // term --> factor term-prime
    public void term(){
        factor();
        termPrime();
    }

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
