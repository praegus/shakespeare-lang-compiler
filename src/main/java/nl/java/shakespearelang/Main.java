package nl.java.shakespearelang;

import nl.java.shakespearelang.executor.PlayPerformer;
import nl.java.shakespearelang.parser.Play;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args == null || args.length==0) {
            throw new RuntimeException("There is no program to load!");
        }
        if (args.length > 1) {
            throw new RuntimeException("Please give only one argument!");
        }
        Play play = new Play(args[0]);
        PlayPerformer playPerformer = new PlayPerformer(play);
        playPerformer.performPlay();
    }
}
