package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.ExecutionException;
import nl.java.shakespearelang.parser.line.InputStatement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class InputStatementPerformer {
    private InputStatement inputStatement;

    public InputStatementPerformer(InputStatement line) {
        this.inputStatement = line;
    }

    public int performInputStatement(Reader characterReader) throws IOException {
        if (inputStatement.readNumber()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                return Integer.parseInt(br.readLine());
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format!");
            }
        } else {
            try {
                return characterReader.read();
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format!");
            }
        }
        throw new ExecutionException("unknown input type");
    }
}
