package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.Characters;
import nl.java.shakespearelang.parser.Push;
import nl.java.shakespearelang.parser.line.InputStatement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class PushPerformer {
    private Push line;
    private Characters characters;

    public PushPerformer(Push line, Characters characters) {
        this.line = line;
        this.characters = characters;
    }

    public void performPushStatement() {
        
    }
}
