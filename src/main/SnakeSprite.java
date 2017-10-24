package main;

import java.awt.*;

public class SnakeSprite extends Sprite {
    protected Snake entity;

    public SnakeSprite(Snake snake) {
        entity = snake;
    }

    @Override
    void draw(Graphics g, Board board) {
        TextureManager tm = board.getTextureManager();
        board.drawImage(tm.getHead(), entity.getX(), entity.getY(), g);
        for (Point point: entity.getBody()) {
            board.drawImage(tm.getBody(), point.getX(), point.getY(), g);
        }
    }
}
