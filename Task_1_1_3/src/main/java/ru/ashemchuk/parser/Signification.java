package ru.ashemchuk.parser;

import java.util.HashMap;
import java.util.Map;

public class Signification {
    private Map<String, Integer> SF; // SF - singnification

    public Signification () {
        this.SF = new HashMap<>();
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

    public Integer getValue (String variable) {
        return SF.get(variable);
    }

}
