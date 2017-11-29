package test;

import static org.junit.Assert.*;

import main.*;
import main.Objects.Apple;
import main.Objects.AppleBig;
import main.Objects.AppleSimple;
import main.Objects.Wall;
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
        Game game = new Game(field, 1);
        assertFalse(game.getLost());
        assertEquals(0, game.getPoints());
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
        Game game = new Game(field, 1);
        Snake snake = game.spawnSnake(new Point(2, 1), 0);
        game.tick();
        assertEquals(new Point(3, 1), snake.getPosition());
        assertTrue(field.hasEntityAtPoint(new Point(3, 1)));
        assertFalse(field.hasEntityAtPoint(new Point(2, 1)));
        assertSame(snake, field.entityAtPoint(new Point(3, 1)));
    }

    @Test
    public void testCrashIntoBorder() {
        Field field = new Field(4, 6, false);
        Game game = new Game(field, 1);
        Snake snake = game.spawnSnake(new Point(0, 1), 2);
        game.tick();
        assertTrue(game.getLost());
    }

    @Test
    public void testWrapSnake() {
        Field field = new Field(4, 6, true);
        Game game = new Game(field, 1);
        Snake snake = game.spawnSnake(new Point(1, 5), 1);
        game.tick();
        assertEquals(new Point(1, 0), snake.getPosition());
        assertTrue(field.hasEntityAtPoint(new Point(1, 0)));
        assertFalse(field.hasEntityAtPoint(new Point(1, 5)));
        assertSame(snake, field.entityAtPoint(new Point(1, 0)));
        assertFalse(game.getLost());
    }

    @Test
    public void testEatApple() {
        Field field = new Field(4, 6, false);
        Game game = new Game(field, 1);
        Snake snake = game.spawnSnake(new Point(0, 3), 3);
        Apple apple = game.spawnApple(new Point(0, 2), AppleSimple::new);
        game.tick();
        assertTrue(field.hasEntityAtPoint(new Point(0, 2)));
        assertTrue(field.hasEntityAtPoint(new Point(0, 3)));
        assertSame(snake, field.entityAtPoint(new Point(0, 2)));
        assertSame(snake, field.entityAtPoint(new Point(0, 3)));
        assertEquals(new Point(0, 2), snake.getPosition());
        assertEquals(1, game.getPoints());
    }

    @Test
    public void testRotateSnake() {
        Field field = new Field(4, 6, false);
        Game game = new Game(field, 1);
        Snake snake = game.spawnSnake(new Point(1, 1), 0);
        game.setDirection(1);
        game.setDirection(3);
        game.setDirection(2);
        assertEquals(3, snake.getDirection());
        game.tick();
        assertEquals(new Point(1, 0), snake.getPosition());
        assertEquals(3, snake.getDirection());
    }

    @Test
    public void testEatAppleBig() {
        Field field = new Field(10, 10, false);
        Game game = new Game(field, 1);
        Snake snake = game.spawnSnake(new Point(0, 0), 0);
        Apple apple = game.spawnApple(new Point(1, 0), AppleBig::new);
        assertEquals(snake.length(), 1);
        game.tick();
        assertTrue(field.hasEntityAtPoint(new Point(0, 0)));
        assertTrue(field.hasEntityAtPoint(new Point(1, 0)));
        assertEquals(game.getPoints(), 2);
        assertEquals(snake.length(), 2);
        game.tick();
        assertTrue(field.hasEntityAtPoint(new Point(0, 0)));
        assertTrue(field.hasEntityAtPoint(new Point(1, 0)));
        assertTrue(field.hasEntityAtPoint(new Point(2, 0)));
        assertSame(snake, field.entityAtPoint(new Point(0, 0)));
        assertSame(snake, field.entityAtPoint(new Point(1, 0)));
        assertSame(snake, field.entityAtPoint(new Point(2, 0)));
        assertEquals(snake.length(), 3);
    }

    @Test
    public void testEatTail() {
        Field field = new Field(4, 4, false);
        Game game = new Game(field, 1);
        Snake snake = game.spawnSnake(new Point(0, 0), 0);
        snake.grow(3);
        assertEquals(snake.length(), 1);
        game.tick();
        assertEquals(snake.length(), 2);
        game.tick();
        assertEquals(snake.length(), 3);
        game.tick();
        assertEquals(snake.length(), 4);
        game.tick();
        assertEquals(snake.length(), 4);
        assertFalse(game.getLost());
        assertEquals(new Point(1, 0), snake.getPosition());
    }

    @Test
    public void testEatApplePoison() {
        Field field = new Field(20, 20, false);
        Game game = new Game(field, 1);
        Snake snake = field.spawnSnake(new Point(0, 0), 0);
        Apple apple = field.spawnApple(new Point(19, 0), AppleBig::new);
        for (int i = 0; i < 18; ++i)
            game.tick();
        assertTrue(field.hasEntityAtPoint(new Point(18, 0)));
        assertTrue(field.hasEntityAtPoint(new Point(19, 0)));
        assertSame(snake, field.entityAtPoint(new Point(18, 0)));
        assertNotSame(snake, field.entityAtPoint(new Point(19, 0)));
        game.tick();
        assertTrue(field.hasEntityAtPoint(new Point(19, 0)));
        assertSame(snake, field.entityAtPoint(new Point(19, 0)));
        assertEquals(snake.length(), 2);
    }

    @Test
    public void testEatWall() {
        Field field = new Field(10, 10, false);
        Game game = new Game(field, 1);
        Snake snake = game.spawnSnake(new Point(0, 0), 0);
        Wall wall = game.spawnWall(new Point(1, 0));
        game.tick();
        assertTrue(game.getLost());
    }
}