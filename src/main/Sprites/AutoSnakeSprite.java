package main.Sprites;

import main.*;
import main.Point;

import java.awt.*;

public class AutoSnakeSprite extends  Sprite{
    protected AutoSnake entity;

    public AutoSnakeSprite(AutoSnake snake) {
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
