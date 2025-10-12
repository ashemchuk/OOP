package ru.ashemchuk.parser;

import java.util.HashMap;
import java.util.Map;

public class Signification {
    private final Map<String, Integer> SF; // SF - signification

    public Signification () {
        this.SF = new HashMap<>();
    }

    /**
     * @param signification must be in format: "x = 1; yx = 5", variable name, "=" and value (number),
     *       separated by semicolon, invalid records and whitespaces will be ignored
     */
    public final void parseSignification(String signification) {
        String[] significations = signification.split(";");
        for (String s : significations) {
            String[]  variableValue =  s.replace(" ", "").split("=");
            if (variableValue.length != 2) {
                continue;
            }
            String var = variableValue[0];
            int value;
            try {
                value = Integer.parseInt(variableValue[1]);
            } catch (Exception ex) {
                continue;
            }
            SF.put(var, value);
        }
    }

    /**
     * @param variable - String name of variable
     * @return if variable is in signification - value, else - null
     */
    public Integer getValue (String variable) {
        return SF.get(variable);
    }

}
