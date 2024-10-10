package ru.nsu.shirokov;

import java.util.Map;

/**
 * Класс Expression (Выражение)
 */
public abstract class Expression {
    public abstract String print();
    /*
     * символьное дифференцирование
     */

    public abstract Expression derivative(String variable);

    public abstract int eval(Map<String, Integer> variables);
}


