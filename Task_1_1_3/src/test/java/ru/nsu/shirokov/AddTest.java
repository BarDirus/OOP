package ru.nsu.shirokov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;


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
