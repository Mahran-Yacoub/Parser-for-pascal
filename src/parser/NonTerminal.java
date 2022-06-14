package parser;

import lexical.CONSTANT;
import lexical.Token;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class NonTerminal {

    private Queue<Token> tokens = new LinkedList<>();

    private Token currentToken;

    public NonTerminal(ArrayList<Token> tokens) {

        for (Token token : tokens) {
            this.tokens.add(token);
        }
    }

    // 	prog-decl --> heading  declarations  block  “.”
    public void progDecl(){
        currentToken = tokens.poll();
        heading();
        declarations();
        block();
        if (currentToken.getToken().equals(".")) {
            System.out.println("Success");
        } else {
            System.out.println("Error program Declaration \t" + currentToken);
        }
    }

    // heading --> program “program-name”  “;”
    public void heading(){
        if (currentToken.getToken().equals("program")) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error heading \t" + currentToken);
            return;
        }

        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error");
            return;
        }


        if (currentToken.getToken().equals(";")) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error");
            return;
        }
    }

    // block --> begin   stmt-list  end
    public void block(){
        if (currentToken.getToken().equals("begin")) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error block \t" + currentToken);
            return;
        }

        stmtList();

        if (currentToken.getToken().equals("end"))  {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error");
            return;
        }
    }

    // declarations --> const-decl  var-decl
    public void declarations() {
        constDecl();
        varDecl();
    }

    // const-decl  --> const  const-list  | lambda
    public void constDecl(){

        if (currentToken.getToken().equals("const")) {

            currentToken = tokens.poll();
            constList();

        } else if (!currentToken.getToken().equals("var") && !currentToken.getToken().equals("begin")) {
            System.out.println("Error const declaration \t" + currentToken);
            return;
        }
    }

    // const-list --> “const-name”  “=”  value  “;”  const-list  | lambda
    public void constList(){
        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID) {

            currentToken = tokens.poll();

            if (currentToken.getToken().equals("=")) {
                currentToken = tokens.poll();
            } else {
                System.out.println("Error constant list \t" + currentToken);
                return;
            }

            value();

            if (currentToken.getToken().equals(";")) {
                currentToken = tokens.poll();
            } else {
                System.out.println("Error constant list \t" + currentToken);
                return;
            }

            constList();

        } else if (!currentToken.getToken().equals("var") && !currentToken.getToken().equals("begin")) {
            System.out.println("Error constant list \t" + currentToken);
            return;
        }
    }

    // var-decl --> var var-list  | lambda
    public void varDecl(){
        if (currentToken.getToken().equals("var")) {

            currentToken = tokens.poll();
            varList();

        } else if (!currentToken.getToken().equals("begin")) {
            System.out.println("Error var declaration \t" + currentToken);
            return;
        }
    }

    // var-list --> var-item  “;”  var-list  | lambda
    public void varList(){
        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID) {
            varItem();

            if (currentToken.getToken().equals(";")) {
                currentToken = tokens.poll();
            } else {
                System.out.println("Error var list \t" + currentToken);
                return;
            }

            varList();

        } else if (!currentToken.getToken().equals("begin")) {
            System.out.println("Error var list \t" + currentToken);
            return;
        }

    }

    // var-item --> name-list  “:”  data-type
    public void varItem(){
        nameList();
        if (currentToken.getToken().equals(":")) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error var item \t" + currentToken);
            return;
        }
        dataType();
    }

    // name-list --> “var-name”  more-names
    public void nameList(){
        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error name list \t" + currentToken);
            return;
        }

        moreNames();
    }

    // more-names --> “,” name-list | lambda
    public void moreNames(){
        if (currentToken.getToken().equals(",")) {
            currentToken = tokens.poll();
            nameList();
        } else if (!currentToken.getToken().equals(":") &&
                !currentToken.getToken().equals(")")) {
            System.out.println("Error more names \t" + currentToken);
            return;
        }
    }

    //data-type --> integer | real |  char
    public void dataType(){
        if (currentToken.getToken().equals("integer") || currentToken.getToken().equals("real")
                || currentToken.getToken().equals("char")) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error data type \t" + currentToken);
            return;
        }
    }

    // stmt-list --> statement  “;” stmt-list  | lambda
    public void stmtList(){

        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID || currentToken.getToken().equals("read")
                || currentToken.getToken().equals("write") || currentToken.getToken().equals("if") ||
                currentToken.getToken().equals("while") || currentToken.getToken().equals("repeat") ||
                currentToken.getToken().equals("begin") || currentToken.getToken().equals("readln") ||
                currentToken.getToken().equals("writeln")) {

            statement();

            if (currentToken.getToken().equals(";")) {
                currentToken = tokens.poll();
            } else {
                System.out.println("Error statement list \t" + currentToken);
                return;
            }

            stmtList();

        } else if (!currentToken.getToken().equals("end") && !currentToken.getToken().equals("until")) {
            System.out.println("Error statement list \t" + currentToken);
            return;
        }
    }

    // statement --> ass-stmt  | read-stmt | write-stmt | if-stmt | while-stmt | repeat-stmt  | block
    public void statement(){

        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID) {
            assStmt();
        } else if (currentToken.getToken().equals("read") || currentToken.getToken().equals("readln")) {
            readStmt();
        } else if (currentToken.getToken().equals("write") || currentToken.getToken().equals("writeln")) {
            writeStmt();
        } else if (currentToken.getToken().equals("if")) {
            ifStmt();
        } else if (currentToken.getToken().equals("while")) {
            whileStmt();
        } else if (currentToken.getToken().equals("repeat")) {
            repeatStmt();
        } else if (currentToken.getToken().equals("begin")) {
            block();
        } else {
            System.out.println("Error ror statement \t" + currentToken);
            return;
        }
    }

    // ass-stmt --> “var-name”  “:=”  exp
    public void assStmt(){
        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error assStmt \t" + currentToken);
            return;
        }

        if (currentToken.getToken().equals(":=")) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error assStmt \t" + currentToken);
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
        if (currentToken.getToken().equals("+") || currentToken.getToken().equals("-")) {
            addOper();
            term();
            expPrime();
        } else if (!currentToken.getToken().equals("else") &&
                !currentToken.getToken().equals(";") &&
                !currentToken.getToken().equals(")")) {
            System.out.println("Error exp prime \t" + currentToken);
            return;
        }
    }

    // term --> factor term-prime
    public void term(){
        factor();
        termPrime();
    }

    // term-prime --> mul-oper  factor  term-prime  | lambda
    public void termPrime(){
        if (currentToken.getToken().equals("*") || currentToken.getToken().equals("/") ||
                currentToken.getToken().equals("mod") || currentToken.getToken().equals("div")) {
            mulOper();
            factor();
            termPrime();
        } else if (!currentToken.getToken().equals("+") && !currentToken.getToken().equals("-")
                && !currentToken.getToken().equals(";") && !currentToken.getToken().equals("else")
                && !currentToken.getToken().equals(")")) {
            System.out.println("Error term prime \t" + currentToken);
            return;
        }
    }

    // factor --> “(“ exp  “)” |  name-value
    public void factor(){

        if (currentToken.getToken().equals("(")) {
            currentToken = tokens.poll();
            exp();
            if (currentToken.getToken().equals(")")) {
                currentToken = tokens.poll();
            } else {
                System.out.println("Error factor \t" + currentToken);
                return;
            }
        } else if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID
                || currentToken.getId() == CONSTANT.FLOAT_ID || currentToken.getId() == CONSTANT.INTEGER_ID) {
            nameValue();
        } else {
            System.out.println("Error factor \t" + currentToken);
            return;
        }
    }

    //add-oper --> “+”  |  “-“
    public void addOper(){
        if (currentToken.getToken().equals("+") || currentToken.getToken().equals("-")) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error \t" + currentToken);
            return;
        }
    }

    //mul-oper --> “*”  |  “/”    |   mod  | div
    public void mulOper(){
        if (currentToken.getToken().equals("*") || currentToken.getToken().equals("/") ||
                currentToken.getToken().equals("mod") || currentToken.getToken().equals("div")) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error \t" + currentToken);
            return;
        }
    }

    //value --> “float-value”  | “integer-value”
    public void value(){
        if (currentToken.getId() == CONSTANT.FLOAT_ID || currentToken.getId() == CONSTANT.INTEGER_ID) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error \t" + currentToken);
            return;
        }
    }

    // read-stmt --> read  “(“ name-list  “)”  | readln  “(“ name-list “)”
    public void readStmt(){

        if (currentToken.getToken().equals("read")
                || currentToken.getToken().equals("readln")) {

            currentToken = tokens.poll();

            if (currentToken.getToken().equals("(")) {
                currentToken = tokens.poll();
            } else {
                System.out.println("Error \t" + currentToken);
                return;
            }

            nameList();

            if (currentToken.getToken().equals(")")) {
                currentToken = tokens.poll();
            } else {
                System.out.println("Error \t" + currentToken);
                return;
            }

        } else {
            System.out.println("Error \t" + currentToken);
            return;
        }
    }

    // write-stmt --> write  “(“ name-list “)”  |  writeln  “(“ name-list “)”
    public void writeStmt(){
        if (currentToken.getToken().equals("write")
                || currentToken.getToken().equals("writeln")) {

            currentToken = tokens.poll();

            if (currentToken.getToken().equals("(")) {
                currentToken = tokens.poll();
            } else {
                System.out.println("Error write statement \t" + currentToken);
                return;
            }

            nameList();

            if (currentToken.getToken().equals(")")) {
                currentToken = tokens.poll();
            } else {
                System.out.println("Error \t" + currentToken);
                return;
            }

        } else {
            System.out.println("Error \t" + currentToken);
            return;
        }
    }

    // if-stmt --> if  condition  then  statement  else-part
    public void ifStmt(){

        if (currentToken.getToken().equals("if")) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error if stmt 1 statement \t" + currentToken);
            return;
        }

        condition();

        if (currentToken.getToken().equals("then")) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error ifstmt 2 statement \t" + currentToken);
            return;
        }

        statement();

        elsePart();
    }

    // else-part --> else  statement  | lambda
    public void elsePart(){
        if (currentToken.getToken().equals("else")) {
            currentToken = tokens.poll();
            statement();
        } else if (!currentToken.getToken().equals(";")) {
            System.out.println("Error else part statement \t" + currentToken);
            return;
        }
    }

    // while-stmt --> while  condition  do  statement
    public void whileStmt(){
        if (currentToken.getToken().equals("while")) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error \t" + currentToken);
            return;
        }

        condition();

        if (currentToken.getToken().equals("do")) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error");
            return;
        }

        statement();

    }

    // repeat-stmt --> repeat   stmt-list   until   condition
    public void repeatStmt(){
        if (currentToken.getToken().equals("repeat")) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error \t" + currentToken);
            return;
        }

        stmtList();

        if (currentToken.getToken().equals("until")) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error \t" + currentToken);
            return;
        }

        condition();
    }

    // condition --> name-value   relational-oper  name-value
    public void condition(){
        nameValue();
        relationalOper();
        nameValue();
    }

    // name-value --> “var-name”  | “const-name”  | value
    public void nameValue(){
        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID || currentToken.getId() == CONSTANT.INTEGER_ID ||
                currentToken.getId() == CONSTANT.FLOAT_ID) {
            currentToken = tokens.poll();
        } else {
            System.out.println("Error \t" + currentToken);
            return;
        }
    }

    // relational-oper --> “=”  |   “<>”    |   “<” |  “<=” |  “>” |   “>=”
    public void relationalOper(){
        if (currentToken.getToken().equals("=") || currentToken.getToken().equals("<>") ||
                currentToken.getToken().equals("<") || currentToken.getToken().equals("<=") ||
                currentToken.getToken().equals(">") || currentToken.getToken().equals(">=")) {

            currentToken = tokens.poll();
        } else {
            System.out.println("Error \t" + currentToken);
            return;
        }
    }
}
