package nl.java.shakespearelang.parser;

import java.util.ArrayList;
import java.util.List;

public class EnterExit {
    private Action action;
    private List<String> players = new ArrayList<>();

    public EnterExit(String enterExit) throws Exception {
        String[] words = enterExit.replace("[", "").replace("]", "").trim().split(" ");
        if (words[0].startsWith("Enter")) {
            action = Action.ENTER;
        } else if (words[0].startsWith("Exit")) {
            action = Action.EXIT;
        } else if (words[0].startsWith("Exeunt")) {
            action = Action.EXEUNT;
        } else {
            throw new Exception("foutje!");
        }
        for(int i=1;i<words.length;i++){
            if(!words[i].equals("and")){
                players.add(words[i]);
            }
        }

    }
}
