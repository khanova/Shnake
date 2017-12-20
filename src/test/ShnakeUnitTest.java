package test;

import static org.junit.Assert.*;

import main.*;
import main.Objects.*;
import org.junit.Test;

public class ShnakeUnitTest {
    @Test
    public void testPointEquals() {
        Point[] points = {
                new Point(1, 2),
                new Point(1, 2),
                new Point(2, 2),
                new Point(1, 3)
        };
        for (int i = 0; i < points.length; ++i) {
            for (int j = 0; j < points.length; ++j) {
                if ((i <= 1) && (j <= 1) || (i == j))
                    assertEquals(points[i], points[j]);
                else
                    assertNotEquals(points[i], points[j]);
            }
        }
    }

    @Test
    public void testPointAdd() {
        Point a = new Point(2, 5);
        Point b = new Point(7, -4);
        Point expectedAB = new Point(9, 1);
        Point expectedAA = new Point(4, 10);
        Point expectedBB = new Point(14, -8);
        assertEquals(a.add(b), expectedAB);
        assertEquals(a.add(a), expectedAA);
        assertEquals(b.add(b), expectedBB);
    }

    @Test
    public void testEmptyGame() {
        Field field = new Field(4 , 6, true);
        assertEquals(4, field.getWidth());
        assertEquals(6, field.getHeight());
        assertTrue(field.getWrap());
        Game game = new Game(field);
    }

    @Test
    public void testFieldIsInside() {
        Field field = new Field(4 , 6, true);
        assertTrue(field.isInside(new Point(0, 0)));
        assertTrue(field.isInside(new Point(3, 0)));
        assertTrue(field.isInside(new Point(0, 5)));
        assertTrue(field.isInside(new Point(3, 5)));
        assertFalse(field.isInside(new Point(4, 0)));
        assertFalse(field.isInside(new Point(0, 6)));
        assertFalse(field.isInside(new Point(4, 5)));
        assertFalse(field.isInside(new Point(3, 6)));
        assertFalse(field.isInside(new Point(4, 6)));
        assertFalse(field.isInside(new Point(0, -1)));
        assertFalse(field.isInside(new Point(-1, 0)));
    }

    @Test
    public void testFieldWithApple() {
        Field field = new Field(4, 6, false);
        Apple apple = field.spawnApple(new Point(2, 1), AppleSimple::new);
        assertTrue(field.hasEntityAtPoint(new Point(2, 1)));
        assertFalse(field.hasEntityAtPoint(new Point(1, 2)));
        assertSame(apple, field.entityAtPoint(new Point(2, 1)));
        field.removeEntity(apple);
        assertFalse(field.hasEntityAtPoint(new Point(2, 1)));
    }

    @Test
    public void testFieldWithRandomApple() {
        Field field = new Field(4, 6, false);
        Apple apple = field.spawnRandomApple();
        Point position = apple.getPosition();
        assertTrue(field.hasEntityAtPoint(position));
        assertSame(apple, field.entityAtPoint(position));
    }

    @Test
    public void testRotateSnake() {
        Field field = new Field(4, 6, false);
        Game game = new Game(field);
        Snake newSnake = new Snake(new Point(1, 1), 0, field);
        field.spawnSnake(newSnake);
        assertEquals(0, newSnake.getDirection());
    }
}
