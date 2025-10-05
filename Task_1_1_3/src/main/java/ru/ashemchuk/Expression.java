package ru.ashemchuk;

import java.util.HashMap;
import java.util.Map;

public abstract class Expression {
    protected static Map<String, Integer> SF = new HashMap<>(); // another class???

    public abstract Expression derivative(String var);
    public abstract Expression eval(String signification);
    public abstract Expression simplify();

    @Override
    public abstract String toString();

    public void print (){
        System.out.println(this);
    }

    protected final void parseSignification (String signification) {
        String[] significations_str = signification.split(";");
        for (String s : significations_str) {
            String[]  variable_value =  s.replace(" ", "").split("=");
            if (variable_value.length != 2) {
                continue;
            }
            String var = variable_value[0];
            Integer value = Integer.parseInt(variable_value[1]);
            SF.put(var, value);
        }
    }

    protected final Integer getValue (String variable) {
        return SF.get(variable);
    }
}
