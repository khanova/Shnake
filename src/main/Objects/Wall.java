package main.Objects;

import main.Entity;
import main.Game;
import main.Point;
import main.Sprites.StationarySprite;
import main.Sprites.Sprite;
import main.TextureManager;

public class Wall extends Entity implements Edible {
    public Wall(Point pos) {
        position = pos;
    }

    public void tick(Game game) {

    }

    public void eatEffect(Game game) {
        game.eatWall();
    }

    @Override
    public Sprite createSprite() {
        return new StationarySprite(this, TextureManager::getWall);
    }
}
