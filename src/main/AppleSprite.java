package main;

import java.awt.*;

public class AppleSprite extends Sprite {
    protected Apple entity;

    public AppleSprite(Apple apple)
    {
        entity = apple;
    }

    @Override
    void draw(Graphics g, Board board) {
        TextureManager tm = board.getTextureManager();
        board.drawImage(tm.getApple(), entity.getX(), entity.getY(), g);
    }
}
