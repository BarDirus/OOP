package ru.nsu.shirokov;

import java.util.Map;

/**
 * Класс Sub ( ̶П̶о̶д̶л̶о̶д̶к̶а̶ Вычитание).
 */
public class Sub extends Expression {
    private final Expression left;
    private final Expression right;

    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression derivative(String variable) {
        /*
         * Производная разности: (f - g)' = f' - g'
         */

        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public String print() {
        return "(" + left.print() + "-" + right.print() + ")";
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) - right.eval(variables);
    }
}
