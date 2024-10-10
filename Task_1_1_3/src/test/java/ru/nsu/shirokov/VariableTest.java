package ru.nsu.shirokov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

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