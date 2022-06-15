package parser;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import lexical.CONSTANT;
import lexical.Token;

import java.util.ArrayList;

public class NonTerminal {

    private SelectToken tokens;
    private Token currentToken;
    private String errorMessage;


    public String getErrorMessage() {
        return errorMessage;
    }

    private void syntaxError() {
        errorMessage = "Syntax Error in Token: " + currentToken.getToken() + "   in line: " + tokens.numberOfLine();
        throw new SyntaxException(errorMessage);
    }

    public NonTerminal(ArrayList<Token> tokens) {

        this.tokens = new SelectToken(tokens);
    }

    // 	prog-decl --> heading  declarations  block  “.”
    public void progDecl() {
        currentToken = tokens.poll();
        heading();
        declarations();
        block();
        if (currentToken.getToken().equals(".")) {
            System.out.println("Success");
        } else {
            syntaxError();
        }
    }

    // heading --> program “program-name”  “;”
    public void heading() {
        if (currentToken.getToken().equals("program")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }

        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }


        if (currentToken.getToken().equals(";")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }
    }

    // block --> begin   stmt-list  end
    public void block() {
        if (currentToken.getToken().equals("begin")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }

        stmtList();

        if (currentToken.getToken().equals("end")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }
    }

    // declarations --> const-decl  var-decl
    public void declarations() {
        constDecl();
        varDecl();
    }

    // const-decl  --> const  const-list  | lambda
    public void constDecl() {

        if (currentToken.getToken().equals("const")) {

            currentToken = tokens.poll();
            constList();

        } else if (!currentToken.getToken().equals("var") && !currentToken.getToken().equals("begin")) {
            syntaxError();
        }
    }

    // const-list --> “const-name”  “=”  value  “;”  const-list  | lambda
    public void constList() {
        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID) {

            currentToken = tokens.poll();

            if (currentToken.getToken().equals("=")) {
                currentToken = tokens.poll();
            } else {
                syntaxError();
            }

            value();

            if (currentToken.getToken().equals(";")) {
                currentToken = tokens.poll();
            } else {
                syntaxError();
            }

            constList();

        } else if (!currentToken.getToken().equals("var") && !currentToken.getToken().equals("begin")) {
            syntaxError();
        }
    }

    // var-decl --> var var-list  | lambda
    public void varDecl() {
        if (currentToken.getToken().equals("var")) {

            currentToken = tokens.poll();
            varList();

        } else if (!currentToken.getToken().equals("begin")) {
            syntaxError();
        }
    }

    // var-list --> var-item  “;”  var-list  | lambda
    public void varList() {
        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID) {
            varItem();

            if (currentToken.getToken().equals(";")) {
                currentToken = tokens.poll();
            } else {
                syntaxError();
            }

            varList();

        } else if (!currentToken.getToken().equals("begin")) {
            syntaxError();
        }

    }

    // var-item --> name-list  “:”  data-type
    public void varItem() {
        nameList();
        if (currentToken.getToken().equals(":")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }
        dataType();
    }

    // name-list --> “var-name”  more-names
    public void nameList() {
        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }

        moreNames();
    }

    // more-names --> “,” name-list | lambda
    public void moreNames() {
        if (currentToken.getToken().equals(",")) {
            currentToken = tokens.poll();
            nameList();
        } else if (!currentToken.getToken().equals(":") &&
                !currentToken.getToken().equals(")")) {
            syntaxError();
        }
    }

    //data-type --> integer | real |  char
    public void dataType() {
        if (currentToken.getToken().equals("integer") || currentToken.getToken().equals("real")
                || currentToken.getToken().equals("char")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }
    }

    // stmt-list --> statement  “;” stmt-list  | lambda
    public void stmtList() {

        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID || currentToken.getToken().equals("read")
                || currentToken.getToken().equals("write") || currentToken.getToken().equals("if") ||
                currentToken.getToken().equals("while") || currentToken.getToken().equals("repeat") ||
                currentToken.getToken().equals("begin") || currentToken.getToken().equals("readln") ||
                currentToken.getToken().equals("writeln")) {

            statement();

            if (currentToken.getToken().equals(";")) {
                currentToken = tokens.poll();
            } else {
                syntaxError();
            }

            stmtList();

        } else if (!currentToken.getToken().equals("end") && !currentToken.getToken().equals("until")) {
            syntaxError();
        }
    }

    // statement --> ass-stmt  | read-stmt | write-stmt | if-stmt | while-stmt | repeat-stmt  | block
    public void statement() {

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
            syntaxError();
        }
    }

    // ass-stmt --> “var-name”  “:=”  exp
    public void assStmt() {
        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }

        if (currentToken.getToken().equals(":=")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }

        exp();
    }

    // exp --> term  exp-prime
    public void exp() {
        term();
        expPrime();
    }

    // exp-prime -->  add-oper term  exp-prime | lambda
    public void expPrime() {
        if (currentToken.getToken().equals("+") || currentToken.getToken().equals("-")) {
            addOper();
            term();
            expPrime();
        } else if (!currentToken.getToken().equals("else") &&
                !currentToken.getToken().equals(";") &&
                !currentToken.getToken().equals(")")) {
            syntaxError();
        }
    }

    // term --> factor term-prime
    public void term() {
        factor();
        termPrime();
    }

    // term-prime --> mul-oper  factor  term-prime  | lambda
    public void termPrime() {
        if (currentToken.getToken().equals("*") || currentToken.getToken().equals("/") ||
                currentToken.getToken().equals("mod") || currentToken.getToken().equals("div")) {
            mulOper();
            factor();
            termPrime();
        } else if (!currentToken.getToken().equals("+") && !currentToken.getToken().equals("-")
                && !currentToken.getToken().equals(";") && !currentToken.getToken().equals("else")
                && !currentToken.getToken().equals(")")) {
            syntaxError();
        }
    }

    // factor --> “(“ exp  “)” |  name-value
    public void factor() {

        if (currentToken.getToken().equals("(")) {
            currentToken = tokens.poll();
            exp();
            if (currentToken.getToken().equals(")")) {
                currentToken = tokens.poll();
            } else {
                syntaxError();
            }
        } else if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID
                || currentToken.getId() == CONSTANT.FLOAT_ID || currentToken.getId() == CONSTANT.INTEGER_ID) {
            nameValue();
        } else {
            syntaxError();
        }
    }

    //add-oper --> “+”  |  “-“
    public void addOper() {
        if (currentToken.getToken().equals("+") || currentToken.getToken().equals("-")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
            ;
        }
    }

    //mul-oper --> “*”  |  “/”    |   mod  | div
    public void mulOper() {
        if (currentToken.getToken().equals("*") || currentToken.getToken().equals("/") ||
                currentToken.getToken().equals("mod") || currentToken.getToken().equals("div")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }
    }

    //value --> “float-value”  | “integer-value”
    public void value() {
        if (currentToken.getId() == CONSTANT.FLOAT_ID || currentToken.getId() == CONSTANT.INTEGER_ID) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }
    }

    // read-stmt --> read  “(“ name-list  “)”  | readln  “(“ name-list “)”
    public void readStmt() {

        if (currentToken.getToken().equals("read")
                || currentToken.getToken().equals("readln")) {

            currentToken = tokens.poll();

            if (currentToken.getToken().equals("(")) {
                currentToken = tokens.poll();
            } else {
                syntaxError();
            }

            nameList();

            if (currentToken.getToken().equals(")")) {
                currentToken = tokens.poll();
            } else {
                syntaxError();
            }

        } else {
            syntaxError();
        }
    }

    // write-stmt --> write  “(“ name-list “)”  |  writeln  “(“ name-list “)”
    public void writeStmt() {
        if (currentToken.getToken().equals("write")
                || currentToken.getToken().equals("writeln")) {

            currentToken = tokens.poll();

            if (currentToken.getToken().equals("(")) {
                currentToken = tokens.poll();
            } else {
                syntaxError();
            }

            nameList();

            if (currentToken.getToken().equals(")")) {
                currentToken = tokens.poll();
            } else {
                syntaxError();
            }

        } else {
            syntaxError();
        }
    }

    // if-stmt --> if  condition  then  statement  else-part
    public void ifStmt() {

        if (currentToken.getToken().equals("if")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }

        condition();

        if (currentToken.getToken().equals("then")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }

        statement();

        elsePart();
    }

    // else-part --> else  statement
    public void elsePart() {
        if (currentToken.getToken().equals("else")) {
            currentToken = tokens.poll();
            statement();
        } else {
            syntaxError();
        }
    }

    // while-stmt --> while  condition  do  statement
    public void whileStmt() {
        if (currentToken.getToken().equals("while")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }

        condition();

        if (currentToken.getToken().equals("do")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }

        statement();

    }

    // repeat-stmt --> repeat   stmt-list   until   condition
    public void repeatStmt() {
        if (currentToken.getToken().equals("repeat")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }

        stmtList();

        if (currentToken.getToken().equals("until")) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }

        condition();
    }

    // condition --> name-value   relational-oper  name-value
    public void condition() {
        nameValue();
        relationalOper();
        nameValue();
    }

    // name-value --> “var-name”  | “const-name”  | value
    public void nameValue() {
        if (currentToken.getId() == CONSTANT.USER_IDENTIFIERS_ID || currentToken.getId() == CONSTANT.INTEGER_ID ||
                currentToken.getId() == CONSTANT.FLOAT_ID) {
            currentToken = tokens.poll();
        } else {
            syntaxError();
        }
    }

    // relational-oper --> “=”  |   “<>”    |   “<” |  “<=” |  “>” |   “>=”
    public void relationalOper() {
        if (currentToken.getToken().equals("=") || currentToken.getToken().equals("<>") ||
                currentToken.getToken().equals("<") || currentToken.getToken().equals("<=") ||
                currentToken.getToken().equals(">") || currentToken.getToken().equals(">=")) {

            currentToken = tokens.poll();
        } else {
            syntaxError();
        }
    }
}
