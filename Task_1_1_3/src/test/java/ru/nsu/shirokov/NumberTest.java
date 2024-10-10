package ru.nsu.shirokov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

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