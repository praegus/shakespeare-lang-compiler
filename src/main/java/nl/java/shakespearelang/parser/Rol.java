package nl.java.shakespearelang.parser;

public class Rol {
    private String name;
    private int value;

    public Rol(String personae) {
        this.name = personae.substring(0, personae.indexOf(","));
    }

}
