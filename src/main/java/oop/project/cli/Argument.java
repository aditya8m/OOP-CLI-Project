package oop.project.cli;

public class Argument {
    String name;
    ArgumentType argType;
    boolean optional = false;
    boolean named = false;


    public Argument(String name, ArgumentType argType, boolean optional, boolean named)
    {
        this.name = name;
        this.argType = argType;
        this.optional = optional;
        this.named = named;
    }

}
