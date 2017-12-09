package main.Objects;

import main.*;
import main.Sprites.StationarySprite;
import main.Sprites.Sprite;

public class AppleBig extends Apple {
    public AppleBig(Point pos, Field field) {
        super(pos, field);
        ticks = 0;
    }

    private static final int MAX_TICK = 15;
    private int ticks;

    public void eatEffect(Game game) {
        game.addGrowth(2);
        game.addPoints(2);
    }

    public void eatEffectAuto(Game game) {
        game.addGrowthAuto(2);
        game.addPoints(2);
    }

    public void tick(Game game) {
        ticks++;
        if (ticks == MAX_TICK) {
            game.removeEntity(this);
            game.addEntity(new ApplePoison(position, field));
        }
    }

    @Override
    public Sprite createSprite() {
        return new StationarySprite(this, TextureManager::getAppleBig);
    }
}
