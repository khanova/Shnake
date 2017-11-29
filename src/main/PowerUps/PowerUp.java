package main.PowerUps;

import main.Objects.Apple;
import main.Game;
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

    public void grow(Game game) {
        Snake snake = game.getSnake();
        game.removeEntity(snake);
        snake.grow(game.getGrowth());
        if (game.getGrowth() <= 0)
            game.setGrowth(0);
        else
            game.setGrowth(game.getGrowth() - 1);
        game.addEntity(snake);
    }

    public void start(Game game) {

    }

    public void tick(Game game) {
        --counter;
        if (counter == 0) {
            finish(game);
            game.setPowerUp(new PowerUp());
        }
    }

    public void finish(Game game) {

    }

    public int getDirection(int dir) {
        return dir;
    }
}
