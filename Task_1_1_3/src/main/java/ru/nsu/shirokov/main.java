package ru.nsu.shirokov;

import java.util.HashMap;
import java.util.Map;

public class main {
    public static void main(String[] args) {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        System.out.println("Expression: " + e.print());
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        int result = e.eval(variables);
        System.out.println("Evaluation with x=10: " + result);
        Expression de = e.derivative("x");
        System.out.println("Derivative: " + de.print());
        int derivativeResult = de.eval(variables);
        System.out.println("Derivative evaluation with x=10: " + derivativeResult);
    }
}
