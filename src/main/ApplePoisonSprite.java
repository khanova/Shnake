package main;

import java.awt.*;

public class ApplePoisonSprite extends Sprite {
    protected Apple entity;
    public ApplePoisonSprite(Apple apple) {
        entity = apple;
    }

    @Override
    void draw(Graphics g, Board board) {
        TextureManager tm = board.getTextureManager();
        board.drawImage(tm.getApplePoison(), entity.getX(), entity.getY(), g);
    }
}
