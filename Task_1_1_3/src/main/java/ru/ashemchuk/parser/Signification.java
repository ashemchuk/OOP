package ru.ashemchuk.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a mapping of variable names to their integer values.
 * This class is used to store and retrieve variable significations for expression evaluation.
 */
public class Signification {
    private final Map<String, Integer> map; // SF - signification

    /**
     * Constructs a new empty Signification object.
     */
    public Signification() {
        this.map = new HashMap<>();
    }

    /**
     * Parses a signification string and populates the internal mapping.
     * The signification must be in format: "x = 1; yx = 5", with variable name,
     * "=" and value (number),
     * separated by semicolon. Invalid records and whitespaces will be ignored.
     *
     * @param signification the string containing variable assignments to parse
     */
    public final void parseSignification(String signification) {
        if (signification == null) {
            return;
        }
        String[] significations = signification.split(";");
        for (String s : significations) {
            String[] variableValue = s.replace(" ", "").split("=");
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
            map.put(var, value);
        }
    }

    /**
     * Retrieves the value associated with the specified variable.
     *
     * @param variable the String name of the variable to look up
     * @return the integer value if the variable is in the signification, null otherwise
     */
    public Integer getValue(String variable) {
        return map.get(variable);
    }
}