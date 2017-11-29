package main.Sprites;

import main.Entity;
import main.Board;
import main.TextureManager;

import java.awt.*;
import java.util.function.Function;

public class StationarySprite extends Sprite {
    protected Entity entity;
    private Function<TextureManager, Image> get_image;

    public StationarySprite(Entity entity, Function<TextureManager, Image> get_image)
    {
        this.get_image = get_image;
        this.entity = entity;
    }

    @Override
    public void draw(Graphics g, Board board) {
        TextureManager tm = board.getTextureManager();
        board.drawImage(get_image.apply(tm), entity.getX(), entity.getY(), g);
    }
}
