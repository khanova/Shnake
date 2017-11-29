package main;

import main.Objects.Apple;
import main.Objects.Wall;
import main.PowerUps.PowerUp;

import java.util.function.BiFunction;

public class Game {
    private Field field;
    private Snake snake;
    private int growth;
    private PowerUp powerUp;
    private boolean lost;
    private int points;
    private int speed;
    private int tickCount;

    public Game(Field field, int speed) {
        powerUp = new PowerUp();
        this.field = field;
        lost = false;
        points = 0;
        growth = 0;
        this.speed = speed;
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

    public int getDirection() { return snake.getDirection(); }

    public void tick() {
        ++tickCount;
        powerUp.tick(this);
        field.tick(this);
    }

    public void eatApple(Apple apple) {
        field.removeEntity(apple);
        apple.eatEffect(this);
    }

    public void eatWall() {
        getPowerUp().eatWall(this);
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    public void setPowerUp(PowerUp powerUp)
    {
        this.powerUp.finish(this);
        this.powerUp = powerUp;
        powerUp.start(this);
    }

    public Field getField() { return field; }

    public Snake getSnake() { return snake; }

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

    public Snake spawnSnake(Point position, int i) {
        snake = field.spawnSnake(position, i);
        return snake;
    }

    public Apple spawnApple(Point position, BiFunction<Point, Field, Apple> aNew) {
        return field.spawnApple(position, aNew);
    }

    public Wall spawnWall(Point position) { return field.spawnWall(position); }

    public void spawnRandomApple() {
        field.spawnRandomApple();
    }

    public void addEntity(Entity entity) { field.addEntity(entity); }

    public void removeEntity(Entity entity) { field.removeEntity(entity); }

    public void outOfField() {
        if (!field.getWrap()) {
            lose();
        }
    }

    public void lose() {
        lost = true;
    }

    public boolean getLost() {
        return lost;
    }

    public void addPoints(int i) {
        points += i;
    }

    public int getPoints() { return points; }

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
