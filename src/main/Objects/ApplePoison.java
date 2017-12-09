package main.Objects;

import main.*;
import main.PowerUps.WrongDirectionPowerUp;
import main.Sprites.StationarySprite;
import main.Sprites.Sprite;

public class ApplePoison extends Apple {
    public ApplePoison(Point pos, Field field) { super(pos, field); }

    public void eatEffect(Game game) {
        game.addGrowth(1);
        game.addPoints(1);
        game.setPowerUp(new WrongDirectionPowerUp());
    }

    public void eatEffectAuto(Game game) {
        game.addGrowthAuto(1);
        game.addPoints(1);
        game.setPowerUp(new WrongDirectionPowerUp());
    }

    public void tick(Game game) {
    }

    @Override
    public Sprite createSprite() {
        return new StationarySprite(this, TextureManager::getApplePoison);
    }
}
