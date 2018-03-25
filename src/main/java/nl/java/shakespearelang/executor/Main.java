package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.parser.Play;

public class Main {

    private static final String programString = "The Infamous Hello World Play.\n"
        + "\n"
        + "Romeo, a young man with a remarkable patience.\n"
        + "Juliet, a likewise young woman of remarkable grace.\n"
        + "Ophelia, a remarkable woman much in dispute with Hamlet.\n"
        + "Hamlet, the flatterer of Andersen Insulting A/S.\n"
        + "\n"
        + "\n"
        + "                    Act I: Hamlet's insults and flattery.\n"
        + "\n"
        + "                    Scene I: The insulting of Romeo.\n"
        + "\n"
        + "[Enter Hamlet and Romeo]\n"
        + "\n"
        + "Hamlet:\n"
        + " You lying stupid fatherless big smelly half-witted coward!\n"
        + " You are as stupid as the difference between a handsome rich brave\n"
        + " hero and thyself! Speak your mind!\n"
        + " \n"
        + "You are as brave as the sum of your fat little stuffed misused dusty\n"
        + " old rotten codpiece and a beautiful fair warm peaceful sunny summer's\n"
        + " day. You are as healthy as the difference between the sum of the\n"
        + " sweetest reddest rose and my father and YOURSELF! Speak your mind!\n"
        + "\n"
        + " You are as cowardly as the sum of YOURSELF and the difference\n"
        + " between a big mighty proud kingdom and a horse. Speak your mind.\n"
        + "\n"
        + " Speak your mind!\n"
        + "\n"
        + "[Exit Romeo]\n"
        + "\n"
        + "                    Scene II: The praising of Juliet.\n"
        + "\n"
        + "[Enter Juliet]\n"
        + "\n"
        + "Hamlet:\n"
        + " Thou art as sweet as the sum of the sum of Romeo and his horse and his\n"
        + " black cat! Speak thy mind!";

    public static void main(String[] args) throws Exception {
        Play play = new Play(programString);
        PlayPerformer executor = new PlayPerformer(play);
        executor.performPlay();
    }
}
