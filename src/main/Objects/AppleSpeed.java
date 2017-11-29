package main.Objects;

import main.*;
import main.PowerUps.SpeedPowerUp;
import main.Sprites.StationarySprite;
import main.Sprites.Sprite;

public class AppleSpeed extends Apple {
    public AppleSpeed(Point pos, Field field) {
        super(pos, field);
    }

    public void eatEffect(Game game) {
        game.addGrowth(1);
        game.addPoints(1);
        game.setPowerUp(new SpeedPowerUp());
    }

    @Override
    public Sprite createSprite() {
        return new StationarySprite(this, TextureManager::getRainbow);
    }
}
