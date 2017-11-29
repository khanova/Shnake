package main.PowerUps;

import main.Game;
import main.PowerUps.PowerUp;

public class NoCrushPowerUp extends PowerUp {
    public NoCrushPowerUp() {
        counter = 15;
    }

    @Override
    public void eatYourself(Game game) {

    }
}
