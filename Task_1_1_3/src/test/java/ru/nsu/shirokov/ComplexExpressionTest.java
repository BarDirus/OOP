package ru.nsu.shirokov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class ComplexExpressionTest {

    @Test
    public void testComplexExpressionPrint() {
        // (3 + (2 * x))
        Expression expr = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals("(3+(2*x))", expr.print());
    }

    @Test
    public void testComplexExpressionDerivative() {
        // (3 + (2 * x))
        Expression expr = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        Expression derivative = expr.derivative("x");
        assertEquals("(0+((0*x)+(2*1)))", derivative.print());
    }

    @Test
    public void testComplexExpressionEval() {
        // (3 + (2 * x))
        Expression expr = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 5);
        assertEquals(13, expr.eval(variables));
    }
}
