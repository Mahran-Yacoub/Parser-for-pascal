package userinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import lexical.Token;
import lexical.Tokenization;
import parser.NonTerminal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ParserUI {

    @FXML
    private TextArea sourceCode;

    @FXML
    private TextArea errorMessage;

    @FXML
    private Label outputStatus;

    private File file;


    @FXML
    void chooseFileAction(ActionEvent event) {
        clearScreen();
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(null);
        writeFileOnScreen();
        sourceCode.setEditable(false);
    }

    private void writeFileOnScreen() {
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine() + "\n";
                sourceCode.setText(sourceCode.getText() + line);
            }

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Wrong File");
            alert.show();
        }
    }

    @FXML
    void clearAction(ActionEvent event) {
        clearScreen();
    }

    @FXML
    void parsingAction(ActionEvent event) {

        Tokenization tokenization = new Tokenization();
        ArrayList<Token> tokens = tokenization.getTokens(file);
        NonTerminal nonTerminal = new NonTerminal(tokens);
        try {
            nonTerminal.progDecl();
            outputStatus.setText("Success");
        } catch (SyntaxException syntaxException) {
            errorMessage.setText(nonTerminal.getErrorMessage());
            outputStatus.setText("Fail Parsing ");
        } catch (NullPointerException nullPointerException) {
            errorMessage.setText("No Remains Terminals");
            outputStatus.setText("Fail Parsing");
        }
    }


    private void clearScreen() {
        sourceCode.clear();
        errorMessage.clear();
        outputStatus.setText("");
    }
}
