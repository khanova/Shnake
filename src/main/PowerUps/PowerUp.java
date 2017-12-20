package main.PowerUps;

import main.Field;
import main.Objects.Apple;
import main.Game;
import main.Point;
import main.Snake;

import java.io.Serializable;

public class PowerUp implements Serializable {
    protected int counter;

    public void outOfField(Snake snake) {
        snake.outOfField();
    }

    public void eatSnake(Snake snake) {
        snake.lose();
    }

    public void eatWall(Snake snake) {
        snake.lose();
    }

    public void eatApple(Snake snake, Apple apple) {
        snake.eatApple(apple);
    }

    public void grow(Snake snake, Point newPosition) {
        Field field = snake.getField();
        field.removeEntity(snake);
        snake.grow(snake.getGrowth(), newPosition);
        if (snake.getGrowth() <= 0)
            snake.setGrowth(0);
        else
            snake.setGrowth(snake.getGrowth() - 1);
        field.addEntity(snake);
    }

    public void start(Snake snake) {

    }

    public void tick(Snake snake) {
        --counter;
        if (counter <= 0) {
            finish(snake);
            snake.setPowerUp(new PowerUp());
        }
    }

    public void finish(Snake snake) {

    }

    public int getDirection(int dir) {
        return dir;
    }

    public int getCounter() {
        return counter;
    }
}
