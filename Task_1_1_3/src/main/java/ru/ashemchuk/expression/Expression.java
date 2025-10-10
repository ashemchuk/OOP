package ru.ashemchuk.expression;

import java.util.HashMap;
import java.util.Map;

public abstract class Expression {
    protected static Map<String, Integer> SF = new HashMap<>(); // another class???

    public abstract Expression derivative(String var);
    public abstract Expression eval(String signification);
    public abstract Expression simplify();

    @Override
    public abstract String toString();


    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }


    public void print (){
        System.out.println(this);
    }

    public final void parseSignification(String signification) {
        String[] significations_str = signification.split(";");
        for (String s : significations_str) {
            String[]  variableValue =  s.replace(" ", "").split("=");
            if (variableValue.length != 2) {
                continue;
            }
            String var = variableValue[0];
            Integer value = Integer.parseInt(variableValue[1]);
            SF.put(var, value);
        }
    }

    protected final Integer getValue (String variable) {
        return SF.get(variable);
    }
}
