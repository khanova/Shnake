package main.Sprites;

import main.Board;
import main.Entity;

import java.awt.*;

abstract public class Sprite {
    protected Entity entity;

    public Entity getEntity()
    {
        return entity;
    }

    public abstract void draw(Graphics g, Board board);
}
