package main;

import java.awt.*;

public class AppleBigSprite extends Sprite {
    protected Apple entity;

    public AppleBigSprite(Apple apple)
    {
        entity = apple;
    }

    @Override
    void draw(Graphics g, Board board) {
        TextureManager tm = board.getTextureManager();
        board.drawImage(tm.getAppleBig(), entity.getX(), entity.getY(), g);
    }
}
