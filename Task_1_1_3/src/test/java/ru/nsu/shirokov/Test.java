package ru.nsu.shirokov;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NumberTest {

    @Test
    public void testPrint() {
        Number num = new Number(5);
        assertEquals("5", num.print());
    }

    @Test
    public void testDerivative() {
        Number num = new Number(5);
        assertEquals("0", num.derivative("x").print());
    }

    @Test
    public void testEval() {
        Number num = new Number(5);
        assertEquals(5, num.eval(null));  // Для константы переменные не нужны
    }
}
class VariableTest {

    @Test
    public void testPrint() {
        Variable var = new Variable("x");
        assertEquals("x", var.print());
    }

    @Test
    public void testDerivativeWithRespectToSameVariable() {
        Variable var = new Variable("x");
        assertEquals("1", var.derivative("x").print());
    }

    @Test
    public void testDerivativeWithRespectToDifferentVariable() {
        Variable var = new Variable("x");
        assertEquals("0", var.derivative("y").print());
    }

    @Test
    public void testEval() {
        Variable var = new Variable("x");
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        assertEquals(10, var.eval(variables));
    }

    @Test
    public void testEvalThrowsExceptionIfVariableNotDefined() {
        Variable var = new Variable("x");
        Map<String, Integer> variables = new HashMap<>();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            var.eval(variables);
        });
        assertTrue(exception.getMessage().contains("Variable x is not defined"));
    }
}

class AddTest {

    @Test
    public void testPrint() {
        Expression expr = new Add(new Number(3), new Variable("x"));
        assertEquals("(3+x)", expr.print());
    }

    @Test
    public void testDerivative() {
        Expression expr = new Add(new Number(3), new Variable("x"));
        assertEquals("(0+1)", expr.derivative("x").print());
    }

    @Test
    public void testEval() {
        Expression expr = new Add(new Number(3), new Variable("x"));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 5);
        assertEquals(8, expr.eval(variables));
    }
}
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