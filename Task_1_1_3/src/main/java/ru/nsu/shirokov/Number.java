package ru.nsu.shirokov;

import java.util.Map;

// Класс Number (константа)
public class Number extends Expression {
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public String print() {
        return String.valueOf(value);
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return value;
    }
}
