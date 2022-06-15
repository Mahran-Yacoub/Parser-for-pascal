package main;

import lexical.Token;
import lexical.Tokenization;
import parser.NonTerminal;
import java.io.File;
import java.util.ArrayList;


public class Main  {

    public static void main(String[] args) {

        Tokenization tokenization = new Tokenization();
        ArrayList<Token> tokens =
                tokenization.getTokens(new File("C:\\Users\\HP\\OneDrive\\Desktop\\second semester\\COMP 439 _ Compiler\\Project\\H1-test.txt"));

        NonTerminal nonTerminal = new NonTerminal(tokens);
        try {
            nonTerminal.progDecl();
        }catch (NullPointerException exception){
            System.out.println("No Tokens");
        }
    }
}
