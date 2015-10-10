package org.essencemc.essencecore.message;

public class Param {

    private String name;
    private String value;

    public Param(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static Param P(String name, String value) {
        return new Param(name, value);
    }
}
