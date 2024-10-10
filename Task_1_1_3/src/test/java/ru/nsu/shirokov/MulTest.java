package ru.nsu.shirokov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MulTest {

    @Test
    public void testPrint() {
        Expression expr = new Mul(new Number(2), new Variable("x"));
        assertEquals("(2*x)", expr.print());
    }

    @Test
    public void testDerivative() {
        Expression expr = new Mul(new Number(2), new Variable("x"));
        assertEquals("((0*x)+(2*1))", expr.derivative("x").print());
    }

    @Test
    public void testEval() {
        Expression expr = new Mul(new Number(2), new Variable("x"));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 5);
        assertEquals(10, expr.eval(variables));
    }
}
