package main;

import lexical.Token;
import lexical.Tokenization;
import parser.NonTerminal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main  {

    public static void main(String[] args) throws FileNotFoundException {

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

    private static  boolean isExist(String keyword) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/programs/keywords.txt"));
        ArrayList<String> strings = new ArrayList<>();
        while (scanner.hasNextLine()){
            String line = scanner.nextLine().trim();
            strings.add(line);
        }

        if(strings.contains(keyword.trim())){
            return true;
        }else {
            return false ;
        }

    }
}
