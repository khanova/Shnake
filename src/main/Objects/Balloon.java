package main.Objects;

import main.*;
import main.PowerUps.NoCrushPowerUp;
import main.Sprites.StationarySprite;
import main.Sprites.Sprite;

public class Balloon extends Apple {
    public Balloon(Point pos, Field field) {
        super(pos, field);
    }

    public void eatEffect(Game game) {
        game.addGrowth(1);
        game.addPoints(1);
        game.setPowerUp(new NoCrushPowerUp());
    }

    @Override
    public Sprite createSprite() {
        return new StationarySprite(this, TextureManager::getBalloon);
    }
}
