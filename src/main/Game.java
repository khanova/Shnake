package main;

import java.util.function.BiFunction;

public class Game {
    public Field field;
    public Snake snake;
    private int growth;
    protected PowerUp powerUp;
    private boolean lost;
    private int points;
    private int speed;
    private int tickCount;

    public Game(Field field) {
        powerUp = new PowerUp();
        this.field = field;
        lost = false;
        points = 0;
        growth = 0;
        speed = 1;
        tickCount = 0;
    }

    public boolean setDirection(int dir) {
        dir = powerUp.getDirection(dir);
        if (!(0 <= dir && dir < 4))
            throw new IllegalArgumentException();
        if ((snake.getDirection() + 2) % 4 == dir)
            return false;
        snake.setDirection(dir);
        return true;
    }

    public void tick() {
        ++tickCount;
        powerUp.tick(this);
        field.tick(this);
    }

    void eatApple(Apple apple) {
        field.removeEntity(apple);
        apple.eatEffect(this);
   }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int value) {
        growth = value;
    }

    public int getWidth() {
        return field.getWidth();
    }

    public int getHeight() {
        return field.getHeight();
    }

    public void spawnSnake(Point point, int i) {
        snake = field.spawnSnake(point, i);
    }

    public void spawnApple(Point point, BiFunction<Point, Field, Apple> aNew) {
        field.spawnApple(point, aNew);
    }

    public void spawnRandomApple() {
        field.spawnRandomApple();
    }

    public void outOfField() {
        if (field.getWrap()) {

        }
        else {
            lose();
        }
    }

    protected void lose() {
        lost = true;
    }

    public boolean getLost() {
        return lost;
    }

    public void addPoints(int i) {
        points += i;
    }

    public void addGrowth(int i) {
        growth += i;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isEvalTick() {
        return tickCount % speed == 0;
    }
}
