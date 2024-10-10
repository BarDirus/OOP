package ru.nsu.shirokov;

import java.util.Map;

/**
 * Класс Variable (переменная)
 */
public class Variable extends Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String print() {
        return name;
    }

    @Override
    public Expression derivative(String variable) {
        return variable.equals(name) ? new Number(1) : new Number(0);
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else {
            throw new IllegalArgumentException("Variable " + name + " is not defined");
        }
    }
}
