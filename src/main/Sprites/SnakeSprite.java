package main.Sprites;

import main.Board;
import main.Point;
import main.Snake;
import main.TextureManager;

import java.awt.*;

public class SnakeSprite extends Sprite {
    protected Snake entity;

    public SnakeSprite(Snake snake) {
        entity = snake;
    }

    @Override
    public void draw(Graphics g, Board board) {
        TextureManager tm = board.getTextureManager();
        for (Point point: entity.getBody()) {
            board.drawImage(tm.getBody(), point.getX(), point.getY(), g);
        }
        board.drawImage(tm.getHead(), entity.getX(), entity.getY(), g);
    }
}
