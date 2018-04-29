package nl.java.shakespearelang.executor;

import lombok.Getter;

@Getter
public class ActScene {
    private int act;
    private int scene;
    private boolean exeunt;

    public ActScene(int act, int scene){
        this.act = act;
        this.scene = scene;
        this.exeunt = false;
    }

    public ActScene(){
        this.exeunt = true;
    }
}
