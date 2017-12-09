package main.Objects;

import main.*;
import main.Sprites.StationarySprite;
import main.Sprites.Sprite;

public class AppleSimple extends Apple {
    public AppleSimple(Point pos, Field field) {
        super(pos, field);
    }

    public void eatEffect(Game game) {
        game.addGrowth(1);
        game.addPoints(1);
    }

    public void eatEffectAuto(Game game) {
        game.addGrowthAuto(1);
        game.addPoints(1);
    }

    public void tick(Game game) {
    }

    @Override
    public Sprite createSprite() {
        return new StationarySprite(this, TextureManager::getApple);
    }
}
