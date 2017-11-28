package main;

import java.awt.*;
import java.util.function.Function;

public class AppleSprite extends Sprite {
    protected Apple entity;
    private Function<TextureManager, Image> func;

    public AppleSprite(Apple apple, Function<TextureManager, Image> func)
    {
        this.func = func;
        entity = apple;
    }

    @Override
    void draw(Graphics g, Board board) {
        TextureManager tm = board.getTextureManager();
        board.drawImage(func.apply(tm), entity.getX(), entity.getY(), g);
    }
}
