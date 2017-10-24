package main;

import java.awt.*;

abstract public class Sprite {
    protected Entity entity;

    public Entity getEntity()
    {
        return entity;
    }

    abstract void draw(Graphics g, Board board);
}
