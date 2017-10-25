package main;

import java.awt.*;

public class WallSprite extends Sprite {
    private Apple entity;
    public WallSprite(Apple wall) { entity = wall; }
    @Override
    void draw(Graphics g, Board board) {
        TextureManager tm = board.getTextureManager();
        board.drawImage(tm.getWall(), entity.getX(), entity.getY(), g);
    }
}
