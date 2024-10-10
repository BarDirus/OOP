package ru.nsu.shirokov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;



class DivTest {

    @Test
    public void testPrint() {
        Expression expr = new Div(new Number(10), new Variable("x"));
        assertEquals("(10/x)", expr.print());
    }

    @Test
    public void testDerivative() {
        Expression expr = new Div(new Number(10), new Variable("x"));
        assertEquals("(((0*x)-(10*1))/(x*x))", expr.derivative("x").print());
    }

    @Test
    public void testEval() {
        Expression expr = new Div(new Number(10), new Variable("x"));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 5);
        assertEquals(2, expr.eval(variables));
    }
}
