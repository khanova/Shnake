package test;

import static org.junit.Assert.*;

import main.*;
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
    public void testEmptyField() {
        Field field = new Field(4 , 6, true);
        assertEquals(4, field.getWidth());
        assertEquals(6, field.getHeight());
        assertEquals(true, field.getWrap());
        assertEquals(false, field.getLost());
        assertEquals(0, field.getPoints());
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
        Apple apple = field.spawnApple(new Point(2, 1), Apple::new);
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
    public void testFieldWithSnake() {
        Field field = new Field(4, 6, false);
        Snake snake = field.spawnSnake(new Point(2, 1), 0);
        assertSame(snake, field.entityAtPoint(new Point(2, 1)));
        assertEquals(new Point(2, 1), snake.getPosition());
        assertTrue(snake.isOccupied(new Point(2, 1)));
        assertFalse(snake.isOccupied(new Point(1, 2)));
        assertFalse(snake.isIntersecting());
    }

    @Test
    public void testMoveSnake() {
        Field field = new Field(4, 6, false);
        Snake snake = field.spawnSnake(new Point(2, 1), 0);
        field.tick();
        assertEquals(new Point(3, 1), snake.getPosition());
        assertTrue(field.hasEntityAtPoint(new Point(3, 1)));
        assertFalse(field.hasEntityAtPoint(new Point(2, 1)));
        assertSame(snake, field.entityAtPoint(new Point(3, 1)));
    }

    @Test
    public void testCrashIntoWall() {
        Field field = new Field(4, 6, false);
        Snake snake = field.spawnSnake(new Point(0, 1), 2);
        field.tick();
        assertTrue(field.getLost());
    }

    @Test
    public void testWrapSnake() {
        Field field = new Field(4, 6, true);
        Snake snake = field.spawnSnake(new Point(1, 5), 1);
        field.tick();
        assertEquals(new Point(1, 0), snake.getPosition());
        assertTrue(field.hasEntityAtPoint(new Point(1, 0)));
        assertFalse(field.hasEntityAtPoint(new Point(1, 5)));
        assertSame(snake, field.entityAtPoint(new Point(1, 0)));
        assertFalse(field.getLost());
    }

    @Test
    public void testEatApple() {
        Field field = new Field(4, 6, false);
        Snake snake = field.spawnSnake(new Point(0, 3), 3);
        Apple apple = field.spawnApple(new Point(0, 2), Apple::new);
        field.tick();
        assertTrue(field.hasEntityAtPoint(new Point(0, 2)));
        assertTrue(field.hasEntityAtPoint(new Point(0, 3)));
        assertSame(snake, field.entityAtPoint(new Point(0, 2)));
        assertSame(snake, field.entityAtPoint(new Point(0, 3)));
        assertEquals(new Point(0, 2), snake.getPosition());
        assertEquals(1, field.getPoints());
    }

    @Test
    public void testRotateSnake() {
        Field field = new Field(4, 6, false);
        Snake snake = field.spawnSnake(new Point(1, 1), 0);
        snake.setDirection(1);
        snake.setDirection(3);
        snake.setDirection(2);
        assertEquals(3, snake.getDirection());
        assertEquals(0, snake.getLastDirection());
        field.tick();
        assertEquals(new Point(1, 0), snake.getPosition());
        assertEquals(3, snake.getDirection());
        assertEquals(3, snake.getLastDirection());
    }

    @Test
    public void testEatAppleBig() {
        Field field = new Field(10, 10, false);
        Snake snake = field.spawnSnake(new Point(0, 0), 0);
        Apple apple = field.spawnApple(new Point(1, 0), AppleBig::new);
        field.tick();
        assertTrue(field.hasEntityAtPoint(new Point(0, 0)));
        assertTrue(field.hasEntityAtPoint(new Point(1, 0)));
        assertEquals(snake.getGrowth(), 1);
        assertEquals(field.getPoints(), 2);
        field.tick();
        assertTrue(field.hasEntityAtPoint(new Point(0, 0)));
        assertTrue(field.hasEntityAtPoint(new Point(1, 0)));
        assertTrue(field.hasEntityAtPoint(new Point(2, 0)));
        assertSame(snake, field.entityAtPoint(new Point(0, 0)));
        assertSame(snake, field.entityAtPoint(new Point(1, 0)));
        assertSame(snake, field.entityAtPoint(new Point(2, 0)));
    }

    @Test
    public void testEatApplePoison() {
        Field field = new Field(10, 10, false);
        Snake snake = field.spawnSnake(new Point(0, 0), 0);
        Apple apple = field.spawnApple(new Point(9, 0), AppleBig::new);
        for (int i = 0; i < 8; ++i)
            field.tick();
        assertTrue(field.hasEntityAtPoint(new Point(8, 0)));
        assertTrue(field.hasEntityAtPoint(new Point(9, 0)));
        assertSame(snake, field.entityAtPoint(new Point(8, 0)));
        assertNotSame(snake, field.entityAtPoint(new Point(9, 0)));
        field.tick();
        assertTrue(field.hasEntityAtPoint(new Point(9, 0)));
        assertSame(snake, field.entityAtPoint(new Point(9, 0)));
        assertEquals(snake.getGrowth(), 0);
        assertEquals(snake.length(), 1);
        assertEquals(field.getPoints(), -1);
    }
}