package main;

public class PowerUp {
    void outOfField(Game game) {
        game.outOfField();
    }

    void crash(Game game) {
        game.lose();
    }

    void eatApple(Game game, Apple apple) {
        game.eatApple(apple);
    }

    void grow(Game game) {
        game.field.removeEntity(game.snake);
        game.snake.grow(game.getGrowth());
        if (game.getGrowth() <= 0)
            game.setGrowth(0);
        else
            game.setGrowth(game.getGrowth() - 1);
        game.field.addEntity(game.snake);
    }

    void tick(Game game) {
    }

    public int getDirection(int dir) {
        return dir;
    }
}
