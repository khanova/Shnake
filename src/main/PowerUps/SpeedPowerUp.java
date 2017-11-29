package main.PowerUps;

import main.Game;
import main.PowerUps.PowerUp;

public class SpeedPowerUp extends PowerUp {
    public SpeedPowerUp() {
        counter = 10;
    }

    public void start(Game game) {
        game.setSpeed(1);
    }

    public void finish(Game game) {
        game.setSpeed(2);
    }
}
