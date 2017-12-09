package main.PowerUps;

import main.AutoSnake;
import main.Objects.Apple;
import main.Game;
import main.Point;
import main.Snake;

public class PowerUp {
    protected int counter;

    public void outOfField(Game game) {
        game.outOfField();
    }

    public void eatYourself(Game game) {
        game.lose();
    }

    public void eatWall(Game game) {
        game.lose();
    }

    public void eatApple(Game game, Apple apple) {
        game.eatApple(apple);
    }

    public void eatAppleAuto(Game game, Apple apple) {
        game.eatAppleAuto(apple);
    }

    public void growSnake(Game game, Point newPosition) {
        Snake snake = game.getSnake();
        game.removeEntity(snake);
        snake.grow(game.getGrowthSnake(), newPosition);
        if (game.getGrowthSnake() <= 0)
            game.setGrowthSnake(0);
        else
            game.setGrowthSnake(game.getGrowthSnake() - 1);
        game.addEntity(snake);
    }

    public void growAutoSnake(Game game, Point newPosition) {
        AutoSnake autoSnake = game.getAutoSnake();
        game.removeEntity(autoSnake);
        autoSnake.grow(game.getGrowthAutoSnake(), newPosition);
        if (game.getGrowthSnake() <= 0)
            game.setGrowthAutoSnake(0);
        else
            game.setGrowthAutoSnake(game.getGrowthAutoSnake() - 1);
        game.addEntity(autoSnake);
    }

    public void start(Game game) {

    }

    public void tick(Game game) {
        --counter;
        if (counter <= 0) {
            finish(game);
            game.setPowerUp(new PowerUp());
        }
    }

    public void finish(Game game) {

    }

    public int getDirection(int dir) {
        return dir;
    }

    public int getCounter() {
        return counter;
    }
}
