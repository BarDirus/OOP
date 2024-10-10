package ru.nsu.shirokov;

import java.util.Map;

/**
 * Класс Mul (Умножение).
 */
public class Mul extends Expression {
    private final Expression left, right;

    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String print() {
        return "(" + left.print() + "*" + right.print() + ")";
    }

    /*
     * Производная произведения: (f * g)' = f' * g + f * g'
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(
                new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))
        );
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) * right.eval(variables);
    }
}
