package nl.java.shakespearelang.executor;

import lombok.Getter;

@Getter
public class ActSceneLine {
    private int act;
    private int scene;
    private int line;
    private boolean exeunt;

    public ActSceneLine(int act, int scene, int line) {
        this.act = act;
        this.scene = scene;
        this.line = line;
        this.exeunt = false;
    }

    public ActSceneLine() {
        this.exeunt = true;
    }

    public static ActSceneLine next(ActSceneLine actSceneLine) {
        return new ActSceneLine(actSceneLine.act, actSceneLine.scene, actSceneLine.line + 1);
    }
}
