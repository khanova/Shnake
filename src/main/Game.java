package main;

import main.Objects.Apple;
import main.Objects.Wall;
import main.PowerUps.PowerUp;

import java.util.function.BiFunction;

public class Game {
    private Field field;
    private Snake snake;
    private AutoSnake autoSnake;
    private int growthSnake;
    private int growthAutoSnake;
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
        growthSnake = 0;
        this.speed = speed;
        tickCount = 0;
    }

    public boolean setDirection(int dir) {
        dir = powerUp.getDirection(dir);
        return snake.setDirection(dir);

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

    public void eatAppleAuto(Apple apple) {
        field.removeEntity(apple);
        apple.eatEffectAuto(this);
    }

    public void eatWall() {
        getPowerUp().eatWall(this);
    }

    public void eatWallAuto() {
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

    public AutoSnake getAutoSnake() { return autoSnake; }

    public int getGrowthSnake() {
        return growthSnake;
    }

    public int getGrowthAutoSnake() {
        return growthAutoSnake;
    }

    public void setGrowthSnake(int value) {
        growthSnake = value;
    }

    public void setGrowthAutoSnake(int value) { growthAutoSnake = value; }

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

    public AutoSnake spawnAutoSnake(Point position, int i) {
        autoSnake = field.spawnAutoSnake(position, i);
        return autoSnake;
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

    public void addGrowth(int i) { growthSnake += i; }

    public void addGrowthAuto(int i) {
        growthAutoSnake += i;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isEvalTick() {
        return tickCount % speed == 0;
    }
}
